package com.nmefc.hpcmmp.service.impl.dutyManagement;

import com.nmefc.hpcmmp.common.enumPackage.Regex;
import com.nmefc.hpcmmp.common.enumPackage.ResponseMsg;
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
}
