package cn.eblcu.questionbank.domain.service.impl;

import cn.eblcu.questionbank.domain.service.ITestResultDetailService;
import cn.eblcu.questionbank.persistence.dao.IBaseDao;
import cn.eblcu.questionbank.persistence.dao.ITestResultDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("testResultDetailService")
public class TestResultDetailServiceImpl extends BaseServiceImpl implements ITestResultDetailService {
    @Autowired
    private ITestResultDetailDao testResultDetailDao;

    @Override
    IBaseDao getDao() {
        return this.testResultDetailDao;
    }
}