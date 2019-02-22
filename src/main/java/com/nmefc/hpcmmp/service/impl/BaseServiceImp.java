package com.nmefc.hpcmmp.service.impl;

import com.nmefc.hpcmmp.dao.BaseMapper;
import com.nmefc.hpcmmp.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: QuYuan
 * @Description: 公共Service实现方法抽取
 * @Date: Created in 9:57 2019/2/22
 * @Modified By:
 */

public abstract class BaseServiceImp<T> implements BaseService<T> {
    @Autowired
    private BaseMapper<T> baseMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(T entity) {
        return baseMapper.insert(entity);
    }

    @Override
    public int insertSelective(T  entity) {
        return baseMapper.insertSelective(entity);
    }

    @Override
    public T selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(T entity) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(T entity) {
        return 0;
    }
}
