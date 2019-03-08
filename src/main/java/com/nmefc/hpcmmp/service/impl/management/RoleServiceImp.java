package com.nmefc.hpcmmp.service.impl.management;

import com.nmefc.hpcmmp.common.transPara.RolesRelate;
import com.nmefc.hpcmmp.dao.management.UserMapper;
import com.nmefc.hpcmmp.entity.management.*;
import com.nmefc.hpcmmp.entity.management.association.UserRoleAssociation;
import com.nmefc.hpcmmp.entity.management.association.*;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.impl.BaseServiceImp;
import com.nmefc.hpcmmp.service.management.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nmefc.hpcmmp.dao.management.RoleMapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: QuYuan
 * @Description: 角色业务逻辑层实现
 * @Date: Created in 16:06 2019/2/25
 * @Modified By:
 */
@Service("roleService")
public class RoleServiceImp extends BaseServiceImp<Role,RoleExample,Integer>  implements RoleService{
    @Autowired
    private RoleMapper roleMapper;


    /**
     *@description:重写插入用户功能
     *@date:2019.03.06
     *@author:Li Fei
     * @param:
     * @return:
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 3600,rollbackFor = Exception.class)
    public int insertSelective(Role role) throws ServiceException {
        int row = 0;
        try{
            row = roleMapper.insertSelective(role);
            if(role.getActionList() != null && role.getActionList().size() > 0){
                //注意此时role是没有ID的(Hibernate有数据自动回填，mybatis没有，需要思考解决策略)，需要从数据库中查出ID
                role.setId(modifiedDetected(role).get(0).getId());
                saveActionRelativity(role);
            }
        }catch (Exception e){
            throw  new ServiceException("Insert Exception in Service :" + e.getMessage());
        }

        return row;
    }

    /**
     *@description:通过modified(修改时间)反查ID
     *@date:2019.03.06
     *@author:Li Fei
     * @param:
     * @return:
     */
    @Override
    public List<Role> modifiedDetected(Role role) throws ServiceException {
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        criteria.andGmtModifiedEqualTo(role.getGmtModified());
        List<Role> list = new ArrayList<Role>();
        try{
            list  = roleMapper.selectByExample(example);
        }catch (Exception e){
            throw new ServiceException("Account Detection  Exception in Service: " + e.getMessage() );
        }

        return list;
    }

    /**
     *@description:向用户角色关联表中传入数据
     *@date:2019.03.06
     *@author:Li Fei
     * @param:
     * @return:
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 3600,rollbackFor = Exception.class)
    public int saveActionRelativity(Role role) throws ServiceException {
        int row = 0;
        List<Action> actionList = role.getActionList();
        for(Action action:actionList){
            RoleActionAssociation roleActionAssociation = new RoleActionAssociation(role.getId(),action.getId());
            try {
                row = roleMapper.saveActionRelativity(roleActionAssociation);
            }catch(Exception e){
                throw  new ServiceException("Save Relativity Exception :" + e.getMessage());
            }
        }
        return row ;
    }

    /**
     *@description:向用户角色关联表中传入数据
     *@date:2019.03.06
     *@author:Li Fei
     * @param:
     * @return:
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 3600,rollbackFor = Exception.class)
    public int saveUserRelativity(Role role) throws ServiceException {
        int row = 0;
        List<User> userList = role.getUserList();
        for(User user:userList){
            UserRoleAssociation userRoleAssociation = new UserRoleAssociation(user.getId(),role.getId());
            try {
                row = roleMapper.saveUserRelativity(userRoleAssociation);
            }catch(Exception e){
                throw  new ServiceException("Save Relativity Exception :" + e.getMessage());
            }
        }
        return row ;
    }

    @Override
    public String check(Role record, String response) throws ServiceException {
        return null;
    }
    /**
     *@description:通过role选择出相关联的user列表
     *@date:2019.03.04
     *@author:Li Fei
     * @param:
     * @return:
     */
    @Override
    public List<User> selectRelateUserByRole(Role role) {
        List<User> userList = new ArrayList<>();
        if (role == null){ return null; }
        try {
            userList = roleMapper.selectRelateUserByRoleID(role.getId());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return userList;
        }
    }


    /**
     *@description:通过role选择出相关联的action列表
     *@date:2019.03.04
     *@author:Li Fei
     * @param:
     * @return:
     */
    @Override
    public List<Action> selectRelateActionByRole(Role role) {
        List<Action> actionList = new ArrayList<>();
        if (role == null){ return null; }
        try {
            actionList = roleMapper.selectRelateActionByRoleID(role.getId());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return actionList;
        }
    }

    /**
     *@description:删除role与user的所有关联项
     *@date:2019.03.07
     *@author:Li Fei
     * @param:
     * @return:
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 3600,rollbackFor = Exception.class)
    public int deleteRelativityWithUserByRoleID(Integer id) {
        int row = 0;
        if(id!=null){
            try{
                row = roleMapper.deleteRelativityWithUserByRoleID(id);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return row;
    }

    /**
     *@description:删除role与action所有的关联项
     *@date:2019.03.07
     *@author:Li Fei
     * @param:
     * @return:
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 3600,rollbackFor = Exception.class)
    public int deleteRelativityWithActionByRoleID(Integer id) {
        int row = 0;
        if(id!=null){
            try{
                row = roleMapper.deleteRelativityWithActionByRoleID(id);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return row;
    }

    /**
     *@description:通过id删除role
     *@date:2019.03.07
     *@author:Li Fei
     * @param:
     * @return:
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 3600,rollbackFor = Exception.class)
    public int deleteRoleByID(Integer id) {
        int row = 0;
        if(id!=null){
            try{
                row = roleMapper.deleteRoleByID(id);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return row;
    }

    @Override
    public List<Role> searchRoleInfo(String name, String remark) {
        List<Role> role=new ArrayList<>();
        name='%'+name+'%';
        remark='%'+remark+'%';
        try{
            role=roleMapper.searchRoleInfo(name,remark);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return role;
    }
}
