package cn.eblcu.questionbank.domain.service;

import java.util.List;
import java.util.Map;

public interface IBaseService {

    <T> long selectCount(Map<String,Object> dic);

    <T> List<T> selectList(Map<String,Object> dic);

    int deleteByPrimaryKey(Integer dictionaryDataId);

    <T>  int insertSelective(T record);

    <T> T selectByPrimaryKey(Integer id);

    <T> int updateByPrimaryKeySelective(T record);

    <T> int deleteByParams(Map<String,Object> map);
}
