package com.github.ldeitos.bootstrap;

import java.lang.annotation.Annotation;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SBContext implements ApplicationContextAware {
	private static ApplicationContext springBootAppContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		springBootAppContext = applicationContext;
	}

	public static <T> T get(Class<T> type) {
		return (T) springBootAppContext.getBean(type);
	}

	public static <T> T get(Class<T> type, Annotation... annotations) {
		return (T) springBootAppContext.getBean(type, annotations);
	}
}