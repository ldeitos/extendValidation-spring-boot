package com.github.ldeitos.validation.impl.interpolator;

import static com.github.ldeitos.test.base.GeneralTestConfiguration.ENABLE_REAL_IMPLEMETATION;

import java.util.Locale;

import org.springframework.stereotype.Component;

import com.github.ldeitos.validation.MessagesSource;

@Component
public class TestMessageSource extends MultipleBundlesSource implements MessagesSource {

	@Override
	public String getMessage(String key) {

		if (ENABLE_REAL_IMPLEMETATION) {
			return super.getMessage(key);
		}

		return key;
	}

	@Override
	public String getMessage(String key, Locale locale) {

		if (ENABLE_REAL_IMPLEMETATION) {
			return super.getMessage(key, locale);
		}

		return key;
	}

}
