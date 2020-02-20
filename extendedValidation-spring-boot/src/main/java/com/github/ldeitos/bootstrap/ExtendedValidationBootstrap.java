package com.github.ldeitos.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.github.ldeitos.exception.InvalidConfigurationException;
import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;
import com.github.ldeitos.validation.impl.configuration.Configuration;

/**
 * ExtendedValidation engine bootstrap. Load ExtendedValidation configurations.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @since 0.9.3
 */
@Component
public class ExtendedValidationBootstrap {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExtendedValidationBootstrap.class);

	@EventListener
	public void onApplicationEvent(ContextStartedEvent event) {
		if (Configuration.isUnloaded()) {
			ConfigInfoProvider cp = SBContext.get(ConfigInfoProvider.class);
			try {
				LOGGER.info("Loading ExtendedValidation configurations.");
				Configuration.load(cp);
				LOGGER.info("ExtendedValidation configurations sucefully loaded.");
			} catch (InvalidConfigurationException e) {
				LOGGER.error("Error loading configuration. Audit process will not work.", e);
			}
		}
	}

}
