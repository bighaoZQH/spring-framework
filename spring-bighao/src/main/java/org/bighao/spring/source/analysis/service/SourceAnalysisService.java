package org.bighao.spring.source.analysis.service;

import org.bighao.spring.source.analysis.dao.SourceAnalysisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @author: bighao周启豪
 * @date 2021/1/29 0:40
 */
@Service
public class SourceAnalysisService {

	@Autowired
	private SourceAnalysisDao sourceAnalysisDao;

	public void query() {
		sourceAnalysisDao.query();
	}

}
