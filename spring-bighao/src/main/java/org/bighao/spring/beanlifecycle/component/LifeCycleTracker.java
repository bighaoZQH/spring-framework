package org.bighao.spring.beanlifecycle.component;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @version 1.0
 * @author: bighao周启豪
 * @date 2021/2/1 9:51
 *
 * bean的生命周期 第三阶段 - 测试bean
 * 通过xml加载
 */
public class LifeCycleTracker implements InitializingBean {

	@Autowired
	private LifeCycleObserver lifeCycleObserver;

	@Autowired
	private Observer observer;

	private String trackerName;

	public String getTrackerName() {
		return trackerName;
	}

	public void setTrackerName(String trackerName) {
		this.trackerName = trackerName;
	}

	@Override
	public String toString() {
		return "LifeCycleTracker{" +
				"lifeCycleObserver=" + lifeCycleObserver +
				", observer=" + observer +
				", trackerName='" + trackerName + '\'' +
				'}';
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("LifeCycleTracker===>afterPropertiesSet() execute");
	}

	public void xmlInitMethod() {
		System.out.println("LifeCycleTracker===>xmlInitMethod() execute");
	}
}
