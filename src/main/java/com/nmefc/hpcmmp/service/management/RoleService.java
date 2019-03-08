package com.nmefc.hpcmmp.service.management;

import com.nmefc.hpcmmp.common.transPara.RolesRelate;
import com.nmefc.hpcmmp.entity.management.Role;
import com.nmefc.hpcmmp.entity.management.RoleExample;
import com.nmefc.hpcmmp.entity.management.User;
import com.nmefc.hpcmmp.entity.management.Action;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.BaseService;

import java.util.List;

/**
 * @Author: QuYuan
 * @Description:
 * @Date: Created in 9:53 2019/2/22
 * @Modified By:
 */
public interface RoleService extends BaseService<Role,RoleExample,Integer> {
    /**
     *@description:通过role选择出相关联的user列表
     *@date:2019.03.04
     *@author:Li Fei
     * @param:
     * @return:
     */
    List<User> selectRelateUserByRole(Role role);

    /**
     *@description:通过role选择出相关联的user列表
     *@date:2019.03.04
     *@author:Li Fei
     * @param:
     * @return:
     */
    List<Action> selectRelateActionByRole(Role role);
    /**
     *@description:向用户角色关联表中传入数据建立role和action关联
     *@date:2019.03.06
     *@author:Li Fei
     * @param:
     * @return:
     */
    int saveActionRelativity(Role role)throws ServiceException;
    /**
     *@description:向用户角色关联表中传入数据建立role和user关联
     *@date:2019.03.06
     *@author:Li Fei
     * @param:
     * @return:
     */
    int saveUserRelativity(Role role)throws ServiceException;

    /**
     *@description:通过modified反查ID
     *@date:2019.03.06
     *@author:Li Fei
     * @param:
     * @return:
     */
    List<Role> modifiedDetected(Role role) throws ServiceException;

    /**
     *@description:删除role与user的关联项
     *@date:2019.03.06
     *@author:Li Fei
     * @param:
     * @return:
     */
    int deleteRelativityWithUserByRoleID(Integer id);

    /**
     *@description:删除role与action的关联项
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
