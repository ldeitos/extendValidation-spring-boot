package com.github.ldeitos.validation.impl.interpolator;

import com.github.ldeitos.bootstrap.SBContext;
import com.github.ldeitos.validation.MessagesSource;
import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;
import com.github.ldeitos.validation.impl.configuration.Configuration;

public class BaseInterpolator {

	/**
	 * Messages source.
	 */
	private MessagesSource messageSource;

	public MessagesSource getMessageSource() {
		if (messageSource == null) {
			ConfigInfoProvider configProvider = SBContext.get(ConfigInfoProvider.class);
			Configuration configuration = Configuration.getConfiguration(configProvider);

			messageSource = configuration.getConfiguredMessagesSource();
		}

		return messageSource;
	}

}
