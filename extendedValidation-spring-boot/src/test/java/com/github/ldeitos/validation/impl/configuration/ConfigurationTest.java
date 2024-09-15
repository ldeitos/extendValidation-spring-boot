package com.github.ldeitos.validation.impl.configuration;

import static com.github.ldeitos.constants.Constants.MESSAGE_FILES_SYSTEM_PROPERTY;
import static com.github.ldeitos.validation.impl.configuration.Configuration.getConfiguration;
import static java.lang.System.setProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.github.ldeitos.bootstrap.ExtendedValidationBootstrap;
import com.github.ldeitos.bootstrap.SBContext;
import com.github.ldeitos.validation.impl.interpolator.TestMessageSource;
import com.github.ldeitos.validation.impl.util.DefaultValidationClosure;


@SpringBootTest(classes = { ExtendedValidationBootstrap.class, SBContext.class, TestMessageSource.class,
		ConfigInfoProvider.class, DefaultValidationClosure.class })
public class ConfigurationTest {

	private static final String TEST_MESSAGE_FILE = "TestValidationMessage";

	private static final String OTHER_TEST_MESSAGE_FILE = "OtherTestValidationMessage";

	@Mock
	private ConfigInfoProvider configProvider = new ConfigInfoProvider() {
		public String getConfigFileName() {
			return "extendedValidation_configuredValidationClosure.xml";
		};
		
		protected boolean isInTest() {
			return true;
		};
	};

	private Configuration configuration;
	
	@BeforeEach
	public void setup() {
		configuration = getConfiguration(configProvider);
	}

	@Test
	public void testConfigurationWithMessageFilesCofiguredByEnvironment() {
		setProperty(MESSAGE_FILES_SYSTEM_PROPERTY, "arq1, arq2");

		assertEquals(TestMessageSource.class, configuration.getConfiguredMessagesSource().getClass());

		assertEquals(4, configuration.getConfituredMessageFiles().size());
		assertTrue(configuration.getConfituredMessageFiles().contains(TEST_MESSAGE_FILE));
		assertTrue(configuration.getConfituredMessageFiles().contains(OTHER_TEST_MESSAGE_FILE));
		assertTrue(configuration.getConfituredMessageFiles().contains("arq1"));
		assertTrue(configuration.getConfituredMessageFiles().contains("arq2"));

		System.clearProperty(MESSAGE_FILES_SYSTEM_PROPERTY);
	}

	@Test
	public void testConfigurationWithoutMessageFilesCofiguredByEnvironment() {

		assertEquals(TestMessageSource.class, configuration.getConfiguredMessagesSource().getClass());

		assertEquals(2, configuration.getConfituredMessageFiles().size());
		assertTrue(configuration.getConfituredMessageFiles().contains(TEST_MESSAGE_FILE));
		assertTrue(configuration.getConfituredMessageFiles().contains(OTHER_TEST_MESSAGE_FILE));
	}
}