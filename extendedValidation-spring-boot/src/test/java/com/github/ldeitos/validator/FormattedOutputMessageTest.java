package com.github.ldeitos.validator;

import static com.github.ldeitos.constants.Constants.EXTENDED_VALIDATOR_QUALIFIER;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.ldeitos.bootstrap.ExtendedValidationBootstrap;
import com.github.ldeitos.bootstrap.SBContext;
import com.github.ldeitos.test.base.stubs.FormattedPresentationValidatorImpl;
import com.github.ldeitos.test.base.stubs.FormattedTest;
import com.github.ldeitos.test.base.stubs.GrupoTestBeanA;
import com.github.ldeitos.test.base.stubs.TestInterceptedClassValidation;
import com.github.ldeitos.test.base.stubs.TestInterceptedMethodsValidation;
import com.github.ldeitos.validation.impl.MessageResolverImpl;
import com.github.ldeitos.validation.impl.ValidatorImpl;
import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;
import com.github.ldeitos.validation.impl.configuration.Configuration;
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


@SpringBootTest(classes = { ExtendedValidationBootstrap.class, SBContext.class, ValidatorImpl.class,
		MultipleBundlesSource.class, NotEmptyValidatorImpl.class, EmptyValidatorImpl.class, PatternValidatorImpl.class,
		DigitsValidatorImpl.class, SizeValidatorImpl.class, MinDecimalValidatorImpl.class, MinValidatorImpl.class,
		MaxDecimalValidatorImpl.class, MaxValidatorImpl.class, RangeValidatorImpl.class,
		ValidateParametersInterceptor.class, MessageResolverImpl.class, ValidateParametersInterceptor.class,
		DefaultValidationClosure.class, TestInterceptedClassValidation.class, TestInterceptedMethodsValidation.class,
		TestMessageSource.class, DefaultValidationClosure.class, PreInterpolator.class,
		FormattedPresentationValidatorImpl.class, ConfigInfoProvider.class })
public class FormattedOutputMessageTest {

	private ConfigInfoProvider cip = new ConfigInfoProvider() {
		@Override
		public String getConfigFileName() {
			return "extendedValidation_configuredTemplateMessagePresentation.xml";
		}

		@Override
		protected boolean isInTest() {
			return true;
		}
	};
	
	@Autowired
	@Qualifier(EXTENDED_VALIDATOR_QUALIFIER)
	private Validator validador;
	

	public Validator getValidador() {
		return validador;
	}

	@BeforeEach
	public void setup() {
		Configuration.unload();
		Configuration.load(cip);
	}
	
	@AfterAll
	public static void shutdown() {
		Configuration.unload();
	}

	@Test
	public void testFormatedOutput() {
		FormattedTest f = new FormattedTest();

		Set<ConstraintViolation<FormattedTest>> violations = getValidador().validate(f);
		assertEquals(1, violations.size());
		String msg = violations.iterator().next().getMessage();
		assertEquals("(KEY) Message Test", msg);
	}

	@Test
	public void testFormatedOutputWithExtendedAbstractValidator() {
		FormattedTest f = new FormattedTest();

		Set<ConstraintViolation<FormattedTest>> violations = getValidador().validate(f, GrupoTestBeanA.class);
		assertEquals(2, violations.size());
		List<String> msgs = new ArrayList<String>();

		Iterator<ConstraintViolation<FormattedTest>> iterator = violations.iterator();
		msgs.add(iterator.next().getMessage());
		msgs.add(iterator.next().getMessage());
		assertEquals(true, msgs.contains("(KEY) Message Test"));
		assertEquals(true, msgs.contains("(KEYB) Test B Message"));
	}
}