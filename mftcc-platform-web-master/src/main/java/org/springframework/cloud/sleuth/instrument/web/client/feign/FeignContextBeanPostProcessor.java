
package org.springframework.cloud.sleuth.instrument.web.client.feign;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.cloud.netflix.feign.FeignContext;
import org.springframework.core.PriorityOrdered;


final class FeignContextBeanPostProcessor implements DestructionAwareBeanPostProcessor,PriorityOrdered {

	private final BeanFactory beanFactory;
	private TraceFeignObjectWrapper traceFeignObjectWrapper;

	public FeignContextBeanPostProcessor(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		if (bean instanceof FeignContext && !(bean instanceof TraceFeignContext)) {
			return new TraceFeignContext(getTraceFeignObjectWrapper(), (FeignContext) bean);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	private TraceFeignObjectWrapper getTraceFeignObjectWrapper() {
		if (this.traceFeignObjectWrapper == null) {
			this.traceFeignObjectWrapper = this.beanFactory.getBean(TraceFeignObjectWrapper.class);
		}
		return this.traceFeignObjectWrapper;
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
		
	}

	@Override
	public boolean requiresDestruction(Object bean) {
		// TODO Auto-generated method stub
		return false;
	}
}
