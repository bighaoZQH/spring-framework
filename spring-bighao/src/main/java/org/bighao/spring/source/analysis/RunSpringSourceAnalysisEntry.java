package org.bighao.spring.source.analysis;

import org.bighao.spring.source.analysis.config.SourceAnalysisConfig;
import org.bighao.spring.source.analysis.service.SourceAnalysisService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @version 1.0
 * @author: bighao周启豪
 * @date 2021/1/29 0:38
 */
public class RunSpringSourceAnalysisEntry {

	/**
	 * spring ioc 源码分析入口
	 */
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SourceAnalysisConfig.class);
		SourceAnalysisService sourceAnalysisService = (SourceAnalysisService) context.getBean("sourceAnalysisService");
		sourceAnalysisService.query();
	}


}
