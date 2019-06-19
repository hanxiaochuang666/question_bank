package cn.eblcu.questionbank.domain.service;

import java.util.List;
import java.util.Map;

public interface IBaseService {

    <T> long selectCount(Map<String,Object> map);

    <T> List<T> selectList(Map<String,Object> map);

    int deleteByPrimaryKey(Integer id);

    <T>  int insertSelective(T record);

    <T> T selectByPrimaryKey(Integer id);

    <T> int updateByPrimaryKeySelective(T obj);

    <T> int deleteByParams(Map<String,Object> map);
}
