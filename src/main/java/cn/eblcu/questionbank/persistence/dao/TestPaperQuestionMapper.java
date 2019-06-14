package cn.eblcu.questionbank.persistence.dao;

import cn.eblcu.questionbank.persistence.entity.dto.TestPaperQuestion;

import java.util.List;
import java.util.Map;

public interface TestPaperQuestionMapper {
    int deleteByPrimaryKey(Integer testPaperQuestionId);

    int insertSelective(TestPaperQuestion record);

    TestPaperQuestion selectByPrimaryKey(Integer testPaperQuestionId);

    int updateByPrimaryKeySelective(TestPaperQuestion record);

    List<TestPaperQuestion> selectList(Map<String,Object> map);

    Long selectCount(Map<String,Object> map);
}