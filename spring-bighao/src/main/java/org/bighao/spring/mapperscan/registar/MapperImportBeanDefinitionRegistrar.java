package org.bighao.spring.mapperscan.registar;

import org.bighao.spring.domain.anno.BgMapperScan;
import org.bighao.spring.mapperscan.dao.TestDao;
import org.mybatis.spring.annotation.MapperScannerRegistrar;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: bighao周启豪
 * @Date: 2020/1/26 15:55
 * @Version 1.0
 * <p>
 * 模拟@MapperScan 将dao接口注册到spring的ioc容器中
 * 但思考 如果容器中的bean是接口，那spring如何去实例化?
 * 方法: 通过FactoryBean来返回一个代理对象
 */
public class MapperImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	/**
	 *
	 * @param importingClassMetadata	标注@Import的配置类的注解信息
	 * @param beanDefinitionRegistry	beanDefinition注册器
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
		/**
		 * 1.得到beanDefinition
		 * 扫描所有的接口   获取代理对象后生成beanDefinition 这里简单写死一个接口
		 *  问题1： 接口如果直接放入ioc容器，那这个就有问题，因为接口无法实例化
		 *  解决问题1：通过代理对象
		 *
		 *  问题2： JDK动态代理生成的代理对象是例如:$Proxy01 这个对象我们这里无法直接得到 怎么办?
		 *  解决问题2：通过FactoryBean
		 *
		 *  问题3: FactoryBean如果只装饰一个接口，那就要写很多的FactoryBean,因此在FactoryBean中提供
		 *          一个构造方法用于接收要装饰的接口类型，但这样的话，我们容器如何实例化这个FactoryBean?
		 *  解决问题3: 通过构造方法实例化
		 *      beanDefinition.getConstructorArgumentValues()
		 *                  .addGenericArgumentValue("com.bighao.aop.dao.Dao");
		 *  "com.bighao.aop.dao.Dao"是beanDefinition.getBeanClassName()
		 *  在构造注入时，spring会从容器中找到"com.bighao.aop.dao.Dao"类型的Class注入到构造方法中
		 *
		 *  注意:FactoryBean不要加注解，不要让spring自动扫描到去加入ioc容器，
		 *  这样会报错，因为找不到要注入的依赖无法进行实例化
		 *  FactoryBean是我们在下面的代码手动去加入ioc容器的
		 */
        /*Dao dao = (Dao) Proxy.newProxyInstance(TestAop.class.getClassLoader(), new Class[]{Dao.class}, new MyInvovcationHandler());
        dao.query("aaa");*/

		// 简单的案例====
		// 将dao接口构建成BeanDefinition
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(TestDao.class);
		GenericBeanDefinition beanDefinition = (GenericBeanDefinition) builder.getBeanDefinition();
		System.out.println(beanDefinition.getBeanClassName());
		// 将beanClass设置为FactoryBean
		beanDefinition.setBeanClass(MapperFactoryBean.class);
		// 通过构造方法进行实例化
		//beanDefinition.getConstructorArgumentValues().addGenericArgumentValue("com.bighao.aop.mapperscan.dao.TestDao");
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(TestDao.class);
		// 向ioc容器注册我们的bean信息
		beanDefinitionRegistry.registerBeanDefinition("testDao", beanDefinition);

		// 扫描包。。。====
		AnnotationAttributes mapperScanAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(BgMapperScan.class.getName()));
		if (mapperScanAttrs != null) {
			this.registerBeanDefinitions(mapperScanAttrs, beanDefinitionRegistry, generateBaseBeanName(importingClassMetadata, 0));
		}
	}

	void registerBeanDefinitions(AnnotationAttributes annoAttrs, BeanDefinitionRegistry registry, String beanName) {
		List<String> basePackages = Arrays.stream(annoAttrs.getStringArray("basePackages")).filter(StringUtils::hasText).collect(Collectors.toList());
		for (String basePackage : basePackages) {

		}
	}


	private static String generateBaseBeanName(AnnotationMetadata importingClassMetadata, int index) {
		return importingClassMetadata.getClassName() + "#" + MapperScannerRegistrar.class.getSimpleName() + "#" + index;
	}

}
