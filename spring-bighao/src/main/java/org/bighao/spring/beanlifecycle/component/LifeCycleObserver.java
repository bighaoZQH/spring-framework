package org.bighao.spring.beanlifecycle.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @author: bighao周启豪
 * @date 2021/1/28 14:26
 * <p>
 * bean的生命周期 - 测试bean
 */
@Component
public class LifeCycleObserver extends Observer {

	@Autowired
	private LifeCycleTracker lifeCycleTracker;

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	@Override
	public String toString() {
		return "LifeCycleObserver{" +
				"type='" + type + '\'' +
				'}';
	}
}
