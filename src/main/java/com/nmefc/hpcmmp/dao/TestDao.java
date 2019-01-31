package com.nmefc.hpcmmp.dao;

import com.nmefc.hpcmmp.entity.Test;

import java.util.List;

/**
 * @Author: QuYuan
 * @Description: test dao
 * @Date: Created in 17:45 2019/1/30
 * @Modified By:
 */
public interface TestDao {
    /**
     *
     * @Description: select all test
     *
     * @auther: QuYuan
     * @date: 8:47 2019/1/31
     * @param: []
     * @return: java.util.List<com.nmefc.hpcmmp.entity.Test>
     *
     */
    List<Test> selectAll();
    /**
     *
     * @Description: insert one test into table
     *
     * @auther: QuYuan
     * @date: 8:47 2019/1/31
     * @param: [test]
     * @return: void
     *
     */
    void insert(Test test);
}
