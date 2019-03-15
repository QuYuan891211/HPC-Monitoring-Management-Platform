package com.nmefc.hpcmmp.config;

import com.nmefc.hpcmmp.custom.filter.KickoutSessionControllerFilter;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * @Author: QuYuan
 * @Description: Shiro的配置信息
 * @Date: Created in 15:06 2019/3/9
 * @Modified By:
 */
@Configuration
public class ShiroConfig {
    /**
     * @description: ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * Web应用中,Shiro可控制的Web请求必须经过Shiro主过滤器的拦截
     * @author: QuYuan
     * @date: 15:19 2019/3/9
     * @param: [securityManager]
     * @return: org.apache.shiro.spring.web.ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("getSecurityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //1.设置安全管理器接口
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //2.设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //3.设置登陆成功后跳转的页面
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //4.未授权界面,该配置无效，并不会进行页面跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        //自定义拦截器限制并发人数,参考博客：
        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        //限制同一帐号同时在线的个数
        filtersMap.put("kickout", getKickoutSessionControllerFilter());
        //ShiroFilterFactoryBean既可以设置过滤链，也可以设置自定义过滤器
        shiroFilterFactoryBean.setFilters(filtersMap);
        // 5. 配置访问权限 必须是LinkedHashMap，因为它必须保证有序
        // 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 一定要注意顺序,否则就不好使了
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //6.配置不登录可以访问的资源
        filterChainDefinitionMap.put("/login", "kickout,anon");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        //logout是shiro提供的过滤器
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/**", "kickout");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager getSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置自定义realm.
        securityManager.setRealm(getShiroRealm());
        //配置记住我 参考博客：
        //securityManager.setRememberMeManager(rememberMeManager());

        //配置 ehcache缓存管理器 参考博客：
        securityManager.setCacheManager(getEhCacheManager());

        //配置自定义session管理，使用redis 参考博客：
        securityManager.setSessionManager(getSessionManager());

        return securityManager;
    }


    /**
     * @description: 配置Bean生命周期处理器
     * @author: QuYuan
     * @date: 15:42 2019/3/9
     * @param: []
     * @return: org.apache.shiro.spring.LifecycleBeanPostProcessor
     */
    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * @description: Authentication 的 realm
     * @author: QuYuan
     * @date: 15:53 2019/3/9
     * @param:
     * @return:
     */
    @Bean
    public ShiroRealm getShiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCachingEnabled(true);
        //启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
        shiroRealm.setAuthenticationCachingEnabled(true);
        //缓存AuthenticationInfo信息的缓存名称 在ehcache-shiro.xml中有对应缓存的配置
        shiroRealm.setAuthenticationCacheName("authenticationCache");
        //启用授权缓存，即缓存AuthorizationInfo信息，默认false
        shiroRealm.setAuthorizationCachingEnabled(true);
        //缓存AuthorizationInfo信息的缓存名称  在ehcache-shiro.xml中有对应缓存的配置
        shiroRealm.setAuthorizationCacheName("authorizationCache");
        return shiroRealm;
    }
//    /**
//     * @description: 使用shiro控制按钮
//     * @author: QuYuan
//     * @date: 15:55 2019/3/9
//     * @param: []
//     * @return: at.pollux.thymeleaf.shiro.dialect.ShiroDialect
//     */
//    @Bean
//    public ShiroDialect shiroDialect() {
//        return new ShiroDialect();
//    }

    /**
     * @description: 开启注解模式
     * @author: QuYuan
     * @date: 20:57 2019/3/12
     * @param: [securityManager]
     * @return: org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(@Qualifier("getSecurityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    @DependsOn({"getLifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;

    }

    /**
     * @description: 缓存管理器
     * @author: QuYuan
     * @date: 14:50 2019/3/14
     * @param: []
     * @return: org.apache.shiro.cache.ehcache.EhCacheManager
     */
    @Bean
    public EhCacheManager getEhCacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:shiro/ehcache-shiro.xml");
        return cacheManager;
    }

    @Bean
    public MethodInvokingFactoryBean getMethodInvokingFactoryBean(){
        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
        factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        factoryBean.setArguments(new Object[]{getSecurityManager()});
        return factoryBean;
    }
    /**
     * @description: 配置ID会话生成器
     * @author: QuYuan
     * @date: 22:12 2019/3/14
     * @param: []
     * @return: org.apache.shiro.session.mgt.eis.SessionIdGenerator
     */
    @Bean
    public SessionIdGenerator getSessionIdGenerator(){
        return new JavaUuidSessionIdGenerator();
    }
    @Bean
    public ShiroSessionListener getShiroSessionListener(){
        return new ShiroSessionListener();

    }

    /**
     * @description: 配置 SessionDao
     * @author: QuYuan
     * @date: 10:52 2019/3/15
     * @param: []
     * @return: org.apache.shiro.session.mgt.eis.SessionDAO
     */
    @Bean
    public SessionDAO sessionDAO(){
        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
        enterpriseCacheSessionDAO.setCacheManager(getEhCacheManager());
        enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        enterpriseCacheSessionDAO.setSessionIdGenerator(getSessionIdGenerator());
        return enterpriseCacheSessionDAO;
    }

    /**
     * @description: 将Session存入cookie中
     * @author: QuYuan
     * @date: 11:05 2019/3/15
     * @param: []
     * @return: org.apache.shiro.web.servlet.SimpleCookie
     */
    @Bean
    public SimpleCookie getSessionIdCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //maxAge=-1表示浏览器关闭时失效此Cookie,虽然失效，但无法清空，还需要在session管理器中额外配置
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    @Bean
    public SessionManager getSessionManager() {

        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        //配置监听
        listeners.add(getShiroSessionListener());
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionIdCookie(getSessionIdCookie());
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setCacheManager(getEhCacheManager());

        //全局会话超时时间（单位毫秒），默认30分钟  暂时设置为10秒钟 用来测试
        //sessionManager.setGlobalSessionTimeout(1800000);
        //是否开启删除无效的session对象  默认为true
        sessionManager.setDeleteInvalidSessions(true);
        //是否开启定时调度器进行检测过期session 默认为true
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
        //设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
        //暂时设置为 5秒 用来测试
        //sessionManager.setSessionValidationInterval(3600000);
        //取消url 后面的 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;

    }
    /**
     * @description: 并发登录控制器
     * @author: QuYuan
     * @date: 16:05 2019/3/15
     * @param: []
     * @return: com.nmefc.hpcmmp.custom.filter.KickoutSessionControllerFilter
     */
    @Bean
    public KickoutSessionControllerFilter getKickoutSessionControllerFilter(){
        KickoutSessionControllerFilter kickoutSessionControlFilter = new KickoutSessionControllerFilter();
        kickoutSessionControlFilter.setSessionManager(getSessionManager());
        kickoutSessionControlFilter.setCacheManager(getEhCacheManager());
        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；
        kickoutSessionControlFilter.setKickoutAfter(false);
        kickoutSessionControlFilter.setKickoutUrl("/login?kickout=1");
        return kickoutSessionControlFilter;
    }



}
