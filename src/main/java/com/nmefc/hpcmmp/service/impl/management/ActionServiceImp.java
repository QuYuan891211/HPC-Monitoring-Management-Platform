package com.nmefc.hpcmmp.service.impl.management;

import com.nmefc.hpcmmp.common.enumPackage.Regex;
import com.nmefc.hpcmmp.common.enumPackage.ResponseMsg;
import com.nmefc.hpcmmp.entity.management.Action;
import com.nmefc.hpcmmp.entity.management.ActionExample;
import com.nmefc.hpcmmp.entity.management.Role;
import com.nmefc.hpcmmp.entity.management.User;
import com.nmefc.hpcmmp.entity.management.association.RoleActionAssociation;
import com.nmefc.hpcmmp.entity.management.association.UserRoleAssociation;
import com.nmefc.hpcmmp.exception.ControllerException;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.BaseService;
import com.nmefc.hpcmmp.service.impl.BaseServiceImp;
import com.nmefc.hpcmmp.service.management.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nmefc.hpcmmp.dao.management.ActionMapper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: QuYuan
 * @Description:
 * @Date: Created in 18:38 2019/2/28
 * @Modified By:
 */
@Service("actionService")
public class ActionServiceImp extends BaseServiceImp<Action,ActionExample,Integer> implements ActionService{
    @Autowired
    private ActionMapper actionMapper;
    @Override
    public String check(Action action, String response) throws ServiceException {
        //          1.检测必须项
        if(action == null || action.getName() ==null || action.getName().length() == 0 ||action.getUrl() == null ||action.getUrl().length() == 0 || action.getTypeEnum() ==null || action.getMenuIcon() == null ||action.getMenuIcon().length() == 0||action.getIconWidth() == null||action.getIconHeight() == null||action.getMethodTypeEnum()==null) {
            return ResponseMsg.PARAMETERS_MISSING.getValue();
        }


        //        2.检测所填各项是否合规，并补齐默认值
//        检查是否超出长度及违规字符
        List<String> temp1 = new LinkedList<>();
        temp1.add(action.getName());
        if(!Regex.NAME.getDetectResult(temp1)){return ResponseMsg.PAREMETERE_ERROR.getValue();}
        return response;
    }

    /**
     *@description:根据action的ID查找所有与之关联的role
     *@date:2019.03.08
     *@author:Li Fei
     * @param:
     * @return:
     */
    @Override
    public List<Role> selectRelateRoleByActionID(Action action) {
        List<Role> roleList = new ArrayList<>();
        if (action == null){ return null; }
        try {
            roleList = actionMapper.selectRelateRoleByActionID(action.getId());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return roleList;
        }
    }

    /**
     *@description:根据name、remark、controllerName、areaName、methodName模糊查找action
     *@date:2019.03.08
     *@author:Li Fei
     * @param:
     * @return:
     */
    @Override
    public List<Action> searchActionInfo(String name, String remark, String controllerName, String areaName, String methodName) {
        List<Action> action=new ArrayList<>();
        name='%'+name+'%';
        remark='%'+remark+'%';
        controllerName='%'+controllerName+'%';
        areaName='%'+areaName+'%';
        methodName='%'+methodName+'%';
        try{
            action=actionMapper.searchActionInfo(name,remark,controllerName,areaName,methodName);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return action;
    }

    /**
     *@description:根据actionID删除与role的关联表内容
     *@date:2019.03.08
     *@author:Li Fei
     * @param:
     * @return:
     */
    @Override
    public int deleteRelativityWithRoleByActionID(Integer id) {
        int row = 0;
        if(id!=null){
            try{
                row = actionMapper.deleteRelativityWithRoleByActionID(id);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return row;
    }

    /**
     *@description:通过id删除Action
     *@date:2019.03.07
     *@author:Li Fei
     * @param:
     * @return:
     */
    @Override
    public int deleteActionByID(Integer id) {
        int row = 0;
        if(id!=null){
            try{
                row = actionMapper.deleteActionByID(id);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return row;
    }

    /**
     *@description:建立action与role的关联项
     *@date:2019.03.08
     *@author:Li Fei
     * @param:
     * @return:
     */
    @Override
    public int saveRoleRelativity(Action action) throws ServiceException {
        int row = 0;
        List<Role> roleList = action.getRoleList();
        for(Role role:roleList){
            RoleActionAssociation roleActionAssociation = new RoleActionAssociation(role.getId(),action.getId());
            try {
                row = actionMapper.saveRoleRelativity(roleActionAssociation);
            }catch(Exception e){
                throw  new ServiceException("Save Relativity Exception :" + e.getMessage());
            }
        }
        return row ;
    }

    /**
     *@description:检测URL是否唯一
     *@date:2019.03.08
     *@author:Li Fei
     * @param:
     * @return:
     */
    @Override
    public boolean checkURL(String url) {
        boolean newURL=false;
        List<Action> list=actionMapper.checkURL(url);
        if(list.isEmpty()) newURL=true;
        return newURL;
    }
}
