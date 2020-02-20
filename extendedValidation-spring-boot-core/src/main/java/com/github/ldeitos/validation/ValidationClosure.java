package com.github.ldeitos.validation;

import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;

import com.github.ldeitos.exception.ViolationException;

/**
 * Closure to be executed when occurs violations on objects validation during
 * validation interception.<br>
 * Custom implementations must be qualified with {@link Qualifier}.<br>
 * A default implementation also can be defined in configuration file or, if
 * not, a simple built-in component implementation will be used, this will just
 * throw a {@link ViolationException}.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 * 
 * @see Qualifier
 *
 * @since 0.9.2
 */
public interface ValidationClosure {

	/**
	 * Method to be executed when occurs violations in validation process.
	 * 
	 * @param messages
	 *            Violation messages obtained in parameters validation.
	 * @throws Exception
	 *             Allow closure implementation throw a exception.
	 */
	void proceed(Set<Message> messages) throws Exception;
}
