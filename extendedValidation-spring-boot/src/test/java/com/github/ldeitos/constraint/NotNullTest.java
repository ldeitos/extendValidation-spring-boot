package com.github.ldeitos.constraint;

import static com.github.ldeitos.constants.Constants.EXTENDED_VALIDATOR_QUALIFIER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.ldeitos.test.base.BaseTest;

public class NotNullTest extends BaseTest {
	
	private static final String MENSAGEM_ESPERADA = "NotNull Teste";

	@Autowired
	@Qualifier(EXTENDED_VALIDATOR_QUALIFIER)
	private Validator validador;
		
	@Test
	public void testNullValue(){
		Teste var = new Teste("");
		Set<ConstraintViolation<Teste>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testNotNullValue(){
		Teste var = new Teste();
		Set<ConstraintViolation<Teste>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	static class Teste {
		@NotNull(messageParameters = {"par=Teste"})
		private String campo;
		
		Teste(){
		}
		
		Teste(String val){
			campo = val;
		}
	}
}
