package com.nmefc.hpcmmp.dao;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: QuYuan
 * @Description: 公共Dao方法抽取
 *  T表示与table表对应的实体类（Entity）
    E表示Entity对应的Example类
    PK表示可能会用到主键 (比如Integer等)
 * @Date: Created in 9:23 2019/2/22
 * @Modified By:
 */
public interface BaseMapper<T,E,PK> {
    int deleteByPrimaryKey(PK pk);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(PK pk);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);

//    使用Example的方法
    long countByExample(E example);

    int deleteByExample(E example);

    List<T> selectByExample(E example);

    //使用@Param的意义在于给实体的属性改名，使得当传入XML的SQL语句时与里面的参数一致（否则实体的属性名必须要与sql中的参数名一致）
    int updateByExampleSelective(@Param("record") T record, @Param("example") E example);

    int updateByExample(@Param("record") T record, @Param("example") E example);





}
