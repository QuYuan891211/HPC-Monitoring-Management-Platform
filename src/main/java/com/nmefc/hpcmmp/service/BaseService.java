package com.nmefc.hpcmmp.service;

import com.github.pagehelper.PageInfo;

/**
 * @Author: QuYuan
 * @Description: universal method Extraction
 * @Date: Created in 16:58 2019/2/14
 * @Modified By:
 */
public interface BaseService<T> {
    /**
     * @description: find all entity from table
     * @author: QuYuan
     * @date: 16:59 2019/2/14
     * @param: [pageNum, pageSize]
     * @return: com.github.pagehelper.PageInfo<com.nmefc.hpcmmp.entity.Test>
     */
    PageInfo<T> selectAll(int pageNum, int pageSize);

    /**
     * @description: insert one entity into table
     * @author: QuYuan
     * @date: 17:00 2019/2/14
     * @param: [entity]
     * @return: int
     */
    int insert(T entity);

    /**
     * @description: delete one entity by primary key
     * @author: QuYuan
     * @date: 17:00 2019/2/14
     * @param: [entity]
     * @return: int
     */
    int deleteByPk(T entity);

    /**
     * @description: delete all entity
     * @author: QuYuan
     * @date: 17:01 2019/2/14
     * @param: []
     * @return: int
     */
    int deleteAll();

    /**
     * @description: update one entity by primary key
     * @author: QuYuan
     * @date: 17:01 2019/2/14
     * @param: [entity]
     * @return: int
     */
    int updateByPk(T entity);
}
