package org.bighao.spring.circularreference;

import org.bighao.spring.circularreference.service.AService;
import org.bighao.spring.circularreference.service.BService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @version 1.0
 * @author: bighao周启豪
 * @date 2021/2/26 9:23
 */
@Configuration
@ComponentScan(basePackages = {"org.bighao.spring.circularreference"})
/**
 * 异常：
 * Caused by: org.springframework.beans.factory.BeanNotOfRequiredTypeException:
 * Bean named 'AServiceImpl' is expected to be of type 'org.bighao.spring.circularreference.service.impl.AServiceImpl'
 * but was actually of type 'com.sun.proxy.$Proxy20'
 *
 * 因为存在接口时spring默认使用JDK动态代理，
 * 如果不存在接口才默认使用cglib动态代理(也可以直接指定全部使用cglib动态代理)，
 * cglib动态代理的情况下才可以直接使用实现类进行注入
 * EnableAspectJAutoProxy(proxyTargetClass = false)
 *
 * false是jdk动态代理，true是cglib
 */
@EnableAspectJAutoProxy(proxyTargetClass = false)
public class RunCircularReferenceTest {

	/**
	 * https://blog.csdn.net/flyfeifei66/article/details/81481222
	 * 经测试，jdk创建对象的速度远大于cglib，这是由于cglib创建对象时需要操作字节码。
	 * cglib执行速度略大于jdk，所以比较适合单例模式。
	 * 另外由于CGLIB的大部分类是直接对Java字节码进行操作，这样生成的类会在Java的永久堆中。
	 * 如果动态代理操作过多，容易造成永久堆满，触发OutOfMemory异常。
	 * spring默认使用jdk动态代理，如果类没有接口，则使用cglib。
	 *
	 * 1、如果目标对象实现了接口，默认情况下会采用JDK的动态代理实现AOP
	 * 2、如果目标对象实现了接口，可以强制使用CGLIB实现AOP
	 *
	 * JDK动态代理和CGLIB字节码生成的区别？
	 *  （1）JDK动态代理只能对实现了接口的类生成代理，而不能针对类
	 *  （2）CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法
	 *    因为是继承，所以该类或方法最好不要声明成final
	 */
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RunCircularReferenceTest.class);
		AService aService = context.getBean(AService.class);
		BService bService = context.getBean(BService.class);
		aService.print();
		bService.print();
	}


}
