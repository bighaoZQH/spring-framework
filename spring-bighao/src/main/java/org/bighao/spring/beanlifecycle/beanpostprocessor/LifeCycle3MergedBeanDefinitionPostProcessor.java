package org.bighao.spring.beanlifecycle.beanpostprocessor;

import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.util.ObjectUtils;

/**
 * @description:
 * @author: ZhouQiHao bighao1996@163.com
 * @date: 2022-08-10 23:29
 */
public class LifeCycle3MergedBeanDefinitionPostProcessor implements MergedBeanDefinitionPostProcessor {

	/**
	 * spring在这一步进行 扫描并缓存bean的注入信息的后置处理器
	 *
	 * 主要是三个内置的bean后置处理器
	 * CommonAnnotationBeanPostProcessor 扫描PostConstruct 和 @PreDestroy @Resource
	 * AutowiredAnnotationBeanPostProcessor 扫描@Autowired 和 @Value
	 * ApplicationListenerDetector 将单例beanDefinition缓存起来 是为了后面用的. 对监听器进行过滤用的
	 */
	@Override
	public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
		if (ObjectUtils.nullSafeEquals("lifeCycleObserver", beanName)) {
			System.out.println("bean的生命周期-lifeCycleObserver-3-实例化后-扫描并缓存bean的注入信息-MergedBeanDefinitionPostProcessor#postProcessMergedBeanDefinition");

			/**
			 * 以下的逻辑不是在postProcessMergedBeanDefinition里做的，不过这个beanPostProcessor执行完后，会执行到下面的代码
			 * 为了方便打印，日志就在这边输出了
			 * addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean));
			 *
			 *  () -> getEarlyBeanReference() 是一个工厂对象，用于通过bean的后置处理器完成bean的aop代理
			 *  addSingletonFactory提前暴露一个Bean工厂，为了防止循环引用，尽早持有对象的引用
			 */
			System.out.println("bean的生命周期-lifeCycleObserver-入三级缓存-此时会暴露该对象的早期工厂对象，该工厂被添加到三级缓存中，用于完成后面的扩展性依赖注入bean，比如aop，接下来进行属性注入阶段");
		}
	}

}
