package org.bighao.spring.domain.anno;

import org.bighao.spring.beanlifecycle.importxxx.MyLogImportAware;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: ZhouQiHao bighao1996@163.com
 * @date: 2022-08-11 19:36
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(MyLogImportAware.class)
public @interface EnableLogPrefix {

	String prefix() default "MyLog";

}
