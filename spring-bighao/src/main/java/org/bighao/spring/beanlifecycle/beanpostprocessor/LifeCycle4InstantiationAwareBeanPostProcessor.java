package org.bighao.spring.beanlifecycle.beanpostprocessor;

import org.bighao.spring.beanlifecycle.component.LifeCycleTracker;
import org.bighao.spring.beanlifecycle.component.Observer;
import org.bighao.spring.beanlifecycle.component.TrackerHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;

/**
 * @version 1.0
 * @author: bighao周启豪
 * @date 2022/8/11 20:57
 */
public class LifeCycle4InstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

	/**
	 * bean的生命周期 第四次 回调 - 实例化后 spring对bean进行依赖注入前 拦截spring的属性填充流程
	 * 这里可以实现对bean的属性注入。
	 * 这里返回false，spring内部不再对bean进行依赖注入,也就是说属性注入阶段就完成了，直接进入初始化阶段
	 * 这里返回true，spring内部还会继续进行依赖注入
	 *
	 * @param bean     the bean instance created, with properties not having been set yet
	 * @param beanName the name of the bean
	 * @return
	 * @throws BeansException
	 */
	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		// bean的生命周期日志跟踪
		if (ObjectUtils.nullSafeEquals("lifeCycleObserver", beanName)) {
			System.out.println("bean的生命周期-lifeCycleObserver-4-属性注入前-扫描并缓存bean的注入信息-MergedBeanDefinitionPostProcessor#postProcessMergedBeanDefinition");
		}

		// bean进行自定义依赖注入逻辑
		if (ObjectUtils.nullSafeEquals("lifeCycleTracker", beanName) &&
				LifeCycleTracker.class.equals(bean.getClass())) {
			// return false后 对象不允许属性赋值(填入) (配置元信息 -> 属性值)
			LifeCycleTracker tracker = (LifeCycleTracker) bean;
			tracker.setTrackerName("by postProcessAfterInstantiation");
			return false;
		}

		return true;
	}


}