package cn.eblcu.questionbank.persistence.dao;

import java.util.List;
import java.util.Map;

public interface IBaseDao {

    int deleteByPrimaryKey(Integer id);

    <T> int insertSelective(T record);

    <T> T selectByPrimaryKey(Integer id);

    <T> int updateByPrimaryKeySelective(T record);

    <T> long selectCount(Map<String,Object> map);

    <T> List<T> selectList(Map<String,Object> map);

    <T> int deleteByParams(Map<String,Object> map);
}
