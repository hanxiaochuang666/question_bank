package cn.eblcu.questionbank.persistence.dao;

import cn.eblcu.questionbank.persistence.entity.dto.TestPaper;

import java.util.List;
import java.util.Map;

public interface TestPaperMapper {
    int deleteByPrimaryKey(Integer testPaperId);

    int insertSelective(TestPaper record);

    TestPaper selectByPrimaryKey(Integer testPaperId);

    int updateByPrimaryKeySelective(TestPaper record);

    List<TestPaper> selectList(Map<String,Object> map);

    Long selectCount(Map<String,Object> map);
}