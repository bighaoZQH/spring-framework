package org.bighao.spring.mapperscan.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: bighao周启豪
 * @Date: 2020/1/26 18:18
 * @Version 1.0
 */
@Mapper
public interface TestDao {
    @Select("select * from user where id=#{id}")
    void query(String id);

    void query();
}
