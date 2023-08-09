package org.bighao.spring.beanlifecycle.beanFactoryPostProcessor;

import org.bighao.spring.domain.entity.UserEntity;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: ZhouQiHao bighao1996@163.com
 * @date: 2022-08-10 18:52
 *
 * 总结一下处理顺序：
 * 1.BeanDefinitionRegistryPostProcessor优先比BeanFactoryPostProcessor执行
 * 2.手动add添加的 比 spring内部添加的 优先执行，遵守第一条
 * 3.通过以上的处理器新扫描出来的最后执行，遵守第一条
 *
 * BeanFactoryPostProcessor和BeanDefinitionRegistryPostProcessor 都是spring bean工厂的后置处理器，但是两个类的侧重点不一样，
 * BeanDefinitionRegistryPostProcessor 侧重于创建自定义的bd 而 BeanFactoryPostProcessor侧重于对已有bd属性的修改。
 * BeanDefinitionRegistryPostProcessor 先于 BeanFactoryPostProcessor 执行
 *
 * BeanDefinitionRegistryPostProcessor继承了BeanFactoryPostProcessor，提供了向工厂注册BeanDefinition的方法
 * ConfigurationClassPostProcessor就是该接口的一个实现类，他是初始化BeanFactoy最重要的类 ，没有之一；
 * 该后置处理器实现了BeanFactory实例化过程中最核心的操作：解析配置类，解析注释，完成扫描，
 * 处理@Import导入的类，并将所有交给spring管理的类解析生成BeanDefinition，并将他们注册到BeanFactoy；
 *
 *
 * 总结
 * BeanDefinitionRegistryPostProcessor实现类只要加注解（@Component）就可以执行，
 * 而ImportBeanDefinitionRegister加注解（@Component）只是交给spring管理，并不会执行接口的方法，必须要在配置类中@Import()才会发挥作用；
 * ImportBeanDefinitionRegister执行顺序更早
 * mybatis中@MapperScan 注解就是利用ImportBeanDefinitionRegister
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		System.out.println("进行beanFactory的回调-侧重于创建自定义的bd-MyBeanDefinitionRegistryPostProcessor#postProcessBeanDefinitionRegistry... ");

		// 侧重于创建自定义的beanDefinition
		BeanDefinitionBuilder bdBuilder = BeanDefinitionBuilder.genericBeanDefinition(UserEntity.class);
		GenericBeanDefinition beanDefinition = (GenericBeanDefinition) bdBuilder.getBeanDefinition();

		beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.addPropertyValue("id", "100");
		propertyValues.addPropertyValue("name", "bighao");
		propertyValues.addPropertyValue("age", "26");
		beanDefinition.setPropertyValues(propertyValues);
		registry.registerBeanDefinition("userEntity100", beanDefinition);
	}
}
