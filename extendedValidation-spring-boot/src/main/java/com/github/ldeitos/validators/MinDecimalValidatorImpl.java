package com.github.ldeitos.validators;

import static com.github.ldeitos.constants.Constants.PROTOTYPE_SCOPE;

import java.math.BigDecimal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.ldeitos.constraint.DecimalMin;

@Component
@Scope(PROTOTYPE_SCOPE)
public class MinDecimalValidatorImpl extends BigDecimalComparativeValidator<DecimalMin> 
	implements MinDecimalValidator {
	private BigDecimal minValue;
	
	private boolean inclusive;

	public void initialize(DecimalMin constraintAnnotation) {
		minValue = new BigDecimal(constraintAnnotation.value());
		inclusive = constraintAnnotation.inclusive();
	}

	@Override
	protected boolean compareValid(BigDecimal n) {
		int comparassion = minValue.compareTo(n);
		return inclusive ? comparassion <= 0 : comparassion < 0;
	}

}
