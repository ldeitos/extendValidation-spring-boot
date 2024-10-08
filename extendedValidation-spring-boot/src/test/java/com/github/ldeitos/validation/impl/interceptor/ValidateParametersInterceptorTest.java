package com.github.ldeitos.validation.impl.interceptor;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.ldeitos.exception.ViolationException;
import com.github.ldeitos.test.base.BaseTest;
import com.github.ldeitos.test.base.stubs.TestBeanA;
import com.github.ldeitos.test.base.stubs.TestBeanB;
import com.github.ldeitos.test.base.stubs.TestInterceptedClassValidation;
import com.github.ldeitos.test.base.stubs.TestInterceptedMethodsValidation;
import com.github.ldeitos.validation.Message;

public class ValidateParametersInterceptorTest extends BaseTest {
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
		} catch (ViolationException e) {
			Set<Message> messages = e.getMessages();
			assertFalse(messages.isEmpty());

			assertHasMessages(messages, "Invalid TestBeanA field");
		}
	}

	@Test
	public void testClassInterceptedInvalidParameterMethodOneParameter() {
		try {
			classIntercepted.oneParameter(invalidBeanA);
		} catch (ViolationException e) {
			Set<Message> messages = e.getMessages();
			assertFalse(messages.isEmpty());

			assertHasMessages(messages, "Invalid TestBeanA field");
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
		} catch (ViolationException e) {
			Set<Message> messages = e.getMessages();
			assertFalse(messages.isEmpty());

			assertHasMessages(messages, "Invalid TestBeanA field", "Invalid TestBeanB field");
		}
	}

	@Test
	public void testClassInterceptedInvalidParameterMethodTwoParameter() {
		try {
			classIntercepted.twoParameter(invalidBeanA, invalidBeanB);
		} catch (ViolationException e) {
			Set<Message> messages = e.getMessages();
			assertFalse(messages.isEmpty());

			assertHasMessages(messages, "Invalid TestBeanA field", "Invalid TestBeanB field");
		}
	}

	@Test
	public void testMethodInterceptedInvalidParameterMethodTwoParameterOneNull() {
		try {
			methodIntercepted.twoParameter(null, invalidBeanB);
		} catch (ViolationException e) {
			Set<Message> messages = e.getMessages();
			assertFalse(messages.isEmpty());

			assertHasMessages(messages, "Invalid TestBeanB field");
		}
	}

	@Test
	public void testClassInterceptedInvalidParameterMethodTwoParameterOneNull() {
		try {
			classIntercepted.twoParameter(invalidBeanA, null);
		} catch (ViolationException e) {
			Set<Message> messages = e.getMessages();
			assertFalse(messages.isEmpty());

			assertHasMessages(messages, "Invalid TestBeanA field");
		}
	}

	@Test
	public void testMethodInterceptedInvalidParameterMethodTwoParameterOneSkipped() {
		try {
			methodIntercepted.twoParameterBSkipped(invalidBeanA, invalidBeanB);
		} catch (ViolationException e) {
			Set<Message> messages = e.getMessages();
			assertFalse(messages.isEmpty());

			assertHasMessages(messages, "Invalid TestBeanA field");
		}
	}

	@Test
	public void testClassInterceptedInvalidParameterMethodTwoParameterOneSkipped() {
		try {
			classIntercepted.twoParameterBSkipped(invalidBeanA, invalidBeanB);
		} catch (ViolationException e) {
			Set<Message> messages = e.getMessages();
			assertFalse(messages.isEmpty());

			assertHasMessages(messages, "Invalid TestBeanA field");
		}
	}

	@Test
	public void testMethodInterceptedInvalidParameterMethodVarParameters() {
		try {
			methodIntercepted.varParameters(invalidBeanA, invalidBeanA, validBeanA, invalidBeanA);
		} catch (ViolationException e) {
			Set<Message> messages = e.getMessages();
			assertFalse(messages.isEmpty());

			assertHasMessages(messages, "Invalid TestBeanA field", "Invalid TestBeanA field",
				"Invalid TestBeanA field");
		}
	}

	@Test
	public void testClassInterceptedInvalidParameterMethodVarParameter() {
		try {
			classIntercepted.varParameters(invalidBeanA, validBeanA, invalidBeanA);
		} catch (ViolationException e) {
			Set<Message> messages = e.getMessages();
			assertFalse(messages.isEmpty());

			assertHasMessages(messages, "Invalid TestBeanA field", "Invalid TestBeanA field");
		}
	}

	@Test
	public void testMethodInterceptedInvalidParameterMethodWithDefinedGroup() {
		try {
			methodIntercepted.withGroup(invalidBeanA);
		} catch (ViolationException e) {
			Set<Message> messages = e.getMessages();
			assertFalse(messages.isEmpty());

			assertHasMessages(messages, "Invalid TestBeanA otherField");
		}
	}

	@Test
	public void testClassInterceptedInvalidParameterMethodWithDefinedGroup() {
		try {
			classIntercepted.withGroup(invalidBeanA);
		} catch (ViolationException e) {
			Set<Message> messages = e.getMessages();
			assertFalse(messages.isEmpty());

			assertHasMessages(messages, "Invalid TestBeanA otherField");
		}
	}

	@Test
	public void testMethodInterceptedInvalidParameterMethodCustomClosure() {
		try {
			methodIntercepted.customClosure(invalidBeanA, invalidBeanB);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 2 violations", message);
		}
	}

	@Test
	public void testClassInterceptedInvalidParameterMethodCustomClosure() {
		try {
			classIntercepted.customClosure(invalidBeanA, invalidBeanB);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 2 violations", message);
		}
	}

	private void assertHasMessages(Set<Message> messages, String... msgs) {
		assertEquals(msgs.length, messages.size());

		Collection<String> strMsgs = messages.stream().map(m-> m.getMessage()).collect(toList());
		assertTrue(asList(msgs).containsAll(strMsgs));
	}

}
