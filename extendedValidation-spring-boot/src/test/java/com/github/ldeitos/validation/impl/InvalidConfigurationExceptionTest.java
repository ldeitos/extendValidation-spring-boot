package com.github.ldeitos.validation.impl;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.ldeitos.exception.InvalidConfigurationException;

public class InvalidConfigurationExceptionTest {
	
	@Test()
	public void testThrowMSG() {
		Assertions.assertThrows(InvalidConfigurationException.class, () -> {
			InvalidConfigurationException.throwNew("Teste");
		});		
	}
	
	@Test()
	public void testThrowMSGAndCause() {
		Assertions.assertThrows(RuntimeException.class, () -> {
				InvalidConfigurationException.throwNew("Teste", new RuntimeException());		
			
		});
	}
}
