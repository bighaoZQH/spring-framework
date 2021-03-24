package org.bighao.spring.beanlifecycle;

import org.bighao.spring.beanlifecycle.beanpostprocessor.LifeCycle1InstantiationAwareBeanPostProcessor;
import org.bighao.spring.beanlifecycle.beanpostprocessor.LifeCycleDestructionAwareBeanPostProcessor;
import org.bighao.spring.beanlifecycle.component.*;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.*;

/**
 * @version 1.0
 * @author: bighao周启豪
 * @date 2021/1/28 14:59
 */
@Configuration
@ComponentScan(basePackages = {"org.bighao.spring.beanlifecycle.*"})
// @ImportResource：通过locations属性加载对应的xml配置文件，同时需要配合@Configuration注解一起使用，定义为配置类
@ImportResource({"classpath:life-cycle-beans.xml"})
public class RunBeanLidrCycleTest {

	/**
	 * Spring Bean的生命周期
	 *
	 * 01.配置bean并解析元数据 bean -> beanDefinition
	 * 02.将beanDefinition注册到ioc容器
	 * 03.合并beanDefinition ==> RootDefinition
	 * 04.通过类加载器加载bean的class文件 beanDefinition.beanClass字段
	 * 05.bean实例化 前 - 第一次调用bean的后置处理器 - bean实例化前 - 是否进行自定义初始化 - InstantiationAwareBeanPostProcessor接口的postProcessBeforeInstantiation方法
	 * 06.bean实例化 前 - 如果有Supplier函数式接口来实例化对象，就通过这个来实例化对象，
	 * 07.bean实例化 前 - 如果有工厂方法，则通过工厂方法来实例化对象 @Bean就是在这里实例化的
	 * 08.bean实例化 时 - 第二次调用bean的后置处理器 - 选择bean的构造方法列表 - AutowiredAnnotationBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor extend InstantiationAwareBeanPostProcessor
	 * 09.bean实例化 后 - 第三次调用bean的后置处理器 - 扫描并缓存bean的注入信息的后置处理器(@Autowired、@Resource、@Value)  MergedBeanDefinitionPostProcessor->postProcessMergedBeanDefinition
	 * 10.bean实例化 后 - 第四次调用bean的后置处理器 - 为了处理循环依赖，尽早持有对象的引用，将该对象暴露给工厂 - 同时aop代理在此处完成 - SmartInstantiationAwareBeanPostProcessor - getEarlyBeanReference
	 * 11.bean属性填充 前 - 第五次调用bean的后置处理器 - 是否进行bean的属性赋值，return fasle spring不进行属性填充，由我们自己实现属性注入的逻辑 - InstantiationAwareBeanPostProcessor接口的postProcessAfterInstantiation方法
	 * 12.bean属性填充 时 - 第六次调用bean的后置处理器 - 调用内置的两个处理器进行@Resource和@Autowired的填充，如果有自己实现的，也会调用自己的填充属性，最后注入到bean实例中  InstantiationAwareBeanPostProcessor接口的postProcessProperties()方法
	 * 13.bean初始化 前 - 第七次调用bean的后置处理器 - 回调各种aware接口、执行@PostConstruct方法集      AbstractAutowireCapableBeanFactory==>applyBeanPostProcessorsBeforeInitialization()方法中进行回调
	 * 14.bean初始化 前 - 如果实现了InitializingBean接口，则执行afterPropertiesSet()方法，和init-method
	 * 15.bean初始化 后 - 第8次调用bean的后置处理器 - InstantiationAwareBeanPostProcessor的postProcessAfterInitialization()方法
	 * 16.bean销毁   前 - 第9次调用bean的后置处理器 - InitDestroyAnnotationBeanPostProcessor的postProcessBeforeDestruction()方法
	 * 为什么DestructionAwareBeanPostProcessor中没有after方法？
	 * 因为执行after的时候bean都没了，Spring认为你也没必要做什么扩展了
	 */
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();

		// bean的生命周期 - 第一次调用bean的后置处理器 - postProcessBeforeInstantiation方法
		beanFactory.addBeanPostProcessor(new LifeCycle1InstantiationAwareBeanPostProcessor());
		beanFactory.addBeanPostProcessor(new LifeCycleDestructionAwareBeanPostProcessor());

		// 注册并扫描配置类
		context.register(RunBeanLidrCycleTest.class);
		// 启动刷新spring上下文环境
		context.refresh();

		LifeCycleObserver lifeCycleObserver = (LifeCycleObserver) context.getBean("lifeCycleObserver");
		System.out.println(lifeCycleObserver);
		LifeCycleTracker lifeCycleTracker = (LifeCycleTracker) context.getBean("lifeCycleTracker");
		System.out.println(lifeCycleTracker);
		TrackerHolder trackerHolder = (TrackerHolder) context.getBean("trackerHolder");
		System.out.println(trackerHolder);
		AwareTracker awareTracker = (AwareTracker) context.getBean("awareTracker");
		System.out.println(awareTracker);

		AtBeanTracker atBeanTracker = (AtBeanTracker) context.getBean("atBeanTracker");
		System.out.println(atBeanTracker);

		// 执行bean销毁(注意bean的销毁是指在ioc容器内被销毁，不代表在java环境内被销毁，不代表会被GC回收)
		beanFactory.destroyBean("trackerHolder");
		System.out.println(trackerHolder);
	}


	/**
	 * 该@Bean会被解析成工厂方法
	 */
	@Bean
	public AtBeanTracker atBeanTracker() {
		AtBeanTracker atBeanTracker = new AtBeanTracker();
		atBeanTracker.setId(1);
		atBeanTracker.setName("@Bean tracker");
		return atBeanTracker;
	}


}
