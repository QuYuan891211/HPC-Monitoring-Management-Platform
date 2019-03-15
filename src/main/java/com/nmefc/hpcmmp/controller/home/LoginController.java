package com.nmefc.hpcmmp.controller.home;

import com.nmefc.hpcmmp.config.ShiroSessionListener;
import com.nmefc.hpcmmp.entity.management.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * @Author: QuYuan
 * @Description: 登录访问控制器
 * @Date: Created in 17:19 2019/3/9
 * @Modified By:
 */
@Controller
public class LoginController {

    @Autowired
    private ShiroSessionListener  getShiroSessionListener;

    /**
     * @description: 用户访问根目录
     * @author: QuYuan
     * @date: 17:29 2019/3/9
     * @param: [model]
     * @return: java.lang.String
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String root(Model model){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        if(user==null){
            return "redirect:/login";
        }else {
            return "redirect:/index";
        }
    }
    /**
     * @description: 用户访问登录页面
     * @author: QuYuan
     * @date: 17:29 2019/3/9
     * @param: [model]
     * @return: java.lang.String
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    @ResponseBody
    public String login(Model model) {
        Subject subject = SecurityUtils.getSubject();
        User user=(User) subject.getPrincipal();
        if (user == null){
            return "login";
        }else{
            return "redirect:index";
        }

    }
    /**
     * @description: 用户登录
     * @author: QuYuan
     * @date: 18:16 2019/3/9
     * @param: [request, account, username, model, session]
     * @return: java.lang.String
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String loginUser(HttpServletRequest request, String account, String password, Model model, HttpSession session){
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(account,password);
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(usernamePasswordToken);

            User user = (User)subject.getPrincipal();
            session.setAttribute("user",user);
            model.addAttribute("user",user);
            return "index";
        }catch (Exception e){
            String exception = (String) request.getAttribute("shiroLoginFailure");
            model.addAttribute("msg",e.getMessage());
            return "login";
        }
    }

    /**
     * @description: 用户登录主页
     * @author: QuYuan
     * @date: 23:59 2019/3/9
     * @param: [session, model]
     * @return: java.lang.String
     */
    @RequestMapping("/index")
    @ResponseBody
    public String index(HttpSession session, Model model) {
        Subject subject = SecurityUtils.getSubject();
        User user=(User) subject.getPrincipal();
        if (user == null){
            return "login";
        }else{
            model.addAttribute("user",user);
            model.addAttribute("count",getShiroSessionListener.getSessionCount());
            return "index";
        }
    }

    /**
     * @description: 用户登出
     * @author: QuYuan
     * @date: 0:01 2019/3/10
     * @param: [session, model]
     * @return: java.lang.String
     */
    @RequestMapping("/logout")
    @ResponseBody
    public String logout(HttpSession session, Model model) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        model.addAttribute("msg","安全退出！");
        return "login";
    }
    /**
     * @description: 跳转到无权页面
     * @author: QuYuan
     * @date: 0:02 2019/3/10
     * @param: [session, model]
     * @return: java.lang.String
     */
    @RequestMapping("/unauthorized")
    @ResponseBody
    public String unauthorized(HttpSession session, Model model) {
        return "unauthorized";
    }

}
