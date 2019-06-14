package cn.eblcu.questionbank.persistence.dao;

import cn.eblcu.questionbank.persistence.entity.dto.QuestionType;

import java.util.List;
import java.util.Map;

public interface QuestionTypeMapper {
    int deleteByPrimaryKey(Integer questionTypeId);

    int insertSelective(QuestionType record);

    QuestionType selectByPrimaryKey(Integer questionTypeId);

    int updateByPrimaryKeySelective(QuestionType record);

    List<QuestionType> selectList(Map<String,Object> map);

    Long selectCount(Map<String,Object> map);
}