package com.github.ldeitos.constraint;

import static com.github.ldeitos.constants.Constants.EXTENDED_VALIDATOR_QUALIFIER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.ldeitos.test.base.BaseTest;

public class AssertTrueTest extends BaseTest {
	
	private static final String MENSAGEM_ESPERADA = "AssertTrue Teste";

	@Autowired
	@Qualifier(EXTENDED_VALIDATOR_QUALIFIER)
	private Validator validador;
	
	@Test
	public void testTrueValue(){
		TestePrimitive var = new TestePrimitive(true);
		Set<ConstraintViolation<TestePrimitive>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testFalseValue(){
		TestePrimitive var = new TestePrimitive();
		Set<ConstraintViolation<TestePrimitive>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testTrueObjectValue(){
		TesteObject var = new TesteObject(true);
		Set<ConstraintViolation<TesteObject>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testNullObjectValue(){
		TesteObject var = new TesteObject();
		Set<ConstraintViolation<TesteObject>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testFalseObjectValue(){
		TesteObject var = new TesteObject(false);
		Set<ConstraintViolation<TesteObject>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	static class TestePrimitive {
		@AssertTrue(messageParameters = {"par=Teste"})
		private boolean campo;
		
		TestePrimitive(){
		}
		
		TestePrimitive(boolean val){
			campo = val;
		}
	}
	
	static class TesteObject {
		@AssertTrue(messageParameters = {"par=Teste"})
		private Boolean campo;
		
		TesteObject(){
		}
		
		TesteObject(boolean val){
			campo = val;
		}
	}
}
