package com.nmefc.hpcmmp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nmefc.hpcmmp.dao.TestMapper;
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
    private TestMapper testDao;
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
     * @description:
     * @author: QuYuan
     * @date: 17:23 2019/2/14 
     * @param: [test]
     * @return: int
     */
    @Override
    public int insert(Test test) {
        return testDao.insert(test);
    }
    /**
     * @description:
     * @author: QuYuan
     * @date: 17:23 2019/2/14 
     * @param: [entity]
     * @return: int
     */
    @Override
    public int deleteByPk(Test entity) {
        return 0;
    }
    
    /**
     * @description:
     * @author: QuYuan
     * @date: 17:23 2019/2/14 
     * @param: []
     * @return: int
     */
    @Override
    public int deleteAll() {
        return 0;
    }
    /**
     * @description:
     * @author: QuYuan
     * @date: 17:23 2019/2/14 
     * @param: [entity]
     * @return: int
     */
    @Override
    public int updateByPk(Test entity) {
        return 0;
    }
    
    

}
