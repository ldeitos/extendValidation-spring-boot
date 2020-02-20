package com.github.ldeitos.validation.impl.interceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.ldeitos.test.base.BaseTest;
import com.github.ldeitos.test.base.stubs.TestBeanA;
import com.github.ldeitos.test.base.stubs.TestBeanB;
import com.github.ldeitos.test.base.stubs.TestInterceptedClassValidation;
import com.github.ldeitos.test.base.stubs.TestInterceptedMethodsValidation;

public class ValidateParametersInterceptorWithConfiguredValidationClosureTest extends BaseTest {

	@Autowired
	private TestInterceptedClassValidation classIntercepted;

	@Autowired
	private TestInterceptedMethodsValidation methodIntercepted;

	private TestBeanA validBeanA = new TestBeanA("");

	private TestBeanA invalidBeanA = new TestBeanA();

	private TestBeanB validBeanB = new TestBeanB("");

	private TestBeanB invalidBeanB = new TestBeanB();

	@Test
	public void testMethodInterceptedInvockedMethodNoParameter() {
		methodIntercepted.noParameters();
	}

	@Test
	public void testClassInterceptedInvockedMethodNoParameter() {
		classIntercepted.noParameters();
	}

	@Test
	public void testMethodInterceptedValidParameterMethodOneParameter() {
		methodIntercepted.oneParameter(validBeanA);
	}

	@Test
	public void testClassInterceptedValidParameterMethodOneParameter() {
		classIntercepted.oneParameter(validBeanA);
	}

	@Test
	public void testMethodInterceptedInvalidParameterMethodOneParameter() {
		try {
			methodIntercepted.oneParameter(invalidBeanA);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 1 violations", message);
		}
	}

	@Test
	public void testClassInterceptedInvalidParameterMethodOneParameter() {
		try {
			classIntercepted.oneParameter(invalidBeanA);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 1 violations", message);
		}
	}

	@Test
	public void testMethodInterceptedValidParameterMethodTwoParameter() {
		methodIntercepted.twoParameter(validBeanA, validBeanB);
	}

	@Test
	public void testClassInterceptedValidParameterMethodTwoParameter() {
		classIntercepted.twoParameter(validBeanA, validBeanB);
	}

	@Test
	public void testMethodInterceptedInvalidParameterMethodTwoParameter() {
		try {
			methodIntercepted.twoParameter(invalidBeanA, invalidBeanB);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 2 violations", message);
		}
	}

	@Test
	public void testClassInterceptedInvalidParameterMethodTwoParameter() {
		try {
			classIntercepted.twoParameter(invalidBeanA, invalidBeanB);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 2 violations", message);
		}
	}

	@Test
	public void testMethodInterceptedInvalidParameterMethodTwoParameterOneNull() {
		try {
			methodIntercepted.twoParameter(null, invalidBeanB);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 1 violations", message);
		}
	}

	@Test
	public void testClassInterceptedInvalidParameterMethodTwoParameterOneNull() {
		try {
			classIntercepted.twoParameter(invalidBeanA, null);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 1 violations", message);
		}
	}

	@Test
	public void testMethodInterceptedInvalidParameterMethodTwoParameterOneSkipped() {
		try {
			methodIntercepted.twoParameterBSkipped(invalidBeanA, invalidBeanB);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 1 violations", message);
		}
	}

	@Test
	public void testClassInterceptedInvalidParameterMethodTwoParameterOneSkipped() {
		try {
			classIntercepted.twoParameterBSkipped(invalidBeanA, invalidBeanB);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 1 violations", message);
		}
	}

	@Test
	public void testMethodInterceptedInvalidParameterMethodVarParameters() {
		try {
			methodIntercepted.varParameters(invalidBeanA, invalidBeanA, validBeanA, invalidBeanA);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 3 violations", message);
		}
	}

	@Test
	public void testClassInterceptedInvalidParameterMethodVarParameter() {
		try {
			classIntercepted.varParameters(invalidBeanA, validBeanA, invalidBeanA);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 2 violations", message);
		}
	}

	@Test
	public void testMethodInterceptedInvalidParameterMethodWithDefinedGroup() {
		try {
			methodIntercepted.withGroup(invalidBeanA);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 1 violations", message);
		}
	}

	@Test
	public void testMethodInterceptedInvalidParameterMethodOtherCustomClosureDefined() {
		try {
			methodIntercepted.otherCustomClosure(invalidBeanA);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 1 violations [OTHER]", message);
		}
	}

	@Test
	public void testClassInterceptedInvalidParameterMethodOtherCustomClosureDefined() {
		try {
			classIntercepted.otherCustomClosure(invalidBeanA);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 1 violations [OTHER]", message);
		}
	}

	@Test
	public void testClassInterceptedInvalidParameterMethodWithDefinedGroup() {
		try {
			classIntercepted.withGroup(invalidBeanA);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 1 violations", message);
		}
	}
}