package org.bighao.spring.beanlifecycle.importxxx;

import org.bighao.spring.beanlifecycle.importxxx.service.TestImportService;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 * @author: ZhouQiHao bighao1996@163.com
 * @date: 2022-08-11 1:48
 * <p>
 * 配合@Import注解使用的
 * <p>
 * selectImports返回String数组，
 * 返回需要导入的组件的全类名数组；
 *
 * ImportSelector和ImportBeanDefinitionRegistrar的区别
 * ImportSelector实现注册bean更简单点，只要把需要注册的bean的全类名返回出去就行，没办法手动注册bean，但有些bean是接口类型的，你返回全类名是无法实例化的
 * 而ImportBeanDefinitionRegistrar功能更强大点，可以手动注册bean，可以通过FactoryBean来实现对接口的注入
 * 并且ImportBeanDefinitionRegistrar可以查找beanDefiniton，可以实现当ABean已经存在IOC bean定义容器中，才注册BBean
 *
 * ImportSelector的功能比ImportBeanDefinitionRegistrar更单一
 */
public class MyImportSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(ComponentScan.class.getName());
		String[] basePackages = (String[]) annotationAttributes.get("basePackages");
		// 手动扫描TestImportService类型的bean
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		TypeFilter serviceFiler = new AssignableTypeFilter(TestImportService.class);
		scanner.addIncludeFilter(serviceFiler);
		Set<String> classes = new HashSet<>();
		for (String basePackage : basePackages) {
			scanner.findCandidateComponents(basePackage).forEach(beanDefinition -> classes.add(beanDefinition.getBeanClassName()));
		}

		String[] strings = classes.toArray(new String[classes.size()]);
		System.out.println("MyImportSelector#selectImports#手动导入bean==>" + Arrays.toString(strings));
		return strings;
	}
}
