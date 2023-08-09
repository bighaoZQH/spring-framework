package org.bighao.spring.beanlifecycle.component;


import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @author: bighao周启豪
 * @date 2021/1/28 15:17
 *
 * 这个类我在第一次beanPostProcessor中 手动初始化了，所以后面的生命周期跟踪不了了
 */
@Component
public class Observer {

	private Integer id;

	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Observer{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
