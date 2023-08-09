package org.bighao.spring.beanlifecycle.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: ZhouQiHao bighao1996@163.com
 * @date: 2022-08-12 15:45
 */
@Service
public class TestListener implements ApplicationListener<TestEvent> {

	@Override
	public void onApplicationEvent(TestEvent event) {
		System.out.println("spring事件监听机制--收到TestEvent事件--" +  event.toString());
	}

}
