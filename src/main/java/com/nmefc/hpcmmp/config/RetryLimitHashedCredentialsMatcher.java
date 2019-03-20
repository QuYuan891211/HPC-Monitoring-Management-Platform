package com.nmefc.hpcmmp.config;

import com.nmefc.hpcmmp.entity.management.User;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.management.UserService;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: QuYuan
 * @Description: 登录次数限制
 * @Date: Created in 13:40 2019/3/20
 * @Modified By:
 */
public class RetryLimitHashedCredentialsMatcher extends SimpleCredentialsMatcher {
    @Autowired
    private UserService userService;
    private Cache<String,AtomicInteger> passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager){
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String account = (String)token.getPrincipal();
        //尝试次数
        AtomicInteger retryCount = passwordRetryCache.get(account);
        //首次登陆
        if(retryCount == null){
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(account,retryCount);
        }
        if (retryCount.incrementAndGet() > 5){
            //如果用户登陆失败次数大于5次 抛出锁定用户异常  并修改数据库字段
            User user = new User();
            User record = null;
            user.setAccount(account);
            try {
                record = userService.accountDetected(user).get(0);
            } catch (ServiceException e) {
                e.printStackTrace();
            }

            if(record != null && !record.getIsLocked()){
                //数据库字段 默认为 0  就是正常状态 所以 要改为1
                //修改数据库的状态字段为锁定
                record.setIsLocked(true);
                try {
                    userService.updateByPrimaryKeySelective(record);
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }
            throw new LockedAccountException();
        }

        //判断用户是否登陆正确
        boolean matches = super.doCredentialsMatch(token,info);
        if(matches){
            passwordRetryCache.remove(account);
        }
        return matches;
    }
/**
 * @description: 解锁用户
 * @author: QuYuan
 * @date: 14:59 2019/3/20
 * @param: [username]
 * @return: void
 */
    public void unlockAccount(String account) {
        User user = new User();
        user.setAccount(account);
        List<User> userList = new ArrayList<>();
        try {
            userList = userService.accountDetected(user);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        if (userList.size() == 1) {
            //修改数据库的状态字段为锁定
            userList.get(0).setIsLocked(false);
            try {
                userService.updateByPrimaryKeySelective(userList.get(0));
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            passwordRetryCache.remove(account);
        }
    }
}
