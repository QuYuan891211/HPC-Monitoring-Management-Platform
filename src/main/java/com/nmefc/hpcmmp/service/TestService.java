package com.nmefc.hpcmmp.service;

import com.github.pagehelper.PageInfo;
import com.nmefc.hpcmmp.entity.Test;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: QuYuan
 * @Description: test service interface
 * @Date: Created in 10:41 2019/1/31
 * @Modified By:
 */

public interface TestService {
   /**
    *
    * @Description: 
    * 
    * @auther: QuYuan
    * @date: 10:44 2019/1/31
    * @param: [pageNum, pageSize]
    * @return: com.github.pagehelper.PageInfo<com.nmefc.hpcmmp.entity.Test>
    *
    */
    PageInfo<Test> selectAll(int pageNum,int pageSize);

    /**
     *
     * @Description: insert one test into table
     *
     * @auther: QuYuan
     * @date: 10:43 2019/1/31
     * @param: [test]
     * @return: int
     *
     */
    int insert(Test test);
    
}
