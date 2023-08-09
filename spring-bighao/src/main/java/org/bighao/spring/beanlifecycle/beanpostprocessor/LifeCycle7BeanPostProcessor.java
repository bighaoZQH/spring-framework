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
public class LifeCycle7BeanPostProcessor implements BeanPostProcessor, PriorityOrdered {

	/**
	 * 初始化后回调 bean的生命周期 第7次回调
	 * <p>
	 * 注意这里如果我们返回null的话，那么在这个处理器后面的处理器就不会再执行了。 所以直接返回bean
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// bean的生命周期日志跟踪
		if (ObjectUtils.nullSafeEquals("lifeCycleObserver", beanName)) {
			System.out.println("bean的生命周期-lifeCycleObserver-7-此时初始化已经完成 进行初始化后回调-非循环依赖的aop也在此完成-BeanPostProcessor#postProcessAfterInitialization");

			// 以下日志不是在这个回调中实现的，只是为了打印日志
			System.out.println("bean的生命周期-lifeCycleObserver-注册bean的销毁-DisposableBean#destory");
			System.out.println("bean的生命周期-lifeCycleObserver-将创建好的bean从三级和二级缓存中移除，并入一级缓存中,bean创建完成");
		}
		return bean;
	}

	@Override
	public int getOrder() {
		// 把优先级调到最高，方便看日志
		return 0;
	}
}
