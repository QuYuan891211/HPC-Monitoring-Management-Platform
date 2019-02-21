package com.nmefc.hpcmmp.dao;

import java.util.List;

/**
 * @Author: QuYuan
 * @Description: universal method Extraction
 * @Date: Created in 16:27 2019/2/14
 * @Modified By:
 */
public interface BaseMapper<T> {
/**
 * @description: insert entity into table
 * @author: QuYuan
 * @date: 16:36 2019/2/14
 * @param: [entity]
 * @return: int
 */
    int insert(T entity);
    /**
     * @description: select entity by primary key
     * @author: QuYuan
     * @date: 16:37 2019/2/14
     * @param: [entity]
     * @return: T
     */
    T selectByPK(T entity);
    /**
     * @description:select all entity from the table
     * @author: QuYuan
     * @date: 16:39 2019/2/14
     * @param: []
     * @return: java.util.List<T>
     */
    List<T> selectAll();
    /**
     * @description: delete one entity by primary key
     * @author: QuYuan
     * @date: 16:44 2019/2/14
     * @param: [entity]
     * @return: int
     */
    int deleteByPk(T entity);

    /**
     * @description: delete all entity
     * @author: QuYuan
     * @date: 16:45 2019/2/14
     * @param: []
     * @return: int
     */
    int deleteAll();

    /**
     * @description: update one entity by primary key
     * @author: QuYuan
     * @date: 16:46 2019/2/14
     * @param: [entity]
     * @return: int
     */
    int updateByPk(T entity);
}
