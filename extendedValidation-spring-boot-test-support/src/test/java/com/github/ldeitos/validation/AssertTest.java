package com.github.ldeitos.validation;

import static com.github.ldeitos.constants.Constants.EXTENDED_VALIDATOR_QUALIFIER;
import static com.github.ldeitos.validation.Assert.assertContainMessageInConstraints;
import static com.github.ldeitos.validation.Assert.assertContainMessageInMessages;
import static com.github.ldeitos.validation.Assert.assertContainsAllMessagesInConstraints;
import static com.github.ldeitos.validation.Assert.assertContainsAllMessagesInMessages;
import static com.github.ldeitos.validation.Assert.validate;
import static java.util.Arrays.asList;

import java.util.Set;

import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.ldeitos.validation.support.ToTest;

public class AssertTest extends ExtendedValidationBaseTest {

	private static final String OTHER_MESSAGE = "Other message";

	private static final String UNXPECTED = "Unxpected Message";

	private static final String EXPECTED_1 = "Must be not null";

	private static final String EXPECTED_2 = "Must have minimum lentgh size equals 5";

	@Autowired
	@Qualifier(EXTENDED_VALIDATOR_QUALIFIER)
	private Validator extendedValidator;

	@Autowired
	@Qualifier(EXTENDED_VALIDATOR_QUALIFIER)
	private jakarta.validation.Validator defaultValidator;

	@Test
	public void testAssertContainMessage() {
		ToTest toTest = new ToTest();
		Set<ConstraintViolation<ToTest>> violations = defaultValidator.validate(toTest);

		assertContainMessageInConstraints(EXPECTED_1, violations);
	}

	@Test
	public void testAssertContainsAllMessage() {
		ToTest toTest = new ToTest("test");
		Set<ConstraintViolation<ToTest>> violations = defaultValidator.validate(toTest);

		assertContainsAllMessagesInConstraints(asList(EXPECTED_1, EXPECTED_2), violations);
	}

	@Test()
	public void testAssertContainMessageWrongMessage() {
		Assertions.assertThrows(AssertionError.class, () -> {
			ToTest toTest = new ToTest();
			Set<ConstraintViolation<ToTest>> violations = defaultValidator.validate(toTest);

			assertContainMessageInConstraints(UNXPECTED, violations);
		});
	}

	@Test()
	public void testAssertContainsAllMessageOneWrongMessage() {
		Assertions.assertThrows(AssertionError.class, () -> {
			ToTest toTest = new ToTest("test");
			Set<ConstraintViolation<ToTest>> violations = defaultValidator.validate(toTest);

			assertContainsAllMessagesInConstraints(asList(EXPECTED_1, UNXPECTED), violations);
		});
	}

	@Test()
	public void testAssertContainsAllMessageOneMoreMessage() {
		Assertions.assertThrows(AssertionError.class, () -> {
			ToTest toTest = new ToTest("test");
			Set<ConstraintViolation<ToTest>> violations = defaultValidator.validate(toTest);

			assertContainsAllMessagesInConstraints(asList(EXPECTED_1, EXPECTED_2, OTHER_MESSAGE), violations);
		});
	}

	@Test
	public void testAssertContainMessageWithRunner() {
		ToTest toTest = new ToTest("test");
		assertContainMessageInConstraints(EXPECTED_1, validate(defaultValidator, toTest));
	}

	@Test
	public void testAssertContainsAllMessageWithRunner() {
		ToTest toTest = new ToTest("test");
		assertContainsAllMessagesInConstraints(asList(EXPECTED_1, EXPECTED_2),
			validate(defaultValidator, toTest));
	}

	@Test
	public void testAssertContainsAllMessageWithRunnerAndLessMessages() {
		ToTest toTest = new ToTest("test");
		assertContainsAllMessagesInConstraints(asList(EXPECTED_1), validate(defaultValidator, toTest));
	}

	@Test()
	public void testAssertContainsAllMessageOneWrongMessageWithRunner() {
		Assertions.assertThrows(AssertionError.class, () -> {
			ToTest toTest = new ToTest("test");

			assertContainsAllMessagesInConstraints(asList(EXPECTED_1, UNXPECTED),
				validate(defaultValidator, toTest));
		});
	}

	@Test()
	public void testAssertContainsAllMessageOneMoreMessageWithRunner() {
		Assertions.assertThrows(AssertionError.class, () -> {
			ToTest toTest = new ToTest("test");

			assertContainsAllMessagesInConstraints(asList(EXPECTED_1, EXPECTED_2, OTHER_MESSAGE),
				validate(defaultValidator, toTest));
		});
	}

	@Test
	public void testAssertContainMessageExtededWithRunner() {
		ToTest toTest = new ToTest("test");
		assertContainMessageInMessages(EXPECTED_1, validate(extendedValidator, toTest));
	}

	@Test
	public void testAssertContainsAllMessageExtededWithRunner() {
		ToTest toTest = new ToTest("test");
		assertContainsAllMessagesInMessages(asList(EXPECTED_1, EXPECTED_2),
			validate(extendedValidator, toTest));
	}

	@Test()
	public void testAssertContainsAllMessageExtededOneWrongMessageWithRunner() {
		Assertions.assertThrows(AssertionError.class, () -> {
			ToTest toTest = new ToTest("test");

			assertContainsAllMessagesInMessages(asList(EXPECTED_1, UNXPECTED),
				validate(extendedValidator, toTest));
		});
	}

	@Test()
	public void testAssertContainsAllMessageExtededOneMoreMessageWithRunner() {
		Assertions.assertThrows(AssertionError.class, () -> {
			ToTest toTest = new ToTest("test");

			assertContainsAllMessagesInMessages(asList(EXPECTED_1, EXPECTED_2, OTHER_MESSAGE),
				validate(extendedValidator, toTest));
		});
	}

}
