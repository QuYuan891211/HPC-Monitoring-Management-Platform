package com.nmefc.hpcmmp.service;

import com.nmefc.hpcmmp.exception.ServiceException;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: QuYuan
 * @Description: 公共Service方法抽取
 * @Date: Created in 9:46 2019/2/22
 * @Modified By:
 */
public interface BaseService<T,E,PK extends Serializable> {
    int deleteByPrimaryKey(PK pk);

    int insert(T record);

    int insertSelective(T record) throws ServiceException;

    T selectByPrimaryKey(PK pk);

    int updateByPrimaryKeySelective(T record) throws ServiceException;

    int updateByPrimaryKey(T record);

    //    使用Example的方法
    long countByExample(E example);

    int deleteByExample(E example);

    List<T> selectByExample(E example);

    //使用@Param的意义在于给实体的属性改名，使得当传入XML的SQL语句时与里面的参数一致（否则实体的属性名必须要与sql中的参数名一致）
    int updateByExampleSelective(@Param("record") T record, @Param("example") E example);

    int updateByExample(@Param("record") T record, @Param("example") E example);

}
