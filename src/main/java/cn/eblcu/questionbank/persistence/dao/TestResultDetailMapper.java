package cn.eblcu.questionbank.persistence.dao;

import cn.eblcu.questionbank.persistence.entity.dto.TestResultDetail;

import java.util.List;
import java.util.Map;

public interface TestResultDetailMapper {
    int deleteByPrimaryKey(Integer testResultDetailId);

    int insertSelective(TestResultDetail record);

    TestResultDetail selectByPrimaryKey(Integer testResultDetailId);

    int updateByPrimaryKeySelective(TestResultDetail record);

    List<TestResultDetail> selectList(Map<String,Object> map);

    Long selectCount(Map<String,Object> map);
}