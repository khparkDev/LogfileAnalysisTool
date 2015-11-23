package com.khpark.log.analysis.base;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.web.context.WebApplicationContext;

/**
 * Controller 유닛 테스트할 경우 tilesConfigurer 의 scope를 request를 변경
 * (src/test/resources의 test-mvc-config.xml)
 *
 * 유닛테스트만을 위한 목적
 * 
 * @author khpark
 *
 */
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException {
		final BeanDefinition bd = beanFactory.getBeanDefinition("tilesConfigurer");
		bd.setScope(WebApplicationContext.SCOPE_REQUEST);
	}
}