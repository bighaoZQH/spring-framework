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
 * @date 2021/1/28 14:59
 */
public class LifeCycle1InstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

	/**
	 * InstantiationAwareBeanPostProcessor这个后置处理器是spring bean的生命周期中最先执行的
	 */

	/**
	 * bean的生命周期 第一次 回调 - 实例化前
	 * <p>
	 * 在目标对象实例化之前调用，方法的返回值类型是Object，我们可以返回任何类型的值。
	 * 由于这个时候目标对象还未实例化，所以这个返回值可以用来代替原本该生成的目标对象的实例(一般都是代理对象)。
	 * 如果该方法的返回值代替原本该生成的目标对象，后续只有postProcessAfterInitialization方法会调用，
	 * 其它方法不再调用；否则按照正常的流程走
	 */
	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		if (ObjectUtils.nullSafeEquals("lifeCycleObserver", beanName)) {
			System.out.println("bean的生命周期-lifeCycleObserver-1-实例化前-是否进行自定义初始化-InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation");
		}
		if (ObjectUtils.nullSafeEquals("observer", beanName) && Observer.class.equals(beanClass)) {
			// 把配置完成的LifeCycleObserver对象覆盖掉
			Observer observer = new Observer();
			observer.setName("BeforeInstantiation，observer这个bean是在第一次bean的后置处理器中手动初始化的");
			return observer;
		}
		// 不满足条件return null，然后ioc容器会继续进行bean的实例化操作
		return null;
	}


}