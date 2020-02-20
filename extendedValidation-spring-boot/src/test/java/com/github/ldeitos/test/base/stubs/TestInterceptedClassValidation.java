package com.github.ldeitos.test.base.stubs;

import org.springframework.beans.factory.annotation.Qualifier;

import com.github.ldeitos.validation.annotation.SkipValidation;
import com.github.ldeitos.validation.annotation.ValidateParameters;

@ValidateParameters
public class TestInterceptedClassValidation {

	public void noParameters() {

	}

	public void oneParameter(TestBeanA a) {

	}

	public void twoParameter(TestBeanA a, TestBeanB b) {

	}

	public void twoParameterBSkipped(TestBeanA a, @SkipValidation TestBeanB b) {

	}

	public void varParameters(TestBeanA... a) {

	}

	@ValidateParameters(groups = GrupoTestBeanA.class)
	public void withGroup(TestBeanA a) {

	}

	@ValidateParameters(closure = @Qualifier("Test"))
	public void customClosure(TestBeanA a, TestBeanB b) {

	}

	@ValidateParameters(closure = @Qualifier("OtherTest"))
	public void otherCustomClosure(TestBeanA a) {

	}

}
