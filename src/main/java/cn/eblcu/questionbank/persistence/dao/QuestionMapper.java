package cn.eblcu.questionbank.persistence.dao;

import cn.eblcu.questionbank.persistence.entity.dto.Question;

import java.util.List;
import java.util.Map;

public interface QuestionMapper {
    int deleteByPrimaryKey(Integer questionId);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer questionId);

    int updateByPrimaryKeySelective(Question record);

    List<Question> selectList(Map<String, Object> map);

    Long selectCount(Map<String, Object> map);
}