package com.nmefc.hpcmmp.common.utils;

import com.nmefc.hpcmmp.entity.management.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @Author: QuYuan
 * @Description:
 * @Date: Created in 19:46 2019/5/23
 * @Modified By:
 */
public class UserSessionContexts {
    public static final String GLOBLE_USER_SESSION = "globle_user";
    public static User getUser(){
        Subject subject = SecurityUtils.getSubject();
        User user=(User) subject.getPrincipal();
        return user;
    }
}
