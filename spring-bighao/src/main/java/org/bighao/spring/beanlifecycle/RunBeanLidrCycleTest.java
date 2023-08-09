package org.bighao.spring.beanlifecycle;

import org.bighao.spring.beanlifecycle.beanpostprocessor.*;
import org.bighao.spring.beanlifecycle.component.*;
import org.bighao.spring.beanlifecycle.importxxx.MyImportBeanDefinitionRegistrar;
import org.bighao.spring.beanlifecycle.importxxx.MyImportSelector;
import org.bighao.spring.beanlifecycle.importxxx.MyLogImportAware;
import org.bighao.spring.domain.anno.EnableLogPrefix;
import org.bighao.spring.domain.entity.UserEntity;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.*;

/**
 * @version 1.0
 * @author: bighao周启豪
 * @date 2021/1/28 14:59
 *
 * component包说明：
 * 观察bean的生命周期，包括aware，初始化，销毁等，主要以LifeCycleObserver为主，AwareTracker和TrackerHolder的都整合到这个中了
 * AtBeanTracker看@Bean注入的
 * LifeCycleTracker是xml注入的
 *
 */
@Configuration
@ComponentScan(basePackages = {"org.bighao.spring.beanlifecycle"})
// @ImportResource：通过locations属性加载对应的xml配置文件，同时需要配合@Configuration注解一起使用，定义为配置类
@ImportResource({"classpath:life-cycle-beans.xml"})
/**
 * 注解@Import注入Bean的方式有如下三种
 * 基于Configuration Class(实现ImportAware)
 * 基于ImportSelector接口
 * 基于ImportBeanDefinitionRegistrar接口
 */
@Import({MyImportSelector.class, MyImportBeanDefinitionRegistrar.class, LifeCycleObserver.class})
// 通过ImportAware 自定义实现enable开关
@EnableLogPrefix(prefix = "importAware实现EnableXxx")
@EnableAspectJAutoProxy
public class RunBeanLidrCycleTest {

