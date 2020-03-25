package com.github.ldeitos.validation.impl.interceptor;

import static com.github.ldeitos.constants.Constants.DEFAULT_VALIDATION_CLOSURE_QUALIFIER;
import static com.github.ldeitos.constants.Constants.EXTENDED_VALIDATOR_QUALIFIER;
import static com.github.ldeitos.validation.impl.configuration.Configuration.getConfiguration;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.slf4j.LoggerFactory.getLogger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.ldeitos.bootstrap.SBContext;
import com.github.ldeitos.validation.Message;
import com.github.ldeitos.validation.ValidationClosure;
import com.github.ldeitos.validation.Validator;
import com.github.ldeitos.validation.annotation.SkipValidation;
import com.github.ldeitos.validation.annotation.ValidateParameters;
import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;

/**
 * CDI based interceptor to do audit process.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @since 0.9.2
 */
@Aspect
public class ValidateParametersInterceptor {
	private Logger logger = getLogger(getClass());

	@Autowired
	@Qualifier(EXTENDED_VALIDATOR_QUALIFIER)
	private Validator validator;

	@Autowired
	private ConfigInfoProvider configProvider;

	/**
	 * Validation interceptor method.
	 *
	 * @return Intercepted method result.
	 *
	 * @throws Exception
	 * 			  Any exception throwed by validation execution or after that, when execution method proceeds.
	 *
	 */
	@Around("@annotation(com.github.ldeitos.validation.annotation.ValidateParameters)")
	public Object doAudit(ProceedingJoinPoint pjp) throws Throwable {
		Set<Message> violations = doValidation(pjp);

		if (isNotEmpty(violations)) {
			ValidationClosure closure = getClosure(pjp);

			closure.proceed(violations);
			logger.info("Validation process successfuly completed.");
		}

		return pjp.proceed();
	}

	private Set<Message> doValidation(ProceedingJoinPoint pjp) throws NoSuchMethodException, SecurityException {
		Signature sourceCall = pjp.getSignature();
		String methodName = sourceCall.getName();
		Object[] args = pjp.getArgs();
		Class<?> aspectedType = sourceCall.getDeclaringType();
				
		Method invokedMethod = aspectedType.getMethod(methodName, parameterTypes(args));
		logger.info(format("Init method [%s] parameters validation process.", methodName));
		Set<Message> violations = new HashSet<Message>();
		Set<Message> objectViolations = new HashSet<Message>();
		Annotation[][] parameterAnnotations = invokedMethod.getParameterAnnotations();
		Object[] toValidate = getParametersToValidate(args, parameterAnnotations);

		for (Object object : toValidate) {
			if (logger.isDebugEnabled()) {
				logger.debug(format("Doing parameter [%s] validation. Parameter class: [%s]",
				    valueOf(object), object.getClass().getSimpleName()));
			}

			objectViolations.addAll(validator.validateBean(object, getValidationGroups(aspectedType, invokedMethod)));

			violations.addAll(objectViolations);

			if (isNotEmpty(objectViolations)) {
				logger.debug("Violations found during validation process.");
				objectViolations.clear();
			}
		}

		return violations;
	}

	private Class<?>[] parameterTypes(Object[] args) {
		int argsLength = args.length;
		Class<?>[] parameterTypes = new Class<?>[argsLength];
		
		for(int ix = 0; ix < argsLength; ix++) {
			parameterTypes[ix] = args[ix].getClass();
		}
		
		return parameterTypes;
	}

	private Class<?>[] getValidationGroups(Class<?> aspectedType, Method method) {
		ValidateParameters conf = getInterceptorConfiguration(aspectedType, method);

		return conf.groups();
	}

	private ValidateParameters getInterceptorConfiguration(Class<?> aspectedType, Method method) {
		ValidateParameters conf = method.getAnnotation(ValidateParameters.class);

		if (conf == null) {
			conf = aspectedType.getAnnotation(ValidateParameters.class);
		}
		
		return conf;
	}

	private Object[] getParametersToValidate(Object[] parameters, Annotation[][] parameterAnnotations) {
		List<Object> result = new ArrayList<Object>();

		for (int i = 0; i < parameterAnnotations.length; i++) {
			Object parameter = parameters[i];

			if (assertNotNull(parameter)) {
				if (logger.isDebugEnabled()) {
					String log = format("Processing parameter [%s], parameter index [%d].",
						valueOf(parameter), i);
					logger.debug(log);
				}

				Annotation[] annotations = parameterAnnotations[i];
				SkipValidation skipped = getAnnotation(annotations, SkipValidation.class);

				if (skipped == null) {
					logger.debug(format("Parameter [%s] added to validation process.", valueOf(parameter)));
					result.add(parameter);
				}
			}
		}

		logParamtersToValidate(result);

		return result.toArray();
	}

	private boolean assertNotNull(Object parameter) {
		boolean notNull = parameter != null;

		if (!notNull && logger.isDebugEnabled()) {
			logger.debug("Parameter value is null, skipping.");
		}

		return notNull;
	}

	private void logParamtersToValidate(List<Object> toValidate) {
		if (isEmpty(toValidate)) {
			logger.warn("Has no parameters to be validated.");
		} else {
			logger.debug(format("Found %d paramters to validate.", toValidate.size()));
		}
	}

	private ValidationClosure getClosure(ProceedingJoinPoint pjp) throws NoSuchMethodException, SecurityException {
		Signature sourceCall = pjp.getSignature();
		String methodName = sourceCall.getName();
		Object[] args = pjp.getArgs();
		Class<?> aspectedType = sourceCall.getDeclaringType();
				
		Method invokedMethod = aspectedType.getMethod(methodName, parameterTypes(args));

		ValidationClosure closure = getConfiguration(configProvider).getConfiguredValidationClosure();
		ValidateParameters conf = getInterceptorConfiguration(aspectedType, invokedMethod);

		if (mustUseSpecificClosure(conf.closure())) {
			closure = SBContext.get(ValidationClosure.class, conf.closure());
		}

		return closure;
	}

	private boolean mustUseSpecificClosure(Qualifier qualifier) {
		String stringQualifier = qualifier.value();

		return !DEFAULT_VALIDATION_CLOSURE_QUALIFIER.equals(stringQualifier);
	}

	@SuppressWarnings("unchecked")
	private <T> T getAnnotation(Annotation[] annotations, Class<T> annotationClass) {
		for (Annotation annotation : annotations) {
			if (annotationClass.isAssignableFrom(annotation.getClass())) {
				logger.debug("Parameter identified with @SkipValidation annotation, will be ignored.");
				return (T) annotation;
			}
		}

		return null;
	}
}