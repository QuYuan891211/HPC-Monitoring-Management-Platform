package com.nmefc.hpcmmp.dao.management;

import com.nmefc.hpcmmp.entity.management.Action;

public interface ActionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Action record);

    int insertSelective(Action record);

    Action selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Action record);

    int updateByPrimaryKey(Action record);
}