	/**
	 * Spring Bean的生命周期
	 * <p>
	 * 01.配置bean并解析元数据 bean -> beanDefinition
	 * 02.将beanDefinition注册到ioc容器
	 * 03.合并beanDefinition ==> RootDefinition
	 * 04.通过类加载器加载bean的class文件 beanDefinition.beanClass字段
	 * 05.bean实例化 前 - 第一次调用bean的后置处理器 - bean实例化前 - 是否进行自定义初始化 - InstantiationAwareBeanPostProcessor接口的postProcessBeforeInstantiation方法
	 * 06.bean实例化 前 - 如果有Supplier函数式接口来实例化对象，就通过这个来实例化对象，
	 * 07.bean实例化 前 - 如果有工厂方法，则通过工厂方法来实例化对象 @Bean就是在这里实例化的
	 * 08.bean实例化 时 - 第二次调用bean的后置处理器 - 选择bean的构造方法列表 - AutowiredAnnotationBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor extend InstantiationAwareBeanPostProcessor
	 * 09.bean实例化 后 - 第三次调用bean的后置处理器 - 扫描并缓存bean的注入信息的后置处理器(@Autowired、@Resource、@Value)  MergedBeanDefinitionPostProcessor->postProcessMergedBeanDefinition
	 * --.接下来将bean实例通过singletonFactory工厂方式添加到三级缓存
	 * --.这里并没有去执行 10.bean实例化 后 - 第四次调用bean的后置处理器 - 为了处理循环依赖，尽早持有对象的引用，将该对象暴露给工厂 - 同时aop代理在此处完成 - SmartInstantiationAwareBeanPostProcessor - getEarlyBeanReference
	 * 11.bean属性填充 前 - 第五次调用bean的后置处理器 - 是否进行bean的属性赋值，return fasle spring不进行属性填充，由我们自己实现属性注入的逻辑 - InstantiationAwareBeanPostProcessor接口的postProcessAfterInstantiation方法
	 * 12.bean属性填充 时 - 第六次调用bean的后置处理器 - 调用内置的两个处理器进行@Resource和@Autowired的填充，如果有自己实现的，也会调用自己的填充属性，最后注入到bean实例中  InstantiationAwareBeanPostProcessor接口的postProcessProperties()方法
	 * 13.bean初始化 前 - 第七次调用bean的后置处理器 - 回调各种aware接口、执行@PostConstruct方法集      AbstractAutowireCapableBeanFactory==>applyBeanPostProcessorsBeforeInitialization()方法中进行回调
	 * 14.bean初始化 前 - 如果实现了InitializingBean接口，则执行afterPropertiesSet()方法，和init-method
	 * 15.bean初始化 后 - 第8次调用bean的后置处理器 - InstantiationAwareBeanPostProcessor的postProcessAfterInitialization()方法
	 * 16.bean销毁   前 - 第9次调用bean的后置处理器 - InitDestroyAnnotationBeanPostProcessor的postProcessBeforeDestruction()方法
	 * 为什么DestructionAwareBeanPostProcessor中没有after方法？
	 * 因为执行after的时候bean都没了，Spring认为你也没必要做什么扩展了
	 *
	 *
	 *
	 *
	 * 日志跟踪:
	 * 进行beanFactory的回调-侧重于创建自定义的bd-MyBeanDefinitionRegistryPostProcessor#postProcessBeanDefinitionRegistry...
	 * 进行beanFactory的回调-侧重于对已有bd属性的修改-MyBeanFactoryPostProcessor#postProcessBeanFactory...
	 * getBean流程：
	 * 首先合并bean定义，就是父子bean定义信息合并。
	 * 看有没有depends-on的依赖，有的话实例化依赖的bean。
	 * bean的生命周期-lifeCycleObserver-1-实例化前-是否进行自定义初始化-InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation
	 * bean的生命周期-lifeCycleObserver-2-实例化前-选择bean初始化的构造方法-SmartInstantiationAwareBeanPostProcessor#determineCandidateConstructors
	 * bean的生命周期-lifeCycleObserver-实例化完成...
	 * bean的生命周期-lifeCycleObserver-3-实例化后-扫描并缓存bean的注入信息-MergedBeanDefinitionPostProcessor#postProcessMergedBeanDefinition
	 * bean的生命周期-lifeCycleObserver-入三级缓存-此时会暴露该对象的早期工厂对象，该工厂被添加到三级缓存中，用于完成后面的扩展性依赖注入bean，比如aop，接下来进行属性注入阶段
	 * bean的生命周期-lifeCycleObserver-4-属性注入前-扫描并缓存bean的注入信息-MergedBeanDefinitionPostProcessor#postProcessMergedBeanDefinition
	 * bean的生命周期-lifeCycleObserver-5-属性注入前-自定义属性填充-这里会完成@Resource、@Autowired和@Value的注入，也可以返回自定义的属性注入(你也没法注入，这里只能把pvs返回出去)，但自定义的属性这里不进行注入-InstantiationAwareBeanPostProcessor#postProcessProperties
	 * LifeCycleTracker===>afterPropertiesSet() execute
	 * LifeCycleTracker===>xmlInitMethod() execute
	 * bean的生命周期-lifeCycleObserver-属性注入完成，初始化前阶段-回调BeanFactory相关的Aware接口-BeanNameAware、BeanClassLoaderAware、BeanFactoryAware-为Bean实例对象包装相关属性，如名称，类加载器，所属容器等信息
	 * bean的生命周期-lifeCycleObserver-6-初始化前回调-回调ApplicationContext相关Aware、回调ImportAware、回调@PostConstructor方法集-BeanPostProcessor#postProcessBeforeInitialization
	 * 	bean的生命周期-lifeCycleObserver-6.1-属性注入完成，初始化前阶段-回调ApplicationContext相关的Aware-这些是通过第6次beanPostProcessor来进行回调的
	 * 	bean的生命周期-lifeCycleObserver-6.2-属性注入完成，初始化前阶段-回调ImportAware-这个要配合@Import使用，是通过ConfigurationClassPostProcessor$ImportAwareBeanPostProcessor进行回调的
	 * 	bean的生命周期-lifeCycleObserver-6.3-初始化阶段-执行@PostConstruct方法集-init1
	 * 	bean的生命周期-lifeCycleObserver-6.3-初始化阶段-执行@PostConstruct方法集-init2
	 * bean的生命周期-lifeCycleObserver-初始化阶段-回调InitializingBean#afterPropertiesSet和xml中配置的init-method, spring初始化回调bean的方式有三种(@PostConstruct、InitializingBean、init-method)，三种可以同时存在
	 * bean的生命周期-lifeCycleObserver-7-此时初始化已经完成 进行初始化后回调-BeanPostProcessor#postProcessAfterInitialization
	 * bean的生命周期-lifeCycleObserver-注册bean的销毁-DisposableBean#destory
	 * bean的生命周期-lifeCycleObserver-将创建好的bean从三级和二级缓存中移除，并入一级缓存中,bean创建完成
	 */
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		// 添加beanFactory的后置处理器
		//context.addBeanFactoryPostProcessor();
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
		// bean的生命周期 - 第一次调用bean的后置处理器 - InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation
		beanFactory.addBeanPostProcessor(new LifeCycle1InstantiationAwareBeanPostProcessor());
		// bean的生命周期 - 第二次调用bean的后置处理器 - SmartInstantiationAwareBeanPostProcessor#determineCandidateConstructors
		beanFactory.addBeanPostProcessor(new LifeCycle2SmartInstantiationAwareBeanPostProcessor());
		// bean的生命周期 - 第三次调用bean的后置处理器 - MergedBeanDefinitionPostProcessor#postProcessMergedBeanDefinition
		beanFactory.addBeanPostProcessor(new LifeCycle3MergedBeanDefinitionPostProcessor());
		// bean的生命周期 - 第四次调用bean的后置处理器 - InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation
		beanFactory.addBeanPostProcessor(new LifeCycle4InstantiationAwareBeanPostProcessor());
		// bean的生命周期 - 第五次调用bean的后置处理器 - InstantiationAwareBeanPostProcessor#postProcessProperties
		beanFactory.addBeanPostProcessor(new LifeCycle5InstantiationAwareBeanPostProcessor());
		// bean的生命周期 - 第六次调用bean的后置处理器 - BeanPostProcessor#postProcessBeforeInitialization 这里和第一次不同，第四次是InstantiationAwareBeanPostProcessor的，这里是BeanPostProcessor
		beanFactory.addBeanPostProcessor(new LifeCycle6BeanPostProcessor());
		// bean的生命周期 - 第七次调用bean的后置处理器 - BeanPostProcessor#postProcessAfterInitialization 这里和第四次不同，第四次是InstantiationAwareBeanPostProcessor的，这里是BeanPostProcessor
		beanFactory.addBeanPostProcessor(new LifeCycle7BeanPostProcessor());

		// bean的生命周期 - 自定义bean的销毁回调
		beanFactory.addBeanPostProcessor(new LifeCycleDestructionAwareBeanPostProcessor());

		// 注册并扫描配置类
		context.register(RunBeanLidrCycleTest.class);
		// 启动刷新spring上下文环境
		context.refresh();

		// ============spring上下文启动完成

		// importAware实现EnableXxx
		MyLogImportAware bean = beanFactory.getBean(MyLogImportAware.class);
		System.out.println(bean.getPrefix());

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


		UserEntity userEntity100 = (UserEntity) context.getBean("userEntity100");
		System.out.println("\n自定义注册的bean==>" + userEntity100.toString());
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
