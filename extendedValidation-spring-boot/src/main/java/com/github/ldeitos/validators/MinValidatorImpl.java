package com.github.ldeitos.validators;

import static com.github.ldeitos.constants.Constants.PROTOTYPE_SCOPE;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.ldeitos.constraint.Min;

@Component
@Scope(PROTOTYPE_SCOPE)
public class MinValidatorImpl extends  NumberComparativeValidator<Min> 
	implements MinValidator {
	
	private long min;
			
	public void initialize(Min constraintAnnotation) {
		this.min = constraintAnnotation.value();
	}


	@Override
	protected boolean compareValid(Number n) {
		boolean get = true;
		Class<? extends Number> numberClass = n.getClass();

		if(BigDecimal.class.isAssignableFrom(numberClass)){
			BigDecimal value = BigDecimal.class.cast(n);
			get = value.compareTo(BigDecimal.valueOf(min)) >= 0;
		} else if(BigInteger.class.isAssignableFrom(numberClass)){
			BigInteger value = BigInteger.class.cast(n);
			get = value.compareTo(BigInteger.valueOf(min)) >= 0;
		} else {
			long value =  n.longValue();
			get = value >= min;
		}
		
		return get;
	}

}
