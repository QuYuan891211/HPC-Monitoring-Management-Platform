package com.nmefc.hpcmmp.controller.management;

import com.nmefc.hpcmmp.common.enumPackage.Regex;
import com.nmefc.hpcmmp.common.enumPackage.ResponseMsg;
import com.nmefc.hpcmmp.common.utils.DateTimeUtils;
import com.nmefc.hpcmmp.entity.management.Role;
import com.nmefc.hpcmmp.exception.ControllerException;
import com.nmefc.hpcmmp.service.management.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: QuYuan
 * @Description: 角色管理
 * @Date: Created in 14:42 2019/2/25
 * @Modified By:
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @ResponseBody
    @PostMapping(value = "/insertRoleInfo")
    public String insertRoleInfo(Role role) throws ControllerException {
        if(role == null || role.getId() !=null){return ResponseMsg.REQUEST_ERROR.getValue();}
        String response = ResponseMsg.SUCCESS.getValue();
        response = check(role,response);
        //进行数据校验
        if(!response.equals(ResponseMsg.SUCCESS.getValue())){return response;}

//        记录新建时间
        role.setGmtCreate(DateTimeUtils.date2timestamp(new Date()));
//        更新时间设置为新建时间
        role.setGmtModified(DateTimeUtils.date2timestamp(new Date()));

//        关联权限在业务层中完成
//         传递给业务层
        try{
            roleService.insertSelective(role);
        }catch (Exception e){
            response = ResponseMsg.EXCEPTION.getValue();
            throw new ControllerException("RoleController Exception :" + e.getMessage());
        }finally {
            return response;
        }
    }

    /**
     * @description: 数据校验
     * @author: QuYuan
     * @date: 12:00 2019/2/24
     * @param: [user, response]
     * @return: java.lang.String
     */
    private String check(Role role,String response) throws ControllerException {
        //          1.检测必须项
        if(role == null || role.getName() ==null || role.getName().length() == 0) {
            return ResponseMsg.PARAMETERS_MISSING.getValue();
        }
//        2.检测所填各项是否合规，并补齐默认值
//        检查是否超出长度及违规字符
        List<String> temp1 = new LinkedList<>();
        temp1.add(role.getName());
        if(!Regex.NAME.getDetectResult(temp1)){return ResponseMsg.PAREMETERE_ERROR.getValue();}
        return response;
    }
}
