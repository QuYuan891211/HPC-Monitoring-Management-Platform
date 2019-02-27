package com.nmefc.hpcmmp.dao.management;

import com.nmefc.hpcmmp.dao.BaseMapper;
import com.nmefc.hpcmmp.entity.management.User;
import com.nmefc.hpcmmp.entity.management.UserExample;
import java.util.List;

import com.nmefc.hpcmmp.entity.management.association.UserRoleAssociation;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User, UserExample, Integer> {

    /**
     * @description: 存储用户及角色关系
     * @author: QuYuan
     * @date: 20:20 2019/2/27
     * @param: [userRoleAssociation]
     * @return: int
     */
    int saveRelativity(UserRoleAssociation userRoleAssociation) ;

    /**
     * @description: 根据id解除所有相关的角色关联
     * @author: QuYuan
     * @date: 20:20 2019/2/27
     * @param: [id]
     * @return: int
     */
    int deleteRelativityByUserID(Integer id);
    /**
     * @description: 查找指定id的用户信息及其角色信息
     * @author: QuYuan
     * @date: 20:19 2019/2/27
     * @param: [id]
     * @return: com.nmefc.hpcmmp.entity.management.User
     */
    User selectUserRoleByUserID(Integer id);
    /**
     * @description: 查找所有用户（附带角色信息）
     * @author: QuYuan
     * @date: 20:19 2019/2/27
     * @param: []
     * @return: java.util.List<com.nmefc.hpcmmp.entity.management.User>
     */
    List<User> selectAllUserWithRoleInfo();
}