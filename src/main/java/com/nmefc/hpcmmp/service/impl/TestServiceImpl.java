package com.nmefc.hpcmmp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nmefc.hpcmmp.dao.TestDao;
import com.nmefc.hpcmmp.entity.Test;
import com.nmefc.hpcmmp.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: QuYuan
 * @Description: test service implement
 * @Date: Created in 10:45 2019/1/31
 * @Modified By:
 */
@Service(value = "testService")
public class TestServiceImpl implements TestService{
    @Autowired
    private TestDao testDao;
    /**
     *
     * @Description: pagination
     *
     * @auther: QuYuan
     * @date: 10:56 2019/1/31
     * @param: [pageNum, pageSize]
     * @return: com.github.pagehelper.PageInfo<com.nmefc.hpcmmp.entity.Test>
     *
     */
    @Override
    public PageInfo<Test> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Test> testList = testDao.selectAll();
        PageInfo pageInfo = new PageInfo(testList);
        return pageInfo;
    }

    /**
     *
     * @Description: insert one test into the table
     *
     * @auther: QuYuan
     * @date: 10:59 2019/1/31
     * @param: [test]
     * @return: int
     *
     */
    @Override
    public int insert(Test test) {
        return testDao.insert(test);
    }
}
