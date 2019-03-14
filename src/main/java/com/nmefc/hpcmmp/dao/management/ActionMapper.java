package com.nmefc.hpcmmp.dao.management;

import com.nmefc.hpcmmp.dao.BaseMapper;
import com.nmefc.hpcmmp.entity.management.Action;
import com.nmefc.hpcmmp.entity.management.ActionExample;
import com.nmefc.hpcmmp.entity.management.*;
import java.util.List;

import com.nmefc.hpcmmp.entity.management.association.RoleActionAssociation;
import org.apache.ibatis.annotations.Param;

public interface ActionMapper extends BaseMapper<Action, ActionExample, Integer> {

    /**
     *@description:根据action的ID查找所有与之关联的role
     *@date:2019.03.08
     *@author:Li Fei
     * @param:
     * @return:
     */
    List<Role> selectRelateRoleByActionID(Integer id);

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
    int saveRoleRelativity(RoleActionAssociation roleActionAssociation);

    /**
     *@description:检测URL
     *@date:2019.03.08
     *@author:Li Fei
     * @param:
     * @return:
     */
    List<Action> checkURL(String url);
}