package org.bighao.spring.beanlifecycle.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Constructor;

/**
 * @description:
 * @author: ZhouQiHao bighao1996@163.com
 * @date: 2022-08-10 21:00
 */
public class LifeCycle2SmartInstantiationAwareBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {

	/**
	 * bean实例化前-选择bean初始化的构造方法，这个一般都是用spring自己内部实现的就行，除非不想用spring的那套来初始化
	 *
	 * determineCandidateConstructors方法用于选择合适的构造器，
	 * 如果类有多个构造器，可以实现这个方法选择合适的构造器并用于实例化对象；
	 * 该方法在postProcessBeforeInstantiation方法和postProcessAfterInstantiation方法之间调用，
	 * 如果postProcessBeforeInstantiation方法返回了一个新的实例代替了原本该生成的实例，那么该方法会被忽略；
	 *
	 * @param beanClass the raw class of the bean (never {@code null})
	 * @param beanName the name of the bean
	 * @return
	 * @throws BeansException
	 */
	@Override
	public Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName) throws BeansException {
		if (ObjectUtils.nullSafeEquals("lifeCycleObserver", beanName)) {
			System.out.println("bean的生命周期-lifeCycleObserver-2-实例化前-选择bean初始化的构造方法-SmartInstantiationAwareBeanPostProcessor#determineCandidateConstructors");
		}
		return SmartInstantiationAwareBeanPostProcessor.super.determineCandidateConstructors(beanClass, beanName);
	}

}
