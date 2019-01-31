package com.nmefc.hpcmmp.controller;

import com.nmefc.hpcmmp.entity.Test;
import com.nmefc.hpcmmp.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: QuYuan
 * @Description: test controller
 * @Date: Created in 11:00 2019/1/31
 * @Modified By:
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private TestService testService;

    /**
     *
     * @Description: insert one test into table
     *
     * @auther: QuYuan
     * @date: 11:05 2019/1/31
     * @param: [test]
     * @return: int
     *
     */
    @ResponseBody
    @PostMapping(value = "/add")
    public int insert(Test test){
        return testService.insert(test);
    }
    /***
     * @Description: 
     * @auther: QuYuan
     * @date: 11:24 2019/1/31 
     * @param: [pageNum, pageSize]
     * @return: java.lang.Object
     */
    @ResponseBody
    @GetMapping(value = "/findAll")
    public Object selectAll(@RequestParam(name = "pageNum",required = false,defaultValue = "1") int pageNum, @RequestParam(name = "pageSize",required = false,defaultValue = "10") int pageSize){
        return testService.selectAll(pageNum,pageSize);
    }
}
