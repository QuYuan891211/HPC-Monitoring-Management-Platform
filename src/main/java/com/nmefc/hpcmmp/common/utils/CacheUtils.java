package com.nmefc.hpcmmp.common.utils;

import com.nmefc.hpcmmp.config.ShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

/**
 * @Author: QuYuan
 * @Description:
 * @Date: Created in 17:20 2019/3/14
 * @Modified By:
 */
public class CacheUtils {
    public static void clearAllCache(){
        //清除缓存
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        ShiroRealm shiroRealm = (ShiroRealm) securityManager.getRealms();
        shiroRealm.clearAllCache();
    }
}
