package org.bighao.spring.beanlifecycle.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.ObjectUtils;

/**
 * @description:
 * @author: ZhouQiHao bighao1996@163.com
 * @date: 2022-08-10 23:29
 */
public class LifeCycle6BeanPostProcessor implements BeanPostProcessor, PriorityOrdered {

	/**
	 * bean生命周期 第六次回调 初始化前回调
	 *
	 * spring内部做的事情：
	 * 1. ApplicationContextAwareProcessor - 回调剩余的 内置Aware接口
	 * 2. ConfigurationClassPostProcessor$ImportAwareBeanPostProcessor - 处理 ImportAware
	 * 3. InitDestroyAnnotationBeanPostProcessor(CommonAnnotationBeanPostProcessor)- 调用 @PostConstructor 对应方法集
	 *  拿到 @PostConstructor 对应的方法集合, 进行循环调用.
	 *  这里能看到 @PostConstructor 的调用时机, 是初始化(invokeInitMethods -
	 *  这里面会调用InitializingBean#afterPropertiesSet() 和用户自定义的init-method初始化方法 )之前.
	 *
	 *  我们可以在这里实现回调自定义的aware接口
	 *
	 *  注意这里如果我们返回null的话，那么在这个处理器后面的处理器就不会再执行了。
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (ObjectUtils.nullSafeEquals("lifeCycleObserver", beanName)) {
			System.out.println("bean的生命周期-lifeCycleObserver-6-初始化前回调-回调ApplicationContext相关Aware、回调ImportAware、回调@PostConstructor方法集-BeanPostProcessor#postProcessBeforeInitialization");
		}
		// 注意这里如果我们返回null的话，那么在这个处理器后面的处理器就不会再执行了。 所以直接返回bean
		return bean;
	}

	@Override
	public int getOrder() {
		// 把优先级调到最高，方便看日志
		return 0;
	}
}
