package org.bighao.spring.mapperscan.registar;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: bighao周启豪
 * @Date: 2020/1/26 16:06
 * @Version 1.0
 * <p>
 * 手写实现@MapperScan
 * 用于返回接口的代理对象，以及通过反射执行目标方法
 */

//@Component //这里不要让spring来扫描到!!! 要我们手动注册
// 原因看MapperScanImportBeanDefinitionRegistrar里的注释
public class MapperFactoryBean implements FactoryBean<Object>, InvocationHandler {

	private final Class<?> clazz;

	public MapperFactoryBean(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
	public Object getObject() throws Exception {
		Class<?>[] clazzs = new Class<?>[]{clazz};
		// 返回代理对象
		Object proxy = Proxy.newProxyInstance(this.getClass().getClassLoader(), clazzs, this);
		return proxy;
	}

	@Override
	public Class<?> getObjectType() {
		return clazz;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("proxy invoke");
		// 获取mapper上的sql语句
		// 1.获取对应方法 我这边简单写死了
		Method method1 = proxy.getClass().getInterfaces()[0].getMethod(method.getName(), String.class);
		// 2.获取注解 我这边也简单写死了
		Select select = method1.getDeclaredAnnotation(Select.class);
		System.out.println(select.value()[0]);
		return null;
	}

}
