package com.nmefc.hpcmmp.service.management;

import com.nmefc.hpcmmp.entity.management.Action;
import com.nmefc.hpcmmp.entity.management.ActionExample;
import com.nmefc.hpcmmp.entity.management.Role;
import com.nmefc.hpcmmp.entity.management.association.RoleActionAssociation;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.BaseService;

import java.util.List;

/**
 * @Author: QuYuan
 * @Description:
 * @Date: Created in 9:54 2019/2/22
 * @Modified By:
 */
public interface ActionService extends BaseService<Action,ActionExample,Integer> {
    /**
     *@description:根据action的ID查找所有与之关联的role
     *@date:2019.03.08
     *@author:Li Fei
     * @param:
     * @return:
     */
    List<Role> selectRelateRoleByActionID(Action action);

    /**
     *@description:根据name、remark、controllerName、areaName、methodName模糊查找action
     *@date:2019.03.08
     *@author:Li Fei
     * @param:
     * @return:
     */
    List<Action> searchActionInfo(String name,String remark,String controllerName,String areaName,String methodName);

    /**
     *@description:根据actionID删除与role的关联表内容
     *@date:2019.03.08
     *@author:Li Fei
     * @param:
     * @return:
     */
    int deleteRelativityWithRoleByActionID(Integer id);

    /**
     *@description:通过id删除Action
     *@date:2019.03.07
     *@author:Li Fei
     * @param:
     * @return:
     */
    int deleteActionByID(Integer id);

    /**
     *@description:建立action与role的关联项
     *@date:2019.03.08
     *@author:Li Fei
     * @param:
     * @return:
     */
    int saveRoleRelativity(Action action) throws ServiceException;

    /**
     *@description:检测URL是否唯一
     *@date:2019.03.08
     *@author:Li Fei
     * @param:
     * @return:
     */
    boolean checkURL(String url);
}
