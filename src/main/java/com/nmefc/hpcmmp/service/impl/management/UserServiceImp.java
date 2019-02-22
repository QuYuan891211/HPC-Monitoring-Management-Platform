package com.nmefc.hpcmmp.service.impl.management;

import com.nmefc.hpcmmp.service.impl.BaseServiceImp;
import com.nmefc.hpcmmp.service.management.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: QuYuan
 * @Description:
 * @Date: Created in 9:55 2019/2/22
 * @Modified By:
 */
@Service("userService")
public class UserServiceImp extends BaseServiceImp<User> implements UserService {
    @Autowired
    private UserMapper userMapper;
}
