package com.nmefc.hpcmmp.controller.management;

import com.nmefc.hpcmmp.common.enumPackage.Regex;
import com.nmefc.hpcmmp.common.enumPackage.ResponseMsg;
import com.nmefc.hpcmmp.entity.management.User;
import com.nmefc.hpcmmp.exception.ControllerException;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.management.UserService;
import com.nmefc.hpcmmp.common.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
//    控制器完成数据校验等工作，其余业务工作交给业务层
    public String insertUserInfo(@RequestBody User user) throws ControllerException {
        if(user == null || user.getId() !=null){return ResponseMsg.REQUEST_ERROR.getValue();}
        String response = ResponseMsg.SUCCESS.getValue();
        //进行数据校验
        try {
            response = userService.check(user,response);
        } catch (ServiceException e) {
            throw new ControllerException("Insert Exception : " + e.getErrorMsg());
//            e.printStackTrace();
        }

        if(!response.equals(ResponseMsg.SUCCESS.getValue())){return response;}

//        设置软删除标记
        user.setIsDelete(false);
//        记录新建时间
        user.setGmtCreate(DateTimeUtils.date2timestamp(new Date()));
//        更新时间设置为新建时间
        user.setGmtModified(DateTimeUtils.date2timestamp(new Date()));

//        密码加密,及关联角色在业务层中完成
//         传递给业务层
        try{
            userService.insertSelective(user);
        }catch (Exception e){
            response = ResponseMsg.EXCEPTION.getValue();
            throw new ControllerException("UserController Exception :" + e.getMessage());
        }finally {
            return response;
        }
    }
    /**
     * @description: 修改用户
     * @author: QuYuan
     * @date: 14:14 2019/2/24
     * @param: [user]
     * @return: java.lang.String
     */
    @ResponseBody
    @PostMapping(value = "/updateUserInfo")
    public String updateUserInfo(@RequestBody User user) throws ControllerException  {
        String response = "SUCCESS";
        //进行数据校验
        try {
            response = userService.check(user,response);
        } catch (Exception e) {
//            throw new ControllerException("update Exception :" + e.getMessage());
            e.printStackTrace();
        }

        if(!response.equals("SUCCESS")){return response;}
        //        更新时间设置为新建时间
        user.setGmtModified(DateTimeUtils.date2timestamp(new Date()));

//        密码加密,及关联角色在业务层中完成
//         传递给业务层
        try{
            userService.updateByPrimaryKeySelective(user);
        }catch (Exception e){
            response = "Exception";
            throw new ControllerException("UserController Exception :" + e.getMessage());

        }finally {
            return response;
        }
    }






    /**
     * @description: 软删除用户
     * @author: QuYuan
     * @date: 15:27 2019/2/24
     * @param: [user]
     * @return: java.lang.String
     */
    @ResponseBody
    @PostMapping(value = "/deleteUserInfo")
    public String softDeleteUserInfo(User user){
        String response = "SUCCESS";
        //        更新时间设置为新建时间
        user.setGmtModified(DateTimeUtils.date2timestamp(new Date()));
        //更改软删除标记，MySql不支持布尔类型(自动转为tinyint类型)
        user.setIsDelete(true);

//         传递给业务层
        try{
            userService.updateByPrimaryKeySelective(user);
        }catch (Exception e){
            response = "Exception";
            throw e;
        }finally {
            return response;
        }
    }



}
