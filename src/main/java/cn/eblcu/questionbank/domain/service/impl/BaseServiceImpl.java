package cn.eblcu.questionbank.domain.service.impl;

import cn.eblcu.questionbank.domain.service.IBaseService;
import cn.eblcu.questionbank.persistence.dao.IBaseDao;

import java.util.List;
import java.util.Map;

public abstract class BaseServiceImpl implements IBaseService {


    abstract IBaseDao getMapper();

    @Override
    public <T> long selectCount(Map<String,Object> map) {
        return getMapper().selectCount(map);
    }

    @Override
    public <T> List<T> selectList(Map<String,Object> map) {
        return getMapper().selectList(map);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return getMapper().deleteByPrimaryKey(id);
    }

    @Override
    public <T> int insertSelective(T record) {
        return getMapper().insertSelective(record);
    }

    @Override
    public <T> T selectByPrimaryKey(Integer id) {
        return getMapper().selectByPrimaryKey(id);
    }

    @Override
    public <T> int updateByPrimaryKeySelective(T record) {
        return getMapper().updateByPrimaryKeySelective(record);
    }

    @Override
    public <T> int deleteByParams(Map<String, Object> map) {
        return getMapper().deleteByParams(map);
    }
}
