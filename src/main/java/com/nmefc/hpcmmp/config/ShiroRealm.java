package com.nmefc.hpcmmp.config;

import com.nmefc.hpcmmp.dao.management.ActionMapper;
import com.nmefc.hpcmmp.dao.management.RoleMapper;
import com.nmefc.hpcmmp.dao.management.UserMapper;
import com.nmefc.hpcmmp.entity.management.Action;
import com.nmefc.hpcmmp.entity.management.Role;
import com.nmefc.hpcmmp.entity.management.User;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.management.ActionService;
import com.nmefc.hpcmmp.service.management.RoleService;
import com.nmefc.hpcmmp.service.management.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author: QuYuan
 * @Description:  在认证、授权内部实现机制中都有提到，最终处理都将交给Real进行处理。因为在Shiro中，最终是通过Realm来获取应用程序中的用户、角色及权限信息的。通常情况下，在Realm中会直接从我们的数据源中获取Shiro需要的验证信息。可以说，Realm是专用于安全框架的DAO.
 * @Date: Created in 15:57 2019/3/9
 * @Modified By:
 */
public class ShiroRealm extends AuthorizingRealm{
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    /**
     * @description: 进行用户身份认证
     * @author: QuYuan
     * @date: 16:01 2019/3/9
     * @param: [principalCollection]
     * @return: org.apache.shiro.authz.AuthorizationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String account = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        List<User> userList = new ArrayList<>();
        try {
            userList = userService.accountDetected(user);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        if(userList.size() == 0){
            throw new UnknownAccountException("该用户名未注册");
        }
        if (!password.equals(userList.get(0).getPassword())){
            throw new IncorrectCredentialsException("密码错误");
        }

        //配置自定义权限登录器，参考博客
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),getName());
        return info;
    }

/**
 * @description: * 授权用户权限
 * 授权的方法是在碰到<shiro:hasPermission name=''></shiro:hasPermission>标签的时候调用的
 * 它会去检测shiro框架中的权限(这里的permissions)是否包含有该标签的name值,如果有,里面的内容显示
 * 如果没有,里面的内容不予显示(这就完成了对于权限的认证.)
 *
 * shiro的权限授权是通过继承AuthorizingRealm抽象类，重载doGetAuthorizationInfo();
 * 当访问到页面的时候，链接配置了相应的权限或者shiro标签才会执行此方法否则不会执行
 * 所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回null即可。
 *
 * 在这个方法中主要是使用类：SimpleAuthorizationInfo 进行角色的添加和权限的添加。
 * authorizationInfo.addRole(role.getRole()); authorizationInfo.addStringPermission(p.getPermission());
 *
 * 当然也可以添加set集合：roles是从数据库查询的当前用户的角色，stringPermissions是从数据库查询的当前用户对应的权限
 * authorizationInfo.setRoles(roles); authorizationInfo.setStringPermissions(stringPermissions);
 *
 * 就是说如果在shiro配置文件中添加了filterChainDefinitionMap.put("/add", "perms[权限添加]");
 * 就说明访问/add这个链接必须要有“权限添加”这个权限才可以访问
 *
 * 如果在shiro配置文件中添加了filterChainDefinitionMap.put("/add", "roles[100002]，perms[权限添加]");
 * 就说明访问/add这个链接必须要有 "权限添加" 这个权限和具有 "100002" 这个角色才可以访问

 * @author: QuYuan
 * @date: 16:50 2019/3/9
 * @param: [principalCollection]
 * @return: org.apache.shiro.authz.AuthorizationInfo
 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Role> roleList =  userService.selectUserRoleByUserID(user.getId()).getRoleList();
        SimpleAuthorizationInfo authorizationInfo =  new SimpleAuthorizationInfo();

        for(Role role:roleList){
            authorizationInfo.addRole(role.getName());
            List<Action> actionList;
            actionList = roleService.selectRelateActionByRole(role);
            for(Action action:actionList){
                authorizationInfo.addStringPermission(action.getName());
            }
        }
        return authorizationInfo;
    }


}
