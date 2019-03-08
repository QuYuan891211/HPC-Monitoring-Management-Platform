package com.nmefc.hpcmmp.controller.home;

import com.alibaba.druid.util.StringUtils;
import com.nmefc.hpcmmp.common.enumPackage.Regex;
import com.nmefc.hpcmmp.common.enumPackage.ResponseMsg;
import com.nmefc.hpcmmp.entity.management.User;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.management.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.alibaba.druid.util.StringUtils.*;

/**
 * @Author: QuYuan
 * @Description:
 * @Date: Created in 21:32 2019/3/8
 * @Modified By:
 */
@Controller
@RequestMapping(value = "/home")
public class HomeController {

    @Autowired
    private UserService userService;
    @ResponseBody
    public String login(@RequestBody User user) throws ServiceException {
        if(user == null || isEmpty(user.getPassword())|| isEmpty(user.getAccount())){return ResponseMsg.PARAMETERS_MISSING.getValue();}
            User record = userService.accountDetected(user).get(0);
            if (record ==null){return ResponseMsg.LOGIN_ERROR.getValue();}
                if(!user.getPassword().equals(record.getPassword())){return ResponseMsg.PASSWORD_ERROR.getValue();}
        return ResponseMsg.SUCCESS.getValue();
    }
}
