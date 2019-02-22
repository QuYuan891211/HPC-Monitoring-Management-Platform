package com.nmefc.hpcmmp.controller.management;

import com.nmefc.hpcmmp.service.management.UserService;
import com.nmefc.hpcmmp.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @Author: QuYuan
 * @Description: 用户管理Controller
 * @Date: Created in 10:43 2019/2/22
 * @Modified By:
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * @description: 新增用户
     * @author: QuYuan
     * @date: 12:45 2019/2/22
     * @param: [user]
     * @return: int
     */
    @ResponseBody
    @PostMapping(value = "/insertUserInfo")
    public int insertUserInfo(User user){
//        设置软删除标记
        user.setIsDelete(false);
//        记录新建时间
        user.setGmtCreate(DateTimeUtils.date2timestamp(new Date()));
//        更新时间设置为新建时间
        user.setGmtModified(DateTimeUtils.date2timestamp(new Date()));
        return userService.insertSelective(user);
    }
}
