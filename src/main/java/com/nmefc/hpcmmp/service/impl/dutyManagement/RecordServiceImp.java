package com.nmefc.hpcmmp.service.impl.dutyManagement;

import com.nmefc.hpcmmp.common.enumPackage.Regex;
import com.nmefc.hpcmmp.common.enumPackage.ResponseMsg;
import com.nmefc.hpcmmp.common.transPara.ModUserAssociation;
import com.nmefc.hpcmmp.dao.dutyManagement.RecordMapper;
import com.nmefc.hpcmmp.entity.dutymanagement.Record;
import com.nmefc.hpcmmp.entity.dutymanagement.RecordExample;
import com.nmefc.hpcmmp.entity.management.User;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.dutyManagement.RecordService;
import com.nmefc.hpcmmp.service.impl.BaseServiceImp;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
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
 * @Date: Created in 12:27 2019/5/25
 * @Modified By:
 */
@Service("recordService")
public class RecordServiceImp extends BaseServiceImp<Record,RecordExample,Integer> implements RecordService{
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public String check(Record record, String response) throws ServiceException {
        //          1.检测必须项
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null||user.getName() == null ||user.getName().length()<1) {
            return ResponseMsg.PARAMETERS_MISSING.getValue();
        }
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 3600,rollbackFor = Exception.class)
    public int insertSelective(Record record) throws ServiceException {
        int row = 0;
        try{
            row = recordMapper.insertSelective(record);
        }catch (Exception e){
            throw  new ServiceException("Insert Exception in Service :" + e.getMessage());
        }
        return row;
    }

    /**
     *@description:检测ID是否存在
     *@date:2019.05.30
     *@author:Li Fei
     * @param:
     * @return:
     */

    @Override
    public String checkIDIsExist(Record record, String response) {
        //          1.检测记录ID存在
        try{
            Record test=selectByPrimaryKey(record.getId());
            if(test==null)
                return ResponseMsg.RECORD_ID_MISSING.getValue();
        }
        catch (Exception e) {
            return ResponseMsg.RECORD_ID_MISSING.getValue();
        }

        return response;
    }

    /**
     *@description:根据指定record的id找到修改过记录的人。根据record_id从modified_user_association表选择所有关联的user_id
     *@date:2019.06.03
     *@author:Li Fei
     * @param:recordID
     * @return:
     */
    @Override
    public List<User> selectModifiedUser(Integer id) {

        List<User> list =recordMapper.selectModifiedUser(id);
        return list;
    }

    /**
     *@description:通过输入record和user的关系id，查找是否存在该关系
     *@date:2019.06.03
     *@author:Li Fei
     * @param:
     * @return:
     */
    @Override
    public boolean checkAssociationExist(Integer rid, Integer uid) {
        ModUserAssociation mua=new ModUserAssociation();
        mua.setrecordID(rid);
        mua.setuserID(uid);
        List<ModUserAssociation> list = recordMapper.checkAssociationExist(mua);
        if(list!=null) return false;
            else return true;
    }

    /**
     *@description:插入record和user的关系
     *@date:2019.06.03
     *@author:Li Fei
     * @param:
     * @return:
     */
    @Override
    public int insertAssociation(Integer rid, Integer uid) {
        ModUserAssociation mua=new ModUserAssociation();
        mua.setrecordID(rid);
        mua.setuserID(uid);
        int row=recordMapper.insertAssociation(mua);
        return row;
    }

    /**
     *@description:在插入日志后，选择id号最大的id，即选择当前日志id
     *@date:2019.06.04
     *@author:Li Fei
     * @param:
     * @return:
     */
    @Override
    public int checkCurrentID() {
        return recordMapper.checkCurrentID();
    }
}
