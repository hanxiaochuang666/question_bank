package cn.eblcu.questionbank.persistence.dao;

import cn.eblcu.questionbank.persistence.entity.dto.KnowledgePointsDetail;

import java.util.List;
import java.util.Map;

public interface KnowledgePointsDetailMapper {
    int deleteByPrimaryKey(Integer knowledgePointsDetailId);

    int insertSelective(KnowledgePointsDetail record);

    KnowledgePointsDetail selectByPrimaryKey(Integer knowledgePointsDetailId);

    int updateByPrimaryKeySelective(KnowledgePointsDetail record);

    List<KnowledgePointsDetail> selectList(Map<String, Object> map);

    Long selectCount(Map<String, Object> map);
}