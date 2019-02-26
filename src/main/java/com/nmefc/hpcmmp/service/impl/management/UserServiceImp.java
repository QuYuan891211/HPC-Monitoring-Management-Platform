package com.nmefc.hpcmmp.service.impl.management;

import com.nmefc.hpcmmp.dao.management.UserMapper;
import com.nmefc.hpcmmp.entity.management.Role;
import com.nmefc.hpcmmp.entity.management.User;
import com.nmefc.hpcmmp.entity.management.UserExample;
import com.nmefc.hpcmmp.entity.management.association.UserRoleAssociation;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.impl.BaseServiceImp;
import com.nmefc.hpcmmp.service.management.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: QuYuan
 * @Description:
 * @Date: Created in 9:55 2019/2/22
 * @Modified By:
 */
@Service("userService")
public class UserServiceImp extends BaseServiceImp<User,UserExample,Integer> implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public List<User> accountDetected(User user) throws ServiceException {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(user.getAccount());
        List<User> list = new ArrayList<User>();
        try{
           list  = userMapper.selectByExample(example);
        }catch (Exception e){
            throw new ServiceException("Account Detection  Exception in Service: " + e.getMessage() );
        }

        return list;
    }

    public int insertSelective(User user) throws ServiceException {
        int row = 0;
        try{
            row = userMapper.insertSelective(user);
            if(user.getRoleList() != null && user.getRoleList().size() > 0){
                //注意此时user是没有ID的(Hibernate有数据自动回填，mybatis没有，需要思考解决策略)，需要从数据库中查出ID
                user.setId(accountDetected(user).get(0).getId());
                saveRelativity(user);
            }
        }catch (Exception e){
            throw  new ServiceException("Insert Exception in Service :" + e.getMessage());
        }

        return row;
    }


    /**
     * @description: 向用户角色关联表中传入数据
     * @author: QuYuan
     * @date: 21:55 2019/2/26
     * @param: [user]
     * @return: int
     */
    @Override
    public int saveRelativity(User user) throws ServiceException {
        int row = 0;
        List<Role> roleList = user.getRoleList();
        for(Role role:roleList){
            UserRoleAssociation userRoleAssociation = new UserRoleAssociation(user.getId(),role.getId());
            try {
                    row = userMapper.saveRelativity(userRoleAssociation);
            }catch(Exception e){
                throw  new ServiceException("Save Relativity Exception :" + e.getMessage());
            }
        }
        return row ;
    }

}
