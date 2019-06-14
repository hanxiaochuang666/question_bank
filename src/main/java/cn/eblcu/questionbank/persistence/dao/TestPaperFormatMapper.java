package cn.eblcu.questionbank.persistence.dao;

import cn.eblcu.questionbank.persistence.entity.dto.TestPaperFormat;

import java.util.List;
import java.util.Map;

public interface TestPaperFormatMapper {
    int deleteByPrimaryKey(Integer testPaperFormatId);

    int insertSelective(TestPaperFormat record);

    TestPaperFormat selectByPrimaryKey(Integer testPaperFormatId);

    int updateByPrimaryKeySelective(TestPaperFormat record);

    List<TestPaperFormat> selectList(Map<String,Object> map);

    Long selectCount(Map<String,Object> map);
}