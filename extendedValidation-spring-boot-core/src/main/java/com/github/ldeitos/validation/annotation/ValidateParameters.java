package com.github.ldeitos.validation.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.groups.Default;

import org.springframework.beans.factory.annotation.Qualifier;

import com.github.ldeitos.exception.ViolationException;
import com.github.ldeitos.validation.ValidationClosure;

/**
 * Does method parameters validation.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @since 0.9.2
 */
@Inherited
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface ValidateParameters {
	/**
	 * @return The group or list of groups targeted for validation (defaults to
	 *         {@link Default})
	 */
	Class<?>[] groups() default {};

	/**
	 * @return Closure qualifier to recover custom {@link ValidationClosure}
	 *         instance to be executed if validation process returns violations.<br>
	 *         If not informed, default one will be used. <br>
	 *         Default can be configured in configuration file or, if not,
	 *         built-in component will just throw {@link ViolationException}
	 *         containing violation messages.
	 * 
	 * @see ValidationClosure
	 */
	Qualifier closure() default @Qualifier("extededValidation.default.closure");
}
