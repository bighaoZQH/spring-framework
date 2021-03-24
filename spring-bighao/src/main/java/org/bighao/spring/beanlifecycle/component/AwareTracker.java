package org.bighao.spring.beanlifecycle.component;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @author: bighao周启豪
 * @date 2021/2/1 18:09
 * <p>
 * 跟踪aware
 */
@Component
public class AwareTracker implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware {

	private ClassLoader classLoader;

	private BeanFactory beanFactory;

	private String beanName;

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public String getBeanName() {
		return beanName;
	}

	@Override
	public String toString() {
		return "AwareHolder{" +
				"classLoader=" + classLoader +
				", beanFactory=" + beanFactory +
				", beanName=" + beanName +
				'}';
	}
}
