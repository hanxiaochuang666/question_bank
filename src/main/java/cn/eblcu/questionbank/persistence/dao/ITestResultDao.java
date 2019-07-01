package cn.eblcu.questionbank.persistence.dao;

import cn.eblcu.questionbank.persistence.entity.dto.TestResult;

public interface ITestResultDao extends IBaseDao {

    /**
     * 幂等插入
     * @param testResult
     * @return
     */
    long insertNoExists(TestResult testResult);
}