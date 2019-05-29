package com.nmefc.hpcmmp.controller.dutyManagement;

import com.nmefc.hpcmmp.common.enumPackage.ResponseMsg;
import com.nmefc.hpcmmp.common.utils.DateTimeUtils;
import com.nmefc.hpcmmp.entity.dutymanagement.Record;
import com.nmefc.hpcmmp.entity.management.User;
import com.nmefc.hpcmmp.exception.ControllerException;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.dutyManagement.RecordService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @Author: QuYuan
 * @Description: 值班日志子模块
 * @Date: Created in 19:23 2019/5/24
 * @Modified By:
 */
@Controller
@RequestMapping(value = "/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    /**
     * @description: 新增值班日志
     * @author: QuYuan
     * @date: 12:45 2019/2/22
     * @param: [user]
     * @return: int
     */
    @ResponseBody
    @PostMapping(value = "/insertRecord")
//    控制器完成数据校验等工作，其余业务工作交给业务层
    public String insertRecordInfo(@RequestBody Record record) throws ControllerException {
        if(record == null || record.getId() !=null){return ResponseMsg.REQUEST_ERROR.getValue();}
        String response = ResponseMsg.SUCCESS.getValue();
        //进行数据校验
        try {
            response = recordService.check(record,response);
        } catch (ServiceException e) {
            throw new ControllerException("Insert Exception : " + e.getErrorMsg());
//            e.printStackTrace();
        }

        if(!response.equals(ResponseMsg.SUCCESS.getValue())){return response;}

//        设置软删除标记
        record.setIsDelete(false);
//        记录新建时间
        record.setGmtCreate(DateTimeUtils.date2timestamp(new Date()));
//        更新时间设置为新建时间
        record.setGmtModified(DateTimeUtils.date2timestamp(new Date()));
        if(record.getContent().length()<1){record.setContent("正常");}
//
//         传递给业务层
        try{
            User user = (User)SecurityUtils.getSubject().getPrincipal();
            record.setUid(user.getId());
            recordService.insertSelective(record);
        }catch (Exception e){
            response = ResponseMsg.EXCEPTION.getValue();
            throw new ControllerException("Record Exception :" + e.getMessage());
        }finally {
            return response;
        }
    }


}
