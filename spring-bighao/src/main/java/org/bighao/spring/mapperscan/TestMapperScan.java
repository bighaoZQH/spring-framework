package org.bighao.spring.mapperscan;

import org.bighao.spring.domain.anno.BgMapperScan;
import org.bighao.spring.mapperscan.service.TestService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: ZhouQiHao bighao1996@163.com
 * @date: 2022-08-10 19:27
 */
@Configuration
/*@EnableAspectJAutoProxy(proxyTargetClass = false)*/
//@ImportResource("classpath:spring.xml")
// 手写模拟@MapperScan
@ComponentScan(basePackages = "org.bighao.spring.mapperscan")
@BgMapperScan(basePackages = {"org.bighao.spring.mapperscan.dao", "org.bighao.spring.mapperscan.service"})
public class TestMapperScan {

	/**
	 * 此处需要注意的是，如果配置设置proxyTargetClass=false，或默认为false，
	 *          则是用JDK代理，否则使用的是CGLIB代理
	 *
	 * JDK代理的实现方式是基于接口实现，代理类继承Proxy，实现接口。
	 * 而CGLIB继承被代理的类来实现。
	 * 所以使用target会保证目标不变，关联对象不会受到这个设置的影响。
	 * 但是使用this对象时，会根据该选项的设置，判断是否能找到对象。
	 */

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestMapperScan.class);
		TestService testService = (TestService) context.getBean("testService");
		testService.find("100");
	}

}
