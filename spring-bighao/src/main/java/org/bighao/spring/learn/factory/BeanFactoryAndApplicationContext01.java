package org.bighao.spring.learn.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;

/**
 * BeanFactory 与 ApplicationContext 的区别
 *
 *  a. BeanFactory 与 ApplicationContext 并不仅仅是简单接口继承的关系, ApplicationContext 组合并扩展了 BeanFactory 的功能
 *  b. 又新学一种代码之间解耦途径
 * 练习：完成用户注册与发送短信之间的解耦, 用事件方式、和 AOP 方式分别实现
 */
@Configuration
public class BeanFactoryAndApplicationContext01 {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanFactoryAndApplicationContext01.class);
		/**
		 * 1. 到底什么是 BeanFactory
		 *     - 它是 ApplicationContext 的父接口
		 *     - 它才是 Spring 的核心容器, 主要的 ApplicationContext 实现都【组合】了它的功能
		 */
		System.out.println(context);

		/**
		 *  2. BeanFactory 能干点啥
		 *      - 表面上只有 getBean
		 *      - 实际上控制反转、基本的依赖注入、直至 Bean 的生命周期的各种功能, 都由它的实现类提供
		 */
		Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
		singletonObjects.setAccessible(true);
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		// 获取beanFactory中的singletonObjects
		Map<String, Object> map = (Map<String, Object>) singletonObjects.get(beanFactory);
		map.entrySet().stream().filter(e -> e.getKey().startsWith("component"))
				.forEach(e -> System.out.println(e.getKey() + "=" + e.getValue()));

        // 3. ApplicationContext 比 BeanFactory 多点什么？
		// 国际化
		System.out.println(context.getMessage("hi", null, Locale.CHINA));
		System.out.println(context.getMessage("hi", null, Locale.ENGLISH));
		System.out.println(context.getMessage("hi", null, Locale.JAPANESE));

		// 资源管理
		Resource[] resources = context.getResources("classpath*:META-INF/spring.factories");
		for (Resource resource : resources) {
			System.out.println(resource);
		}

		// 整合 Environment 环境 不区分大小写
		System.out.println(context.getEnvironment().getProperty("java_home"));
		System.out.println(context.getEnvironment().getProperty("server.port"));

		// 事件发布与监听
		//context.publishEvent(new UserRegisteredEvent(context));
		context.getBean(Component1.class).register();

    }

}

@Component
@Slf4j
class Component1 {
	@Autowired
	private ApplicationEventPublisher context;

	public void register() {
		log.debug("用户注册");
		context.publishEvent(new UserRegisteredEvent(this));
	}
}


@Slf4j
@Component
class Component2 {
	@EventListener
	public void aaa(UserRegisteredEvent event) {
		log.debug("{}", event);
		log.debug("发送短信");
	}
}

class UserRegisteredEvent extends ApplicationEvent {
	public UserRegisteredEvent(Object source) {
		super(source);
	}
}