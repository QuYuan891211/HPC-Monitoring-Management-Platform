package com.nmefc.hpcmmp.controller.management;

import com.nmefc.hpcmmp.common.enumPackage.Regex;
import com.nmefc.hpcmmp.common.enumPackage.ResponseMsg;
import com.nmefc.hpcmmp.common.utils.DateTimeUtils;
import com.nmefc.hpcmmp.common.transPara.RolesRelate;
import com.nmefc.hpcmmp.entity.management.Role;
import com.nmefc.hpcmmp.entity.management.User;
import com.nmefc.hpcmmp.entity.management.Action;
import com.nmefc.hpcmmp.exception.ControllerException;
import com.nmefc.hpcmmp.service.management.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    /**
     *@description:插入角色
     *@date:2019.03.01
     *@author:Li Fei
     * @param:
     * @return:
     */
    @ResponseBody
    @PostMapping(value = "/insertRoleInfo")
    public String insertRoleInfo(@RequestBody Role role) throws ControllerException {
        if(role == null || role.getId() !=null){return ResponseMsg.REQUEST_ERROR.getValue();}//无数据或ID已存在则报错
        String response = ResponseMsg.SUCCESS.getValue();
        //进行数据校验
        response = check(role,response);
        //数据校验失败，反馈失败原因
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
     * @description: 数据校验（用于新增）
     * @author: QuYuan
     * @date: 12:00 2019/2/24
     * @param: [user, response]
     * @return: java.lang.String
     */
    private String check(Role role,String response) throws ControllerException {
        //          1.检测必须项，名字不得为空、
        if(role == null || role.getName() ==null || role.getName().length() == 0) {
            return ResponseMsg.PARAMETERS_MISSING.getValue();
        }
//        2.检测所填各项是否合规，并补齐默认值
//        检查是否超出长度及违规字符
        List<String> temp1 = new LinkedList<>();
        temp1.add(role.getName());
        if(!Regex.NAME.getDetectResult(temp1)){return ResponseMsg.PAREMETERE_ERROR.getValue();}
        //3.检测优先级是否符合规范

        //4.检测备注是否合规

        return response;
    }

    /**
     *@description:修改角色
     *@date:2019.03.04
     *@author:Li Fei
     * @param:
     * @return:
     */
    @ResponseBody
    @PostMapping(value = "/updateRoleInfo")
    public String updateRoleInfo(@RequestBody Role role) throws ControllerException  {
        String response = ResponseMsg.SUCCESS.getValue();
        //进行数据校验
        response = updateCheck(role,response);
        //数据校验失败，反馈失败原因
        if(!response.equals(ResponseMsg.SUCCESS.getValue())){return response;}
        //        更新时间设置为新建时间
        role.setGmtModified(DateTimeUtils.date2timestamp(new Date()));

//        关联权限在业务层中完成
//         传递给业务层
        try{
//            修改role
            roleService.updateByPrimaryKeySelective(role);
//            删除与action的关联
            roleService.deleteRelativityWithActionByRoleID(role.getId());
//            此处不进行与user的关联，该功能在user中进行
//            roleService.deleteRelativityWithUserByRoleID(role.getId());
//            重做与action的关联
            roleService.saveActionRelativity(role);
//            roleService.saveUserRelativity(role);
        }catch (Exception e){
            response = ResponseMsg.EXCEPTION.getValue();
            throw new ControllerException("RoleController Exception :" + e.getMessage());
        }finally {
            return response;
        }
    }

    /**
     *@description:数据校验（用于修改）
     *@date:2019.03.04
     *@author:Li Fei
     * @param:
     * @return:
     */
    private String updateCheck(Role role,String response) throws ControllerException {
        //          1.检测必须项，名字不得为空、
        if(role == null ||role.getName().length() == 0||role.getId()==null) {
            return ResponseMsg.PARAMETERS_MISSING.getValue();
        }
//        2.检测所填各项是否合规，并补齐默认值
//        检查是否超出长度及违规字符
        List<String> temp1 = new LinkedList<String>();
        temp1.add(role.getName());
        if(!Regex.NAME.getDetectResult(temp1)){return ResponseMsg.PAREMETERE_ERROR.getValue();}
        //3.检测优先级是否符合规范

        //4.检测备注是否合规

        return response;
    }



//    /**
//     *@description:检查是否有关联项，查找所有关联项，并返回关联的user和action
//     *@date:2019.03.04
//     *@author:Li Fei
//     * @param:输入ID即可
//     * {
//     * 	"id": 1
//     * }
//     * @return:
//     */
//    @ResponseBody
//    @PostMapping(value = "/checkRoleRelate")
//    public Role checkRoleRelate(@RequestBody Role role) throws ControllerException {
//
//        RolesRelate rolesRelate=new RolesRelate();
//        //检查所有关联
//        try{
//            rolesRelate=checkRolesRelate(role);
//            role.setActionList(rolesRelate.actionList);
//            role.setUserList(rolesRelate.userList);
//        }
//        catch (Exception e){
//            throw new ControllerException("RoleController Exception :" + e.getMessage());
//        }
//        finally {
//            return role;
//        }
//
//    }

    /**
     *@description:查找所有关联项，并返回关联的user和action
     *@date:2019.03.06
     *@author:Li Fei
     * @param:
     * @return:
     */
    private RolesRelate checkRolesRelate(Role role){
        RolesRelate rolesRelate=new RolesRelate();

        //检查所有关联的user
        List<User> userList = new ArrayList<>();
        userList = roleService.selectRelateUserByRole(role);

        //检查所有关联的action
        List<Action> actionList = new ArrayList<>();
        actionList = roleService.selectRelateActionByRole(role);

        rolesRelate.userList=userList;
        rolesRelate.actionList=actionList;
        rolesRelate.cheakNum();
        return rolesRelate;
    }

    /**
     *@description:通过id查看role以及相关关联
     *@date:2019.03.07
     *@author:Li Fei
     * @param:
     * @return:
     */
    @ResponseBody
    @PostMapping(value = "/checkRoleByID")
    public Role checkRoleByID(@RequestBody Role role)
            throws ControllerException {

        //查找对应role的参数
        try{
            role=roleService.selectByPrimaryKey(role.getId());
        }
        catch (Exception e){
            throw new ControllerException("RoleController Exception :" + e.getMessage());
        }

        RolesRelate rolesRelate=new RolesRelate();
        //检查所有关联
        try{
            rolesRelate=checkRolesRelate(role);
            role.setActionList(rolesRelate.actionList);
            role.setUserList(rolesRelate.userList);
        }
        catch (Exception e){
            throw new ControllerException("RoleController Exception :" + e.getMessage());
        }
        finally {
            return role;
        }

    }
    /**
     *@description:删除角色及关联项
     *@date:2019.03.04
     *@author:Li Fei
     * @param:
     * @return:
     */
    @ResponseBody
    @PostMapping(value = "/deleteRoleInfo")
    public String deleteRoleInfo(@RequestBody Role role) throws ControllerException  {
        String response = ResponseMsg.SUCCESS.getValue();
        //进行数据校验必须有roleID>0
        try{
            if(role.getId()<=0) response=ResponseMsg.PARAMETERS_MISSING.getValue();
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
            //删除与user的
            roleService.deleteRelativityWithUserByRoleID(role.getId());
            //删除与action的
            roleService.deleteRelativityWithActionByRoleID(role.getId());
        }
        catch (Exception e) {
            //删除关联项失败，反馈异常
            response = ResponseMsg.EXCEPTION.getValue();
            throw new ControllerException("RoleController Exception :" + e.getMessage());
        }
//        关联权限在业务层中完成
//         传递给业务层
        try{
            roleService.deleteRoleByID(role.getId());
        }catch (Exception e){
            response = ResponseMsg.EXCEPTION.getValue();
            throw new ControllerException("RoleController Exception :" + e.getMessage());
        }finally {
            return response;
        }
    }

    /**
     *@description:根据name、remark模糊查找role
     *@date:2019.03.04
     *@author:Li Fei
     * @param:
     * @return:
     */
    @ResponseBody
    @GetMapping(value = "/searchRoleInfo")
    public List<Role> searchRoleInfo(@RequestBody Role role) throws ControllerException{
        List<Role> SearchResult=new ArrayList<>();
        //        根据name、remark模糊查找role
        //         传递给业务层
        try{
            SearchResult=roleService.searchRoleInfo(role.getName(),role.getRemark());
        }catch (Exception e){
            throw new ControllerException("RoleController Exception :" + e.getMessage());
        }finally {
            return SearchResult;
        }

    }



}