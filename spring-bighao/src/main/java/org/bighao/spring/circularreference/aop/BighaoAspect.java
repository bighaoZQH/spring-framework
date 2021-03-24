package org.bighao.spring.circularreference.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @author: bighao周启豪
 * @date 2021/2/26 10:33
 */
@Component
@Aspect
public class BighaoAspect {

	@Pointcut("execution(* org.bighao.spring.circularreference.service..*.*())")
	public void pointCutWithin() {

	}

	@Before("pointCutWithin()")
	private void before() {
		System.out.println("Service AOP Before");
	}

}
