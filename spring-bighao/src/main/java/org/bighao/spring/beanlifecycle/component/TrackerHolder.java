package org.bighao.spring.beanlifecycle.component;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;

/**
 * @version 1.0
 * @author: bighao周启豪
 * @date 2021/2/1 16:13
 *
 * xml配置
 */
public class TrackerHolder {

	@PostConstruct
	public void initHolder() {
		System.out.println("@PostConstruct==>initHolder");
	}

	@PreDestroy
	public void destoryHolder() {
		System.out.println("@PreDestroy==>destoryHolder");
	}


	@Autowired
	private LifeCycleTracker tracker;

	private Integer id;

	private String desc;

	private Map<String, String> propertyMap;


	public LifeCycleTracker getTracker() {
		return tracker;
	}

	public void setTracker(LifeCycleTracker tracker) {
		this.tracker = tracker;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Map<String, String> getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(Map<String, String> propertyMap) {
		this.propertyMap = propertyMap;
	}

	@Override
	public String toString() {
		return "TrackerHolder{" +
				"tracker=" + tracker +
				", id=" + id +
				", desc='" + desc + '\'' +
				", propertyMap=" + propertyMap +
				'}';
	}

}
