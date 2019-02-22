package com.nmefc.hpcmmp.dao;

/**
 * @Author: QuYuan
 * @Description: 公共Dao方法抽取
 * @Date: Created in 9:23 2019/2/22
 * @Modified By:
 */
public interface BaseMapper<T> {
    int deleteByPrimaryKey(Integer id);

    int insert(T entity);

    int insertSelective(T entity);

    T selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(T entity);

    int updateByPrimaryKey(T entity);
}
