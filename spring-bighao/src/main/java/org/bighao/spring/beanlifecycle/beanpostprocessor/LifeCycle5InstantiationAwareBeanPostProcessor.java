package org.bighao.spring.beanlifecycle.beanpostprocessor;

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
 * @date 2022/8/11 20:58
 */
public class LifeCycle5InstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

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