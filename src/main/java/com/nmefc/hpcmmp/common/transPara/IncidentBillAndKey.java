package com.nmefc.hpcmmp.common.transPara;

import com.nmefc.hpcmmp.entity.dutymanagement.IncidentBill;

/**
 * @author:zlyfs date:2019/8/23
 * @description:
 */
public class IncidentBillAndKey {
    public IncidentBill incidentBill;
    private String key;
    private int nextRoleID;
    private boolean ifEnd;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getnextRoleID() {
        return nextRoleID;
    }

    public void setnextRoleID(int nextRoleID) {
        this.nextRoleID = nextRoleID;
    }

    public boolean getifEnd() {
        return ifEnd;
    }
}
