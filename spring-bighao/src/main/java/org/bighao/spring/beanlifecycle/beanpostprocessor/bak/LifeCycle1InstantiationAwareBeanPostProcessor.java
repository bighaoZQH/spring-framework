package org.bighao.spring.beanlifecycle.beanpostprocessor.bak;

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

	/**
	 * bean的生命周期 第五次 回调 - 属性填充之前
	 * <p>
	 * 这里是在spring处理完默认的成员属性，应用到指定的bean之前进行回调，可以用来检查和修改属性，
	 * 最终返回的PropertyValues会应用到bean中
	 * @Autowired和@Value、@Resource等就是根据这个回调来实现最终注入依赖的属性的。
	 *
	 *  注意这一步spring会进行@Resource和@Autowire的注入，自定义返回的pvs不会进行注入
	 *  一个是CommonAnnotationBeanPostProcessor， 处理@Resource，进行注入
	 *  一个是AutowiredAnnotationBeanPostProcessor 处理@Autowired和@Value，进行注入
	 *  自定义处理器，可以返回要注入的属性，但不注入，最后再进行注入（这里你也没法注入，只能把自定义修改好后的pvs返回出去）
	 *
	 * @param pvs 当前解析出来的属性填充值
	 *            即xml中<property name="id" value="1"/>配置在的话, 那么就包含一个PropertyValues(number,1)
	 */
	@Override
	public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
		// bean的生命周期日志跟踪
		if (ObjectUtils.nullSafeEquals("lifeCycleObserver", beanName)) {
			System.out.println("bean的生命周期-lifeCycleObserver-5-属性注入前-自定义属性填充-这里会完成@Resource、@Autowired和@Value的注入，也可以返回自定义的属性注入(你也没法注入，这里只能把pvs返回出去)，但自定义的属性这里不进行注入-InstantiationAwareBeanPostProcessor#postProcessProperties");
		}

		// 检查 和 修改要注入的属性
		if (ObjectUtils.nullSafeEquals("trackerHolder", beanName) && TrackerHolder.class.equals(bean.getClass())) {

			final MutablePropertyValues propertyValues;
			if (pvs instanceof MutablePropertyValues) {
				propertyValues = (MutablePropertyValues) pvs;
			} else {
				propertyValues = new MutablePropertyValues();
			}
			// 等价于xml中的<property name="id" value="1"/> 如果同时存在，这里会覆盖xml中配置的
			propertyValues.addPropertyValue("id", "2");

			HashMap<Object, Object> map = new HashMap<>();
			map.put("name3", "c");
			map.put("name4", "d");
			propertyValues.addPropertyValue("propertyMap", map);

			// 不想覆盖的话，可以判断下
			if (!propertyValues.contains("desc")) {
				propertyValues.addPropertyValue("desc", "aaaa");
			}

			return propertyValues;
		}
		return null;
	}


	/**
	 * 从5.1开始被弃用，被postProcessProperties替代，
	 * 为了兼容考虑，如果postProcessProperties返回null，则会执行到这个方法
	 */
	/*@Override
	public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
		return InstantiationAwareBeanPostProcessor.super.postProcessPropertyValues(pvs, pds, bean, beanName);
	}*/


}