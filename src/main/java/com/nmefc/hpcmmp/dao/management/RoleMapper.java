package com.nmefc.hpcmmp.dao.management;

import com.nmefc.hpcmmp.dao.BaseMapper;
import com.nmefc.hpcmmp.entity.management.Role;
import com.nmefc.hpcmmp.entity.management.RoleExample;
import java.util.List;

import com.nmefc.hpcmmp.entity.management.User;
import com.nmefc.hpcmmp.entity.management.Action;
import com.nmefc.hpcmmp.entity.management.association.*;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper extends BaseMapper<Role, RoleExample, Integer> {
    /**
     *@description:通过role的ID选择出相关联的user列表
     *@date:2019.03.04
     *@author:Li Fei
     * @param:
     * @return:
     */
    List<User> selectRelateUserByRoleID(Integer id);
    /**
     *@description:通过role的ID选择出相关联的user列表
     *@date:2019.03.04
     *@author:Li Fei
     * @param:
     * @return:
     */
    List<Action> selectRelateActionByRoleID(Integer id);
    /**
     *@description:存储权限及角色关系
     *@date:2019.03.06
     *@author:Li Fei
     * @param:
     * @return:
     */
    int saveUserRelativity(UserRoleAssociation userRoleAssociation);
    /**
     *@description:存储权限及角色关系
     *@date:2019.03.06
     *@author:Li Fei
     * @param:
     * @return:
     */
    int saveActionRelativity(RoleActionAssociation roleActionAssociation);
/**
 *@description:通过role的id删除与user的关联项
 *@date:2019.03.06
 *@author:Li Fei
 * @param:
 * @return:
 */
    int deleteRelativityWithUserByRoleID(Integer id);

    /**
     *@description:通过role的id删除与action的关联项
     *@date:2019.03.06
     *@author:Li Fei
     * @param:
     * @return:
     */
    int deleteRelativityWithActionByRoleID(Integer id);

    /**
     *@description:通过id删除role
     *@date:2019.03.07
     *@author:Li Fei
     * @param:
     * @return:
     */
    int deleteRoleByID(Integer id);

    /**
     *@description:通过name和remark搜索相应role
     *@date:2019.03.08
     *@author:Li Fei
     * @param:
     * @return:
     */
    List<Role> searchRoleInfo(String name,String remark);
}