package com.nmefc.hpcmmp.entity.management.association;

import com.nmefc.hpcmmp.entity.management.Role;
import com.nmefc.hpcmmp.entity.management.User;

/**
 * @Author: QuYuan
 * @Description: 关联
 * @Date: Created in 18:46 2019/2/26
 * @Modified By:
 */
public class UserRoleAssociation {
    private Integer userID;
    private Integer roleID;

    public UserRoleAssociation(Integer userID, Integer roleID) {
        this.userID = userID;
        this.roleID = roleID;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }
}
