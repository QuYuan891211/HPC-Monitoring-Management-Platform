package com.nmefc.hpcmmp.service.impl.management;

import com.nmefc.hpcmmp.common.enumPackage.Regex;
import com.nmefc.hpcmmp.common.enumPackage.ResponseMsg;
import com.nmefc.hpcmmp.dao.management.UserMapper;
import com.nmefc.hpcmmp.entity.management.Role;
import com.nmefc.hpcmmp.entity.management.User;
import com.nmefc.hpcmmp.entity.management.UserExample;
import com.nmefc.hpcmmp.entity.management.association.UserRoleAssociation;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.impl.BaseServiceImp;
import com.nmefc.hpcmmp.service.management.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
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

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 3600,rollbackFor = Exception.class)
    public int insertSelective(User user) throws ServiceException {
        int row = 0;
        try{
            row = userMapper.insertSelective(user);
            if(user.getRoleList() != null && user.getRoleList().size() > 0){
                //注意此时user是没有ID的(Hibernate有数据自动回填，mybatis没有，需要思考解决策略)，需要从数据库中查出ID
                user.setId(accountDetected(user).get(0).getId());
                saveRelativity(user);
            }
        }catch (Exception e){
            throw  new ServiceException("Insert Exception in Service :" + e.getMessage());
        }

        return row;
    }


    /**
     * @description: 向用户角色关联表中传入数据
     * @author: QuYuan
     * @date: 21:55 2019/2/26
     * @param: [user]
     * @return: int
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 3600,rollbackFor = Exception.class)
    public int saveRelativity(User user) throws ServiceException {
        int row = 0;
        List<Role> roleList = user.getRoleList();
        for(Role role:roleList){
            UserRoleAssociation userRoleAssociation = new UserRoleAssociation(user.getId(),role.getId());
            try {
                    row = userMapper.saveRelativity(userRoleAssociation);
            }catch(Exception e){
                throw  new ServiceException("Save Relativity Exception :" + e.getMessage());
            }
        }
        return row ;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 3600,rollbackFor = Exception.class)
    public int deleteRelativity(Integer id) {
        int row = 0;
        if(id!=null){
            try{
               row = userMapper.deleteRelativityByUserID(id);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return row;

    }

    @Override
    public List<Role> selectUserRoleByUserID(Integer id) {
        User user = new User();
        if (id == null){ return null; }
        List<Role> list = new ArrayList<>();
        try {
            list = userMapper.selectUserRoleByUserID(id);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }

//    @Override
//    public List<User> selectAllUserWithRoleInfo() {
//        List<User> userList = new ArrayList<>();
//        try {
//            userList =  userMapper.selectAllUserWithRoleInfo();
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            return userList;
//        }
//    }


    /**
     * @description: 数据校验
     * @author: QuYuan
     * @date: 12:00 2019/2/24
     * @param: [user, response]
     * @return: java.lang.String
     */
    public String check(User user,String response) throws ServiceException {
        //          1.检测必须项
        if(user == null || user.getName() ==null || user.getName().length() == 0 || user.getPassword()==null ||user.getPassword().length() == 0 || user.getAccount() == null || user.getAccount().length() == 0) {
            return ResponseMsg.PARAMETERS_MISSING.getValue();
        }
//        2.检测所填各项是否合规，并补齐默认值
//        检查是否超出长度及违规字符
        List<String> temp1 = new LinkedList<String>();
        temp1.add(user.getAccount());
        temp1.add(user.getPassword());
        if(!Regex.LETTERS_NUMBERS.getDetectResult(temp1)){return ResponseMsg.PAREMETERE_ERROR.getValue();}
        List<String> temp2 = new LinkedList<String >();
        temp2.add(user.getName());

        //3.姓名是否违规
        if(!Regex.NAME.getDetectResult(temp2)){return ResponseMsg.PAREMETERE_ERROR.getValue(); }
        //4.账号是否重复
        List<User> list = null;
        try {
            list = accountDetected(user);
        } catch (ServiceException e) {
            throw new ServiceException("Check Exception : " + e.getErrorMsg());
        }
        if(list !=null&& list.size() > 0 && list.get(0).getId() != user.getId()){return ResponseMsg.PAREMETERE_DUPLICATION.getValue();}
        return response;
    }

    /**
     * @description: 更新用户
     * @author: QuYuan
     * @date: 0:49 2019/2/27
     * @param: [user]
     * @return: int
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 3600,rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(User user) throws ServiceException {
        int row = 0;
        try{
            row  = userMapper.updateByPrimaryKeySelective(user);
            deleteRelativity(user.getId());
            saveRelativity(user);
        }catch (Exception e){
            throw  new ServiceException("update exception in Service ：" + e.getMessage());

        }
        return row;
    }

}
