package org.bighao.spring.circularreference.service.impl;

import org.bighao.spring.circularreference.service.AService;
import org.bighao.spring.circularreference.service.BService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @author: bighao周启豪
 * @date 2021/2/26 9:24
 */
@Service
public class BServiceImpl implements BService {

	/**
	 * 异常：
	 * Caused by: org.springframework.beans.factory.BeanNotOfRequiredTypeException:
	 * Bean named 'AServiceImpl' is expected to be of type 'org.bighao.spring.circularreference.service.impl.AServiceImpl'
	 * but was actually of type 'com.sun.proxy.$Proxy20'
	 *
	 * 因为存在接口时spring默认使用JDK动态代理，
	 * 如果不存在接口才默认使用cglib动态代理(也可以直接指定全部使用cglib动态代理)，
	 * cglib动态代理的情况下才可以直接使用实现类进行注入
	 *
	 */
	/*@Autowired
	private AServiceImpl aService;*/

	@Autowired
	private AService aService;

	@Override
	public void print() {
		System.out.println("BService print");
	}


}
