package org.bighao.spring.beanlifecycle.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;

/**
 * @version 1.0
 * @author: bighao周启豪
 * @date 2021/2/2 11:38
 *
 * bean的销毁回调 可以通过实现DestructionAwareBeanPostProcessor来实现全局的bean销毁回调
 */
public class LifeCycleDestructionAwareBeanPostProcessor implements DestructionAwareBeanPostProcessor {

	@Override
	public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
		System.out.println(beanName + "即将销毁");
	}

}
