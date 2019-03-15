package com.nmefc.hpcmmp.config;

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
        //LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        //限制同一帐号同时在线的个数
        //filtersMap.put("kickout", kickoutSessionControlFilter());
        //shiroFilterFactoryBean.setFilters(filtersMap);
        // 5. 配置访问权限 必须是LinkedHashMap，因为它必须保证有序
        // 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 一定要注意顺序,否则就不好使了
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //6.配置不登录可以访问的资源
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        //logout是shiro提供的过滤器
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/**", "authc");
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
        //maxAge=-1表示浏览器关闭时失效此Cookie
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

        return sessionManager;

    }



}
