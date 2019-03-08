package com.nmefc.hpcmmp.common.transPara;

import com.nmefc.hpcmmp.entity.management.Action;
import com.nmefc.hpcmmp.entity.management.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:zlyfs date:2019.03.04
 * @description:
 */
public class RolesRelate {
    private int relatedUserNum=-1;
    private int relatedActionNum=-1;
    public List<User> userList = new ArrayList<>();
    public List<Action> actionList = new ArrayList<>();
    public void cheakNum(){
        relatedUserNum=userList.size();
        relatedActionNum=actionList.size();
    }
    public int getRelatedUserNum() {return relatedUserNum;}
    public int getRelatedActionNum() {return relatedActionNum;}
}
