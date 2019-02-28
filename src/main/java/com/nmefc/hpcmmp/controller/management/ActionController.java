package com.nmefc.hpcmmp.controller.management;

import com.nmefc.hpcmmp.common.enumPackage.ResponseMsg;
import com.nmefc.hpcmmp.common.utils.DateTimeUtils;
import com.nmefc.hpcmmp.entity.management.Action;
import com.nmefc.hpcmmp.exception.ControllerException;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.management.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @Author: QuYuan
 * @Description: 权限管理控制器
 * @Date: Created in 18:22 2019/2/28
 * @Modified By:
 */
@Controller
@RequestMapping(value = "/action")
public class ActionController {
    @Autowired
    private ActionService actionService;
    /**
     * @description: 新增权限
     * @author: QuYuan
     * @date: 19:00 2019/2/28
     * @param: [action]
     * @return: java.lang.String
     */
    @ResponseBody
    @PostMapping(value = "/insertActionInfo")
    public String insertRoleInfo(Action action) throws ControllerException
    {
        if(action == null || action.getId() !=null){return ResponseMsg.REQUEST_ERROR.getValue();}
        String response = ResponseMsg.SUCCESS.getValue();
        try {
            response = actionService.check(action,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //进行数据校验
        if(!response.equals(ResponseMsg.SUCCESS.getValue())){return response;}

//        记录新建时间
        action.setGmtCreate(DateTimeUtils.date2timestamp(new Date()));
//        更新时间设置为新建时间
        action.setGmtModified(DateTimeUtils.date2timestamp(new Date()));
        try{
            actionService.insertSelective(action);
        }catch (Exception e){
            response = ResponseMsg.EXCEPTION.getValue();
            throw new ControllerException("ActionController Exception :" + e.getMessage());
        }finally {
            return response;
        }
    }
}
