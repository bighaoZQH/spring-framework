package org.bighao.spring.beanlifecycle.listener;

import com.alibaba.fastjson.JSON;
import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: ZhouQiHao bighao1996@163.com
 * @date: 2022-08-12 15:46
 */
public class TestEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	/**
	 * Create a new {@code ApplicationEvent}.
	 *
	 * @param source the object on which the event initially occurred or with
	 *               which the event is associated (never {@code null})
	 */
	public TestEvent(Object source) {
		super(source);
	}


	@Override
	public String toString() {
		return JSON.toJSONString(source);
	}
}
