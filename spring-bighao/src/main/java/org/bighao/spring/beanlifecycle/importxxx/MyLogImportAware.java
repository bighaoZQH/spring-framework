package org.bighao.spring.beanlifecycle.importxxx;

import org.bighao.spring.domain.anno.EnableLogPrefix;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description:
 * @author: ZhouQiHao bighao1996@163.com
 * @date: 2022-08-11 2:59
 * <p>
 * ImportAware 是给被@Configuration注解过的类用的，一般来说就是Appconfig.class这种配置类。
 * ImportAware 和@Import注解一起使用更好。
 *
 * ImportAware实际就是回调给你一个@Configuration注解过的类的注解信息，然后自己可以那这些信息去做些别的事情。。。
 *
 * 比ImportSelector的功能更单一
 */
//@Component 旧版本可用，新版需要用@Configuration，下同
//@Configuration 被import了不用加这个注解
public class MyLogImportAware implements ImportAware, EnvironmentAware {

	private AnnotationAttributes annotationAttributes;

	private Environment environment;

	@Override
	public void setImportMetadata(AnnotationMetadata importMetadata) {
		this.annotationAttributes = AnnotationAttributes.fromMap(
				importMetadata.getAnnotationAttributes(EnableLogPrefix.class.getName(), false));
	}

	public String getPrefix() {
		return this.environment.getProperty("spring.my.log.prefix", String.class, annotationAttributes.getString("prefix"));
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
}
