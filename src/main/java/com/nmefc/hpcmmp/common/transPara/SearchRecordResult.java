package com.nmefc.hpcmmp.common.transPara;
import com.nmefc.hpcmmp.entity.dutymanagement.Record;
import com.nmefc.hpcmmp.entity.management.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:zlyfs date:2019.05.31
 * @description:
 */
public class SearchRecordResult extends Record {
    private List<User> modifier = new ArrayList<User>();
    public List<User> getModifier(){
        return modifier;
    }
}
