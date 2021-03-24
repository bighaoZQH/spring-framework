package org.bighao.spring.beanlifecycle.component;

/**
 * @version 1.0
 * @author: bighao周启豪
 * @date 2021/2/2 14:28
 *
 * 通过@Bean注入
 */
public class AtBeanTracker {

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
		return "AtBeanHolder{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
