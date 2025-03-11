package org.bighao.spring.learn.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

public class BeanFactoryImpl {

	public static void main(String[] args) {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// 先创建bean的定义（class，scope，初始化，销毁等等）
		AbstractBeanDefinition beanDef = BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
		beanFactory.registerBeanDefinition("config", beanDef);

		for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
			System.out.println("beanDefinitionName: " + beanDefinitionName);
		}
	}


	@Configuration
	static class Config {
		@Bean
		public Bean1 bean1() {
			return new Bean1();
		}

		@Bean
		public Bean2 bean2() {
			return new Bean2();
		}

		@Bean
		public Bean3 bean3() {
			return new Bean3();
		}

		@Bean
		public Bean4 bean4() {
			return new Bean4();
		}
	}

	interface Inter {

	}

	static class Bean3 implements Inter {

	}

	static class Bean4 implements Inter {

	}

	static class Bean1 {
		private static final Logger log = LoggerFactory.getLogger(Bean1.class);

		public Bean1() {
			log.debug("构造 Bean1()");
		}

		@Autowired
		private Bean2 bean2;

		public Bean2 getBean2() {
			return bean2;
		}

		// 假如这样写了，会注入谁？
		@Autowired
		@Resource(name = "bean4")
		private Inter bean3;

		public Inter getInter() {
			return bean3;
		}
	}

	static class Bean2 {
		private static final Logger log = LoggerFactory.getLogger(Bean2.class);

		public Bean2() {
			log.debug("构造 Bean2()");
		}
	}

}
