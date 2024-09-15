package com.github.ldeitos.constraint;

import static com.github.ldeitos.constants.Constants.EXTENDED_VALIDATOR_QUALIFIER;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.ldeitos.test.base.BaseTest;
import com.github.ldeitos.validators.SizeValidatorImpl;

public class EmptyTest extends BaseTest {

	private static final String MENSAGEM_ESPERADA = "Empty Teste";
	
	@Autowired
	@Qualifier(EXTENDED_VALIDATOR_QUALIFIER)
	private Validator validador;

	@Test
	public void testPrimitiveArrayNull() {
		TestePrimitiveArray var = new TestePrimitiveArray();
		Set<ConstraintViolation<TestePrimitiveArray>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testPrimitiveArrayValid() {
		TestePrimitiveArray var = new TestePrimitiveArray(new int[] {});
		Set<ConstraintViolation<TestePrimitiveArray>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testPrimitiveArrayInvalid() {
		TestePrimitiveArray var = new TestePrimitiveArray(new int[] { 1, 2, 3, 4, 5 });
		Set<ConstraintViolation<TestePrimitiveArray>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testCollectionNull() {
		TesteCollection var = new TesteCollection();
		Set<ConstraintViolation<TesteCollection>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testCollectionValid() {
		TesteCollection var = new TesteCollection(new ArrayList<String>());
		Set<ConstraintViolation<TesteCollection>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testCollectionInvalid() {
		TesteCollection var = new TesteCollection(asList(true));
		Set<ConstraintViolation<TesteCollection>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testMapNull() {
		TesteMap var = new TesteMap();
		Set<ConstraintViolation<TesteMap>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMapValid() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		TesteMap var = new TesteMap(map);
		Set<ConstraintViolation<TesteMap>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testMapInvalid() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, 1);
		TesteMap var = new TesteMap(map);
		Set<ConstraintViolation<TesteMap>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test
	public void testCharSequenceNull() {
		TesteCharSequence var = new TesteCharSequence();
		Set<ConstraintViolation<TesteCharSequence>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testCharSequenceValid() {
		TesteCharSequence var = new TesteCharSequence("");
		Set<ConstraintViolation<TesteCharSequence>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}

	@Test
	public void testCharSequenceInvalid() {
		TesteCharSequence var = new TesteCharSequence("12345");
		Set<ConstraintViolation<TesteCharSequence>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}

	@Test()
	public void testInvalidType() {
		assertThrows(ValidationException.class, () -> {
			TesteInvalidType var = new TesteInvalidType();
			validador.validate(var);
		});
	}

	static class TesteInvalidType {
		@Empty(messageParameters = { "par=Teste" })
		private Integer campo = 1;
	}

	static class TesteCollection {
		@Empty(messageParameters = { "par=Teste" })
		private Collection<?> campo;

		TesteCollection() {
		}

		TesteCollection(Collection<?> val) {
			campo = val;
		}
	}

	static class TesteMap {
		@Empty(messageParameters = { "par=Teste" })
		private Map<?, ?> campo;

		TesteMap() {
		}

		TesteMap(Map<?, ?> val) {
			campo = val;
		}
	}

	static class TesteCharSequence {
		@Empty(messageParameters = { "par=Teste" })
		private CharSequence campo;

		TesteCharSequence() {
		}

		TesteCharSequence(CharSequence val) {
			campo = val;
		}
	}

	static class TestePrimitiveArray {
		@Empty(messageParameters = { "par=Teste" })
		private int[] campo;

		TestePrimitiveArray() {
		}

		TestePrimitiveArray(int[] val) {
			campo = val;
		}
	}

	@Override
	protected Class<?> getClassOnTest() {
		return SizeValidatorImpl.class;
	}
}