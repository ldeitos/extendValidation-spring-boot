package com.github.ldeitos.test.base;

import static com.github.ldeitos.constants.Constants.EXTENDED_VALIDATOR_QUALIFIER;

import javax.validation.Validator;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.ldeitos.bootstrap.ExtendedValidationBootstrap;
import com.github.ldeitos.bootstrap.SBContext;
import com.github.ldeitos.test.base.stubs.TestCustomValidationClosure;
import com.github.ldeitos.test.base.stubs.TestInterceptedClassValidation;
import com.github.ldeitos.test.base.stubs.TestInterceptedMethodsValidation;
import com.github.ldeitos.validation.ValidationClosure;
import com.github.ldeitos.validation.impl.MessageResolverImpl;
import com.github.ldeitos.validation.impl.ValidatorImpl;
import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;
import com.github.ldeitos.validation.impl.interceptor.ValidateParametersInterceptor;
import com.github.ldeitos.validation.impl.interpolator.MultipleBundlesSource;
import com.github.ldeitos.validation.impl.interpolator.PreInterpolator;
import com.github.ldeitos.validation.impl.interpolator.TestMessageSource;
import com.github.ldeitos.validation.impl.util.DefaultValidationClosure;
import com.github.ldeitos.validators.DigitsValidatorImpl;
import com.github.ldeitos.validators.EmptyValidatorImpl;
import com.github.ldeitos.validators.MaxDecimalValidatorImpl;
import com.github.ldeitos.validators.MaxValidatorImpl;
import com.github.ldeitos.validators.MinDecimalValidatorImpl;
import com.github.ldeitos.validators.MinValidatorImpl;
import com.github.ldeitos.validators.NotEmptyValidatorImpl;
import com.github.ldeitos.validators.PatternValidatorImpl;
import com.github.ldeitos.validators.RangeValidatorImpl;
import com.github.ldeitos.validators.SizeValidatorImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ExtendedValidationBootstrap.class, SBContext.class, ValidatorImpl.class,
		MultipleBundlesSource.class, NotEmptyValidatorImpl.class, EmptyValidatorImpl.class, PatternValidatorImpl.class,
		DigitsValidatorImpl.class, SizeValidatorImpl.class, MinDecimalValidatorImpl.class, MinValidatorImpl.class,
		MaxDecimalValidatorImpl.class, MaxValidatorImpl.class, RangeValidatorImpl.class,
		ValidateParametersInterceptor.class, DefaultValidationClosure.class, MessageResolverImpl.class,
		ConfigInfoProvider.class, ValidateParametersInterceptor.class, DefaultValidationClosure.class,
		TestInterceptedClassValidation.class, TestInterceptedMethodsValidation.class, TestMessageSource.class,
		TestCustomValidationClosure.class, DefaultValidationClosure.class, PreInterpolator.class })
public abstract class BaseTest {

	@Autowired
	@Qualifier(EXTENDED_VALIDATOR_QUALIFIER)
	private Validator validador;

	@Autowired
	@Qualifier(EXTENDED_VALIDATOR_QUALIFIER)
	private com.github.ldeitos.validation.Validator extendedValidator;

	@Mock
	private ValidationClosure validationClosure = new DefaultValidationClosure();

	@Mock
	private ConfigInfoProvider cip = new ConfigInfoProvider() {

		@Override
		public String getConfigFileName() {
			return "extendedValidation_configuredValidationClosure.xml";
		}

		@Override
		protected boolean isInTest() {
			return true;
		}
	};

	@BeforeClass
	public static void setup() {
		System.setProperty("org.jboss.weld.nonPortableMode", "true");
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "off");
	}

	@AfterClass
	public static void shutdown() {
		System.clearProperty("org.jboss.weld.nonPortableMode");
	}

	protected Class<?> getClassOnTest() {
		return getClass();
	}

	public Validator getValidador() {
		return validador;
	}

	public com.github.ldeitos.validation.Validator getExtendedValidator() {
		return extendedValidator;
	}

	public ConfigInfoProvider getCip() {
		return cip;
	}
}