package org.bighao.spring.beanlifecycle.component;

import org.bighao.spring.beanlifecycle.listener.TestEvent;
import org.springframework.asm.Handle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @author: bighao周启豪
 * @date 2021/1/28 14:26
 * <p>
 * bean的生命周期 - 测试bean
 * <p>
 * 跟踪完整的bean生命周期
 */
@Component
public class LifeCycleObserver implements
		BeanFactoryAware,
		ApplicationContextAware,
		ImportAware,
		InitializingBean,
		DisposableBean {

	public LifeCycleObserver() {
		System.out.println("bean的生命周期-lifeCycleObserver-实例化完成...");
	}

	@Autowired
	private LifeCycleTracker lifeCycleTracker;

	private BeanFactory beanFactory;

	private ApplicationContext applicationContext;

	private AnnotationAttributes annotationAttributes;

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	@PostConstruct
	public void init1() {
		System.out.println("\tbean的生命周期-lifeCycleObserver-6.3-初始化阶段-执行@PostConstruct方法集-init1");
		// spring事件传播机制
		System.out.println("spring事件传播机制，可以在这里传播bean创建初始化好的信息");
		Map<String, String> map = new HashMap<>();
		map.put("event", "LifeCycleObserver初始化完成");
		applicationContext.publishEvent(new TestEvent(map));
	}

	@PostConstruct
	public void init2() {
		System.out.println("\tbean的生命周期-lifeCycleObserver-6.3-初始化阶段-执行@PostConstruct方法集-init2");
	}

	@PreDestroy
	public void destory() {}

	@Override
	public String toString() {
		return "LifeCycleObserver{" +
				"type='" + type + '\'' +
				'}';
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("bean的生命周期-lifeCycleObserver-属性注入完成，初始化前阶段-回调BeanFactory相关的Aware接口-BeanNameAware、BeanClassLoaderAware、BeanFactoryAware-为Bean实例对象包装相关属性，如名称，类加载器，所属容器等信息");
		this.beanFactory = beanFactory;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("\tbean的生命周期-lifeCycleObserver-6.1-属性注入完成，初始化前阶段-回调ApplicationContext相关的Aware-这些是通过第6次beanPostProcessor来进行回调的");
		this.applicationContext = applicationContext;
	}

	/**
	 * 这里要配合@Import使用，@Import标注的地方要有@Configuration注解才会回调ImportAware
	 */
	@Override
	public void setImportMetadata(AnnotationMetadata importMetadata) {
		this.annotationAttributes = AnnotationAttributes.fromMap(
				importMetadata.getAnnotationAttributes(ComponentScan.class.getName(), false));
		System.out.println("\tbean的生命周期-lifeCycleObserver-6.2-属性注入完成，初始化前阶段-回调ImportAware-这个要配合@Import使用，是通过ConfigurationClassPostProcessor$ImportAwareBeanPostProcessor进行回调的");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("bean的生命周期-lifeCycleObserver-初始化阶段-回调InitializingBean#afterPropertiesSet和xml中配置的init-method, spring初始化回调bean的方式有三种(@PostConstruct、InitializingBean、init-method)，三种可以同时存在");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("bean的生命周期-lifeCycleObserver-销毁前回调DisposableBean#destroy-bean的生命周期完全结束，但注意bean只是不存在spring的IOC容器了，不代表不存在JVM中");
	}
}
