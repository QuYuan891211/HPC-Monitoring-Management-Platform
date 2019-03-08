package com.nmefc.hpcmmp.entity.management.association;

/**
 * @author:zlyfs date:2019.03.06
 * @description:role和action关联
 */
public class RoleActionAssociation {
    private Integer actionID;
    private Integer roleID;

    public RoleActionAssociation(Integer roleID, Integer actionID) {
        this.actionID = actionID;
        this.roleID = roleID;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public Integer getActionID() {
        return actionID;
    }

    public void setactionID(Integer actionID) {
        this.actionID= actionID;
    }
}
