package cn.eblcu.questionbank.domain.service.impl;

import cn.eblcu.questionbank.domain.service.ITestResultService;
import cn.eblcu.questionbank.persistence.dao.IBaseDao;
import cn.eblcu.questionbank.persistence.dao.ITestResultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("testResultService")
public class TestResultServiceImpl extends BaseServiceImpl implements ITestResultService {
    @Autowired
    private ITestResultDao testResultDao;

    @Override
    IBaseDao getDao() {
        return this.testResultDao;
    }
}