package org.bighao.spring.mapperscan.service;

import org.bighao.spring.mapperscan.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: bighao周启豪
 * @Date: 2020/1/26 18:18
 * @Version 1.0
 */
@Service
public class TestService {

    @Autowired
    private TestDao testDao;

    public void find(String id) {
        System.out.println("service find");
        testDao.query(id);
    }

}
