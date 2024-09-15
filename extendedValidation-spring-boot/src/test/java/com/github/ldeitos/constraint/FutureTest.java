package com.github.ldeitos.constraint;

import static com.github.ldeitos.constants.Constants.EXTENDED_VALIDATOR_QUALIFIER;
import static java.util.Calendar.DAY_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.ldeitos.test.base.BaseTest;

public class FutureTest extends BaseTest {
	
	private static final String MENSAGEM_ESPERADA = "Future Teste";

	@Autowired
	@Qualifier(EXTENDED_VALIDATOR_QUALIFIER)
	private Validator validador;
	
	@Test
	public void testDateFuture(){
		Calendar cal = Calendar.getInstance();
		cal.add(DAY_OF_YEAR, 1);
		TesteDate var = new TesteDate(cal.getTime());
		Set<ConstraintViolation<TesteDate>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testDatePast(){
		Calendar cal = Calendar.getInstance();
		cal.add(DAY_OF_YEAR, -1);
		TesteDate var = new TesteDate(cal.getTime());
		Set<ConstraintViolation<TesteDate>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	@Test
	public void testCalendarFuture(){
		Calendar cal = Calendar.getInstance();
		cal.add(DAY_OF_YEAR, 1);
		TesteCalendar var = new TesteCalendar(cal);
		Set<ConstraintViolation<TesteCalendar>> violacoes = validador.validate(var);
		assertTrue(violacoes.isEmpty());
	}
	
	@Test
	public void testCalendarPast(){
		Calendar cal = Calendar.getInstance();
		cal.add(DAY_OF_YEAR, -1);
		TesteCalendar var = new TesteCalendar(cal);
		Set<ConstraintViolation<TesteCalendar>> violacoes = validador.validate(var);
		assertFalse(violacoes.isEmpty());
		assertEquals(1, violacoes.size());
		assertEquals(MENSAGEM_ESPERADA, violacoes.iterator().next().getMessage());
	}
	
	static class TesteDate {
		@Future(messageParameters = {"par=Teste"})
		private Date data;
		
		TesteDate(Date val){
			data = val;
		}
	}
	
	static class TesteCalendar {
		@Future(messageParameters = {"par=Teste"})
		private Calendar data;
		
		TesteCalendar(Calendar val){
			data = val;
		}
	}
}
