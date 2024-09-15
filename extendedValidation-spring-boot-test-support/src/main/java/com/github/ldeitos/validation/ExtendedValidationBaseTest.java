package com.github.ldeitos.validation;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.ldeitos.bootstrap.ExtendedValidationBootstrap;
import com.github.ldeitos.bootstrap.SBContext;
import com.github.ldeitos.validation.impl.MessageResolverImpl;
import com.github.ldeitos.validation.impl.ValidatorImpl;
import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;
import com.github.ldeitos.validation.impl.interceptor.ValidateParametersInterceptor;
import com.github.ldeitos.validation.impl.interpolator.MultipleBundlesSource;
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
		ValidateParametersInterceptor.class, DefaultValidationClosure.class, MessageResolverImpl.class, ConfigInfoProvider.class })
public class ExtendedValidationBaseTest {

	@BeforeAll
	public static void setup() {
	}

	@AfterAll
	public static void shutdown() {
	}

}
