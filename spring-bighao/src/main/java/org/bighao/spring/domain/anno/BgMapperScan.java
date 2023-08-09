package org.bighao.spring.domain.anno;

import org.bighao.spring.mapperscan.registar.MapperImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: bighao周启豪
 * @Date: 2020/1/26 16:32
 * @Version 1.0
 *
 * 自定义注解
 * java的元注解(说明注解的注解)
 * @Target() 表示Entity注解可以标注的位置 ElementType.TYPE是类位置 FIELD是属性位置 METHOD是方法
 * @Retention注解的生命周期 RetentionPolicy.SOURCE是源码中，拿被标注的类的class文件反编译后看到注解消失了
 * RetentionPolicy.CLASS 存在编译后的class文件中，但运行的时候会被jvm忽略
 * RetentionPolicy.RUNTIME 就是在运行时
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({MapperImportBeanDefinitionRegistrar.class})
public @interface BgMapperScan {

	String[] basePackages() default "";

}
