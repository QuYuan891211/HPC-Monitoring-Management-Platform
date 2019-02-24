package com.nmefc.hpcmmp.service.impl.management;

import com.nmefc.hpcmmp.dao.management.UserMapper;
import com.nmefc.hpcmmp.entity.management.User;
import com.nmefc.hpcmmp.entity.management.UserExample;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.BaseService;
import com.nmefc.hpcmmp.service.impl.BaseServiceImp;
import com.nmefc.hpcmmp.service.management.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<User> accountDetected(User user) throws ServiceException {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(user.getAccount());
        List<User> list = new ArrayList<User>();
        try{
           list  = userMapper.selectByExample(example);
        }catch (Exception e){
            throw new ServiceException("Account Detection  Exception in Service: " + e.getMessage() );
        }

        return list;
    }
}
