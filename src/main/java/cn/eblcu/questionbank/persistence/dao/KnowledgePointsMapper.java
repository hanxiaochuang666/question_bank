package cn.eblcu.questionbank.persistence.dao;

import cn.eblcu.questionbank.persistence.entity.dto.KnowledgePoints;

import java.util.List;
import java.util.Map;

public interface KnowledgePointsMapper {
    int deleteByPrimaryKey(Integer konwledgePointsId);

    int insertSelective(KnowledgePoints record);

    KnowledgePoints selectByPrimaryKey(Integer konwledgePointsId);

    int updateByPrimaryKeySelective(KnowledgePoints record);

    List<KnowledgePoints> selectList(Map<String, Object> map);

    Long selectCount(Map<String, Object> map);
}