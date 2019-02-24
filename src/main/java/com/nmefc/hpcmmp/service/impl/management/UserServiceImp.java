package com.nmefc.hpcmmp.service.impl.management;

import com.nmefc.hpcmmp.dao.management.UserMapper;
import com.nmefc.hpcmmp.entity.management.User;
import com.nmefc.hpcmmp.entity.management.UserExample;
import com.nmefc.hpcmmp.service.BaseService;
import com.nmefc.hpcmmp.service.impl.BaseServiceImp;
import com.nmefc.hpcmmp.service.management.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: QuYuan
 * @Description:
 * @Date: Created in 9:55 2019/2/22
 * @Modified By:
 */
@Service("userService")
public class UserServiceImp extends BaseServiceImp<User,UserExample,Integer> implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public List<User> accountDetected(User user) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(user.getAccount());
        List<User> list = userMapper.selectByExample(example);
        return list;
    }
}
