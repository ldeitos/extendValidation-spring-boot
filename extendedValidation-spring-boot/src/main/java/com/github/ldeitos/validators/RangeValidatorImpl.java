package com.github.ldeitos.validators;

import static com.github.ldeitos.constants.Constants.PROTOTYPE_SCOPE;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.ldeitos.constraint.Range;

@Component
@Scope(PROTOTYPE_SCOPE)
public class RangeValidatorImpl extends NumberComparativeValidator<Range> implements RangeValidator {

	private long min;

	private long max;

	@Override
	public void initialize(Range constraintAnnotation) {
		min = constraintAnnotation.min();
		max = constraintAnnotation.max();
	}

	@Override
	protected boolean compareValid(Number n) {
		boolean get = true;
		Class<? extends Number> numberClass = n.getClass();

		if (BigDecimal.class.isAssignableFrom(numberClass)) {
			BigDecimal value = BigDecimal.class.cast(n);
			get = value.compareTo(BigDecimal.valueOf(min)) >= 0
			    && value.compareTo(BigDecimal.valueOf(max)) <= 0;
		} else if (BigInteger.class.isAssignableFrom(numberClass)) {
			BigInteger value = BigInteger.class.cast(n);
			get = value.compareTo(BigInteger.valueOf(min)) >= 0
				&& value.compareTo(BigInteger.valueOf(max)) <= 0;
		} else {
			long value = n.longValue();
			get = value >= min && value <= max;
		}

		return get;
	}

}
