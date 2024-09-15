package com.github.ldeitos.validators;

import static com.github.ldeitos.constants.Constants.PROTOTYPE_SCOPE;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.ldeitos.constraint.Max;

@Component
@Scope(PROTOTYPE_SCOPE)
public class MaxValidatorImpl extends  NumberComparativeValidator<Max> 
	implements MaxValidator {
	
	private long max;
			
	public void initialize(Max constraintAnnotation) {
		this.max = constraintAnnotation.value();
	}


	@Override
	protected boolean compareValid(Number n) {
		boolean let = true;
		Class<? extends Number> numberClass = n.getClass();
		
		if(BigDecimal.class.isAssignableFrom(numberClass)){
			BigDecimal value = BigDecimal.class.cast(n);
			let = value.compareTo(BigDecimal.valueOf(max)) <= 0;
		} else if(BigInteger.class.isAssignableFrom(numberClass)){
			BigInteger value = BigInteger.class.cast(n);
			let = value.compareTo(BigInteger.valueOf(max)) <= 0;
		} else {
			long value =  n.longValue();
			let = value <= max;
		}
		
		return let;
	}

}
