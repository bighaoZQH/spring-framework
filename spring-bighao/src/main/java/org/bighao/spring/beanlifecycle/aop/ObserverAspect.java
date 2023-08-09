package org.bighao.spring.beanlifecycle.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: ZhouQiHao bighao1996@163.com
 * @date: 2022-08-12 1:44
 */
@Aspect
@Component
public class ObserverAspect {

	@Pointcut("execution(* org.bighao.spring.beanlifecycle.component.LifeCycleObserver.getType())")
	public void pointCutWithin() {}

	@Before("pointCutWithin()")
	private void before() {
		System.out.println("Service AOP Before");
	}

}
