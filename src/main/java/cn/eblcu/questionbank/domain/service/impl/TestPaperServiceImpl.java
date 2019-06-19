package cn.eblcu.questionbank.domain.service.impl;

import cn.eblcu.questionbank.domain.service.ITestPaperService;
import cn.eblcu.questionbank.persistence.dao.IBaseDao;
import cn.eblcu.questionbank.persistence.dao.ITestPaperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("testPaperService")
public class TestPaperServiceImpl extends BaseServiceImpl implements ITestPaperService {
    @Autowired
    private ITestPaperDao testPaperDao;

    @Override
    IBaseDao getDao() {
        return this.testPaperDao;
    }
}