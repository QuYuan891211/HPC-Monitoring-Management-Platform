package com.nmefc.hpcmmp.controller.management;

import com.nmefc.hpcmmp.common.enumPackage.ResponseMsg;
import com.nmefc.hpcmmp.common.transPara.RolesRelate;
import com.nmefc.hpcmmp.common.utils.DateTimeUtils;
import com.nmefc.hpcmmp.entity.management.Action;
import com.nmefc.hpcmmp.entity.management.Role;
import com.nmefc.hpcmmp.exception.ControllerException;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.management.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public String insertActionInfo(@RequestBody Action action) throws ControllerException
    {
        if(action == null || action.getId() !=null){return ResponseMsg.REQUEST_ERROR.getValue();}
        String response = ResponseMsg.SUCCESS.getValue();
        try {
            response = actionService.check(action,response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //增加url验重机制
        try{
            boolean newURL=actionService.checkURL(action.getUrl());
            if(!newURL) response=ResponseMsg.PAREMETERE_DUPLICATION.getValue()+"(URL)";
        }
        catch(Exception e) {
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

    /**
     *@description:修改Action
     *@date:2019.03.08
     *@author:Li Fei
     * @param:
     * @return:
     */
    @ResponseBody
    @PostMapping(value = "/updateActionInfo")
    public String updateActionInfo(@RequestBody Action action) throws ControllerException {
        String response = ResponseMsg.SUCCESS.getValue();
        //进行数据校验
        try {
            response = actionService.check(action,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //数据校验失败，反馈失败原因
        if(!response.equals(ResponseMsg.SUCCESS.getValue())){return response;}
        //        更新时间设置为新建时间
        action.setGmtModified(DateTimeUtils.date2timestamp(new Date()));

//        关联权限在业务层中完成
//         传递给业务层
        try{
            //更新action的表内容
            actionService.updateByPrimaryKeySelective(action);
            //删除与role的关联表，此处不做，该功能在role中进行
//            actionService.deleteRelativityWithRoleByActionID(action.getId());
            //重新建立与role的关联表
//            actionService.saveRoleRelativity(action);
        }catch (Exception e){
            response = ResponseMsg.EXCEPTION.getValue();
            throw new ControllerException("RoleController Exception :" + e.getMessage());
        }finally {
            return response;
        }
    }

    /**
     *@description:获取Action及其关联项
     *@date:2019.03.08
     *@author:Li Fei
     * @param:
     * @return:
     */
    @ResponseBody
    @PostMapping(value = "/checkActionByID")
    public Action checkActionByID(@RequestBody Action action) throws ControllerException {

        //查找对应role的参数
        try{
            action=actionService.selectByPrimaryKey(action.getId());
        }
        catch (Exception e){
            throw new ControllerException("RoleController Exception :" + e.getMessage());
        }
        List<Role> roleList = new ArrayList<>();
        //检查所有关联
        try{
            roleList=actionService.selectRelateRoleByActionID(action);
            action.setRoleList(roleList);
        }
        catch (Exception e){
            throw new ControllerException("RoleController Exception :" + e.getMessage());
        }
        finally {
            return action;
        }
    }

    /**
     *@description:通过ID删除Action及其关联项
     *@date:2019.03.08
     *@author:Li Fei
     * @param:
     * @return:
     */
    @ResponseBody
    @PostMapping(value = "/deleteActionInfo")
    public String deleteActionInfo(@RequestBody Action action) throws ControllerException {
        String response = ResponseMsg.SUCCESS.getValue();
        //进行数据校验必须有roleID>0
        try{
            if(action.getId()<=0) response=ResponseMsg.PARAMETERS_MISSING.getValue();
        }
        catch (Exception e)
        {
            response=ResponseMsg.PARAMETERS_MISSING.getValue();
            throw new ControllerException("RoleController Exception :" + e.getMessage());
        }

        //数据校验失败，反馈失败原因
        if(!response.equals(ResponseMsg.SUCCESS.getValue())){return response;}

        //删除关联项
        try{
            //删除与role的关联表
            actionService.deleteRelativityWithRoleByActionID(action.getId());
        }
        catch (Exception e) {
            //删除关联项失败，反馈异常
            response = ResponseMsg.EXCEPTION.getValue();
            throw new ControllerException("RoleController Exception :" + e.getMessage());
        }
//        删除权限在业务层中完成
//         传递给业务层
        try{
            actionService.deleteActionByID(action.getId());
        }catch (Exception e){
            response = ResponseMsg.EXCEPTION.getValue();
            throw new ControllerException("RoleController Exception :" + e.getMessage());
        }finally {
            return response;
        }
    }


    /**
     *@description:通过name、remark、controllerName,areaName,methodName模糊查找action
     *@date:2019.03.08
     *@author:Li Fei
     * @param:
     * @return:
     */
    @ResponseBody
    @PostMapping(value = "/searchActionInfo")
    public List<Action> searchActionInfo(@RequestBody Action action) throws ControllerException {
        List<Action> SearchResult=new ArrayList<>();
        //        根据name、remark模糊查找role
        //         传递给业务层
        try{
            SearchResult=actionService.searchActionInfo(action.getName(),action.getRemark(),action.getControllerName(),action.getAreaName(),action.getMethodName());
        }catch (Exception e){
            throw new ControllerException("RoleController Exception :" + e.getMessage());
        }finally {
            return SearchResult;
        }
    }
}
