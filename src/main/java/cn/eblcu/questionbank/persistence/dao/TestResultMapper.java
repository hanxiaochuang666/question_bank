package cn.eblcu.questionbank.persistence.dao;

import cn.eblcu.questionbank.persistence.entity.dto.TestResult;

import java.util.List;
import java.util.Map;

public interface TestResultMapper {
    int deleteByPrimaryKey(Integer testResultId);

    int insertSelective(TestResult record);

    TestResult selectByPrimaryKey(Integer testResultId);

    int updateByPrimaryKeySelective(TestResult record);

    List<TestResult> selectList(Map<String,Object> map);

    Long selectCount(Map<String,Object> map);
}