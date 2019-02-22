package com.nmefc.hpcmmp.service;

/**
 * @Author: QuYuan
 * @Description: 公共Service方法抽取
 * @Date: Created in 9:46 2019/2/22
 * @Modified By:
 */
public interface BaseService<T> {
    int deleteByPrimaryKey(Integer id);

    int insert(T entity);

    int insertSelective(T entity);

    T selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(T entity);

    int updateByPrimaryKey(T entity);
}
