package com.nmefc.hpcmmp.config;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: QuYuan
 * @Description: Session 监听器
 * @Date: Created in 21:31 2019/3/14
 * @Modified By:
 */
public class ShiroSessionListener implements SessionListener {


    //线程安全
    private final AtomicInteger sessionCount = new AtomicInteger();
    /**
     * @description: 开启一个新对话
     * @author: QuYuan
     * @date: 22:08 2019/3/14
     * @param: [session]
     * @return: void
     */
    @Override
    public void onStart(Session session) {
        sessionCount.incrementAndGet();
    }
    /**
     * @description: 关闭一个对话
     * @author: QuYuan
     * @date: 22:08 2019/3/14
     * @param: [session]
     * @return: void
     */

    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
    }
    /**
     * @description: 会话过期
     * @author: QuYuan
     * @date: 22:09 2019/3/14
     * @param: [session]
     * @return: void
     */
    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
    }
    /**
     * @description: 获取当前会话数
     * @author: QuYuan
     * @date: 22:08 2019/3/14
     * @param: []
     * @return: java.util.concurrent.atomic.AtomicInteger
     */
    public AtomicInteger getSessionCount() {
        return sessionCount;
    }

}
