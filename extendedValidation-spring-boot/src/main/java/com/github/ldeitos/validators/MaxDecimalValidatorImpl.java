package com.github.ldeitos.validators;

import static com.github.ldeitos.constants.Constants.PROTOTYPE_SCOPE;

import java.math.BigDecimal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.ldeitos.constraint.DecimalMax;

@Component
@Scope(PROTOTYPE_SCOPE)
public class MaxDecimalValidatorImpl extends BigDecimalComparativeValidator<DecimalMax> 
	implements MaxDecimalValidator {
	private BigDecimal maxValue;
	
	private boolean inclusive;

	public void initialize(DecimalMax constraintAnnotation) {
		maxValue = new BigDecimal(constraintAnnotation.value());
		inclusive = constraintAnnotation.inclusive();
	}

	@Override
	protected boolean compareValid(BigDecimal n) {
		int comparassion = maxValue.compareTo(n);
		return inclusive ? comparassion >= 0 : comparassion > 0;
	}

}
