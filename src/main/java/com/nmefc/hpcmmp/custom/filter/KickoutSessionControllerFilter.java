package com.nmefc.hpcmmp.custom.filter;

import com.nmefc.hpcmmp.entity.management.User;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @Author: QuYuan
 * @Description: 在线人数与并发数量控制过滤器
 * @Date: Created in 14:43 2019/3/15
 * @Modified By:
 */
public class KickoutSessionControllerFilter extends AccessControlFilter {
    /** 踢出后到的地址 */
    private String kickoutUrl;

    /**  踢出之前登录的/之后登录的用户 默认踢出之前登录的用户 */
    private boolean kickoutAfter = false;

    /**  同一个帐号最大会话数 默认1 */
    private int maxSession = 1;
    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    /**
     * @description: 表示访问拒绝时是否自己处理，如果返回true表示自己不处理且继续拦截器链执行，返回false表示自己已经处理了（比如重定向到另一个页面）。
     * @author: QuYuan
     * @date: 16:04 2019/3/15
     * @param: [servletRequest, servletResponse]
     * @return: boolean
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        if(!subject.isAuthenticated()&&!subject.isRemembered()){
            return true;
        }
        Session session = subject.getSession();
        //这里获取的User是实体 因为我在 自定义ShiroRealm中的doGetAuthenticationInfo方法中
        //new SimpleAuthenticationInfo(user, password, getName()); 传的是 User实体 所以这里拿到的也是实体,如果传的是userName 这里拿到的就是userName
        String account = ((User)subject.getPrincipal()).getAccount();
        Serializable sessionId = session.getId();
        // 初始化用户的队列放到缓存里
        Deque<Serializable> deque = cache.get(account);
        if(deque == null) {
            deque = new LinkedList<Serializable>();
            cache.put(account, deque);
        }
    //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if(!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            deque.push(sessionId);
        }
    //如果队列里的sessionId数超出最大会话数，开始踢人
        while(deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            if (kickoutAfter) { //如果踢出后者
                kickoutSessionId = deque.getFirst();
                kickoutSessionId = deque.removeFirst();
            } else { //否则踢出前者
                kickoutSessionId = deque.removeLast();
            }
            //交给SessionManager去执行
            try {
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if (kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

            //如果已经被踢出了
            if(session.getAttribute("kickout") != null){
                try{
                    subject.logout();
                }catch (Exception e){
                    e.printStackTrace();
                }
                WebUtils.issueRedirect(servletRequest,servletResponse,kickoutUrl);
                return false;
            }
           return true;
    }

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }
    public void setKickoutAfter(boolean kickoutAfter){
        this.kickoutAfter = kickoutAfter;

    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-activeSessionCache");
    }


}
