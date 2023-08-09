package org.bighao.spring.beanlifecycle.beanFactoryPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: ZhouQiHao bighao1996@163.com
 * @date: 2022-07-17 23:55
 *
 * 实现该接口，可以在spring的bean创建之前修改bean的定义属性
 * spring允许BeanFactoryPostProcessor在容器实例化任何其他bean之前读取配置元数据
 * 并可以根据需要进行修改，例如可以把bean的scop从singleton改为protatype,也可以把property的值修改掉
 * 同时配置多个BeanFactoryPostProcessor,并通过设置‘order’属性来控制各个BeanFactoryPostProcessor的执行顺序
 * BeanFactoryPostProcessor是在spring容器加载了bean的定义文件之后，在bean实例化之前执行的
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("进行beanFactory的回调-侧重于对已有bd属性的修改-MyBeanFactoryPostProcessor#postProcessBeanFactory... ");
		// 侧重于修改beanDefinition
		GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanFactory.getBeanDefinition("userEntity100");
		MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
		propertyValues.addPropertyValue("name", "bighao&xeeso");
	}
}
