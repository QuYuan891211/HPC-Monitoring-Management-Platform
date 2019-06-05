package com.nmefc.hpcmmp.controller.dutyManagement;

import com.nmefc.hpcmmp.common.enumPackage.ResponseMsg;
import com.nmefc.hpcmmp.common.transPara.SearchRecord;
import com.nmefc.hpcmmp.common.transPara.SearchRecordResult;
import com.nmefc.hpcmmp.common.utils.DateTimeUtils;
import com.nmefc.hpcmmp.entity.dutymanagement.Record;
import com.nmefc.hpcmmp.entity.dutymanagement.RecordExample;
import com.nmefc.hpcmmp.entity.management.User;
import com.nmefc.hpcmmp.exception.ControllerException;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.dutyManagement.RecordService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            //添加修改用户的关联
            recordService.insertAssociation(recordService.checkCurrentID(),user.getId());
        }catch (Exception e){
            response = ResponseMsg.EXCEPTION.getValue();
            throw new ControllerException("Record Exception :" + e.getMessage());
        }finally {
            return response;
        }
    }

/**
 *@description:修改日志
 *@date:2019.05.30
 *@author:Li Fei
 * @param:id（int），content（string）
 * @return:成功or报错
 */
    @ResponseBody
    @PostMapping(value = "/updateRecord")
//    控制器完成数据校验等工作，其余业务工作交给业务层
    public String updateRecordInfo(@RequestBody Record record) throws ControllerException {
        //参数不得为null，content不得为null，必须有id
        if(record == null){return ResponseMsg.REQUEST_ERROR.getValue();}
        if(record.getId() ==null|| record.getContent()==null){return ResponseMsg.PARAMETERS_MISSING.getValue();}
        String response = ResponseMsg.SUCCESS.getValue();
        //进行数据校验，id必须存在
        response = recordService.checkIDIsExist(record,response);
        if(!response.equals(ResponseMsg.SUCCESS.getValue())){return response;}

//        设置软删除标记
        record.setIsDelete(false);

        //        更新时间设置为新建时间
        record.setGmtModified(DateTimeUtils.date2timestamp(new Date()));
        if(record.getContent().length()<1){record.setContent("正常");}
//
//         传递给业务层
        try{
            //获取操作用户
            User user = (User)SecurityUtils.getSubject().getPrincipal();
            //查看该用户是否有本日志的修改记录
            if(recordService.checkAssociationExist(record.getId(),user.getId())){
                //已有关联记录，不做操作
            }
            else{
                //没有关联记录，增加一条
                recordService.insertAssociation(record.getId(),user.getId());
            }

            recordService.updateByPrimaryKeySelective(record);
        }catch (Exception e){
            response = ResponseMsg.EXCEPTION.getValue();
            throw new ControllerException("Record Exception :" + e.getMessage());
        }finally {
            return response;
        }
    }

/**
 *@description:删除日志记录（软删除）
 *@date:2019.05.31
 *@author:Li Fei
 * @param:id（int）
 * @return:成功or报错
 */
    @ResponseBody
    @PostMapping(value = "/deleteRecord")
//    控制器完成数据校验等工作，其余业务工作交给业务层
    public String deleteRecordInfo(@RequestBody Record record) throws ControllerException {
        //参数不得为null，必须有id
        if(record == null){return ResponseMsg.REQUEST_ERROR.getValue();}
        if(record.getId() ==null){return ResponseMsg.PARAMETERS_MISSING.getValue();}
        String response = ResponseMsg.SUCCESS.getValue();
        //进行数据校验，id必须存在
        response = recordService.checkIDIsExist(record,response);
        if(!response.equals(ResponseMsg.SUCCESS.getValue())){return response;}

//        设置软删除标记
        record.setIsDelete(true);

        //        更新时间设置为新建时间
        record.setGmtModified(DateTimeUtils.date2timestamp(new Date()));

//         传递给业务层
        try{
            recordService.updateByPrimaryKeySelective(record);
        }catch (Exception e){
            response = ResponseMsg.EXCEPTION.getValue();
            throw new ControllerException("Record Exception :" + e.getMessage());
        }finally {
            return response;
        }
    }

/**
 *@description:查询日志
 *@date:2019.05.31
 *@author:Li Fei
 * @param:
 * @return:
 */
    @ResponseBody
    @GetMapping (value = "/searchRecord")
//    控制器完成数据校验等工作，其余业务工作交给业务层
    public List<Record> searchRecordInfo(@RequestBody SearchRecord searchRecord) throws ControllerException {
        //参数不得为null，必须至少有id、uid、gmtCreateStart、gmtModifiedStart、gmtCreateEnd、gmtModifiedEnd、content、isdelete之一
        //if(searchRecord == null){return ResponseMsg.REQUEST_ERROR.getValue();}
        //if(searchRecord.getId() ==null && searchRecord.getGmtCreateStart()==null && searchRecord.getGmtModifiedStart()==null && searchRecord.getGmtCreateEnd()==null && searchRecord.getGmtModifiedEnd()==null &&searchRecord.getContent()==null&& searchRecord.getUid()==null){return ResponseMsg.PARAMETERS_MISSING.getValue();}
        //String response = ResponseMsg.SUCCESS.getValue();
        //gmtCreateStart、gmtModifiedStart、gmtCreateEnd、gmtModifiedEnd必须成对出现，不得单一出现

        //进行数据校验，暂无校验项
        //response = recordService.checkIDIsExist(record,response);
        //if(!response.equals(ResponseMsg.SUCCESS.getValue())){return response;}


        //创建example
        RecordExample recordExample=new RecordExample();
        RecordExample.Criteria criteria=recordExample.createCriteria();
        //传入内容非空时才放入查询
        if(searchRecord.getId() !=null) criteria.andIdEqualTo(searchRecord.getId());
        if(searchRecord.getUid() !=null)criteria.andUidEqualTo(searchRecord.getUid());
        if(searchRecord.getGmtCreateStart() !=null || searchRecord.getGmtCreateEnd()!=null)criteria.andGmtCreateBetween(searchRecord.getGmtCreateStart(),searchRecord.getGmtCreateEnd());
        if(searchRecord.getGmtModifiedStart() !=null ||searchRecord.getGmtModifiedEnd()!=null)criteria.andGmtModifiedBetween(searchRecord.getGmtModifiedStart(),searchRecord.getGmtModifiedEnd());
        if(searchRecord.getContent() !=null) criteria.andContentLike('%'+searchRecord.getContent()+'%');//模糊查询
        if(searchRecord.getIsDelete() !=null) criteria.andIsDeleteEqualTo(searchRecord.getIsDelete());


        List<Record> list = new ArrayList<Record>();


//         传递给业务层

        try{
            list  = recordService.selectByExample(recordExample);
        }catch (Exception e){
            throw new ControllerException("RecordController Exception :" + e.getMessage());
        }

        return list;
    }

    /**
     *@description:查看所选record的关联用户
     *@date:2019.06.03
     *@author:Li Fei
     * @param:record的ID
     * @return:用户ID、姓名、账户列表
     */
    @ResponseBody
    @PostMapping(value = "/modUser")
//    控制器完成数据校验等工作，其余业务工作交给业务层
    public List<User> selectModifiedUser(@RequestBody Record record) throws ControllerException {
        List<User> list=recordService.selectModifiedUser(record.getId());
        return list;
    }

}
