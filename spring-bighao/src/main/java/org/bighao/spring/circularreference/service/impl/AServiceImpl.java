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
public class AServiceImpl implements AService {

	@Autowired
	private BService bService;

	@Override
	public void print() {
		System.out.println("AService printA");
	}

}
