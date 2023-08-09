package org.bighao.spring.source.analysis;

import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: ZhouQiHao bighao1996@163.com
 * @date: 2022-08-10 17:48
 */
public class SupplierBeanTest {

	/**
	 * https://blog.csdn.net/duxd185120/article/details/109224025
	 *
	 * 根据其set方法的解释：就是替代工厂方法（包含静态工厂）或者构造器创建对象，但是其后面的生命周期回调不影响。
	 * 也就是框架在创建对象的时候会校验这个instanceSupplier是否有值，有的话，调用这个字段获取对象。
	 * 那么其应用场景什么呢？什么情况下使用呢？
	 * 静态工厂或者工厂方法或者构造器不足：
	 * 不管是静态工厂还是工厂方法，都需要通过反射调用目标方法创建对象，反射或多或少影响性能，如果不使用反射呢？
	 * 就是面向java8函数式接口编程，就是提供一个回调方法，直接调用回调方法即可，不需要通过反射了。
	 *
	 * 主要是考虑反射调用目标方法不如直接调用目标方法效率高。
	 */
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(User.class);
		beanDefinition.setInstanceSupplier(SupplierBeanTest::createUser);
		context.registerBeanDefinition(User.class.getSimpleName(), beanDefinition);
		context.refresh();
		System.out.println(context.getBean(User.class).getName());
	}

	private static User createUser() {
		return new User("小薇呀");
	}

	static class User {
		private String name;

		public User(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		@PostConstruct
		public void init() {
			System.out.println("user 初始化.....");
		}
	}
}
