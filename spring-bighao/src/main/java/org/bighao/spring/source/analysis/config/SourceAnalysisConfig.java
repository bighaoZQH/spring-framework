package org.bighao.spring.source.analysis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: bighao周启豪
 * @Date: 20201/1/29 0:36
 * @Version 1.0
 */
@Configuration
@ComponentScan("org.bighao.spring.source.analysis.*")
//@MapperScan("org.bighao.spring.source.analysis.dao.*")
public class SourceAnalysisConfig {


}
