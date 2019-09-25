package com.nmefc.hpcmmp.controller.dutyManagement;

import com.nmefc.hpcmmp.common.enumPackage.ResponseMsg;
import com.nmefc.hpcmmp.common.transPara.IncidentBillAndKey;
import com.nmefc.hpcmmp.common.transPara.SearchIncident;
import com.nmefc.hpcmmp.common.utils.DateTimeUtils;
import com.nmefc.hpcmmp.entity.dutymanagement.IncidentBill;
import com.nmefc.hpcmmp.entity.dutymanagement.IncidentBillExample;
import com.nmefc.hpcmmp.entity.management.User;
import com.nmefc.hpcmmp.exception.ControllerException;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.dutyManagement.IncidentBillService;
import com.nmefc.hpcmmp.service.management.UserService;
import com.nmefc.hpcmmp.workflowEngine.entity.WorkflowBean;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nmefc.hpcmmp.workflowEngine.WorkflowService;

import java.util.*;


@Controller
@RequestMapping("/incident")
public class IncidentBillController {

    @Autowired
    private IncidentBillService incidentBillService;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private UserService userService;
    @ResponseBody
    @PostMapping(value = "/insertIncidentBill")
    public String insertIncidentBill(@RequestBody IncidentBill incidentBill){
        String message = ResponseMsg.SUCCESS.getValue();
        try {
            //1.在Service层进行数据校验
            message = incidentBillService.check(incidentBill,message);
        } catch (ServiceException e) {
            e.printStackTrace();
            return ResponseMsg.EXCEPTION.getValue();
        }
        //2.设置基本参数
        incidentBill.setIsDelete(false);
//        incidentBill.setStartTime(DateTimeUtils.date2timestamp(new Date()));
        incidentBill.setGmtCreate(DateTimeUtils.date2timestamp(new Date()));
        incidentBill.setGmtModified(DateTimeUtils.date2timestamp(new Date()));
        //3.找到事件单发起者（当前用户）的ID
        User user = (User) SecurityUtils.getSubject(). getPrincipal();
        if(user == null){return ResponseMsg.USER_LOGIN_ERROR.getValue();}
        incidentBill.setInitiator(user.getId());
        //4.交给Service层完成存入(事件关联功能暂时不做)
        try {
            incidentBillService.insertSelective(incidentBill);
            return message;
        } catch (ServiceException e) {
            e.printStackTrace();
            return ResponseMsg.EXCEPTION.getValue();
        }

    }

    /**
     *@description:更新（仅修改）事件单,本人和高等级角色可用
     *@date:2019/8/19
     *@author:Li Fei
     * @param:要带有id
     * @return:
     */
    @ResponseBody
    @PostMapping(value = "/updateIncidentBill")
    public String updateIncidentBill(@RequestBody IncidentBill incidentBill){
        String message = ResponseMsg.SUCCESS.getValue();
        try {
            //1.在Service层进行数据校验（无权限校验）
            message = incidentBillService.updateCheck(incidentBill,message);
        } catch (ServiceException e) {
            e.printStackTrace();
            return ResponseMsg.EXCEPTION.getValue();
        }
        //2.权限检测
        //2.1查看当前用户
        User user = (User) SecurityUtils.getSubject(). getPrincipal();
        if(user == null){return ResponseMsg.USER_LOGIN_ERROR.getValue();}
        //2.2查看当前事件
        try{
            IncidentBill thisIncidentBill=incidentBillService.selectByPrimaryKey(incidentBill.getId());
            //2.3判断：若当前user是发起人,或属于更高级角色
            if(user.getId()==thisIncidentBill.getInitiator()|user.getRoleList().get(0).getId()>userService.selectUserRoleByUserID(thisIncidentBill.getInitiator()).get(0).getId()){
            //继续
            }else{
                //否则报无权限
                return ResponseMsg.PERMISSION_DENIED.getValue();
            }
        }catch (ServiceException e){
            e.printStackTrace();
            return ResponseMsg.EXCEPTION.getValue();
        }


        //3.设置基本参数
        incidentBill.setGmtModified(DateTimeUtils.date2timestamp(new Date()));
//        //3.找到事件单发起者（当前用户）的ID

//        incidentBill.setInitiator(user.getId());
        //4.交给Service层完成存入
        try {
            incidentBillService.updateByPrimaryKeySelective(incidentBill);
            return message;
        } catch (ServiceException e) {
            e.printStackTrace();
            return ResponseMsg.EXCEPTION.getValue();
        }

    }

    /**
     *@description:办理事件单（修改并新建或推进工作流task）
     *@date:2019/8/19
     *@author:Li Fei
     * @param:要带有id
     * @return:
     */
    @ResponseBody
    @PostMapping(value = "/checkIncidentBill")
    public String checkIncidentBill(@RequestBody IncidentBillAndKey incidentBillAndKey){
//https://www.cnblogs.com/cxyj/p/3877996.html
        //https://blog.csdn.net/xwnxwn/article/details/52638135


        String message = ResponseMsg.SUCCESS.getValue();
        try {
            //1.在Service层进行数据校验（无权限校验）
            message = incidentBillService.updateCheck(incidentBillAndKey.incidentBill,message);
        } catch (ServiceException e) {
            e.printStackTrace();
            return ResponseMsg.EXCEPTION.getValue();
        }
        if(message!=ResponseMsg.SUCCESS.getValue())
        {
            return message;
        }
        //2.设置基本参数
        incidentBillAndKey.incidentBill.setGmtModified(DateTimeUtils.date2timestamp(new Date()));
        //3.找到事件单发起者（当前用户）
        User user = (User) SecurityUtils.getSubject(). getPrincipal();
        if(user == null){return ResponseMsg.USER_LOGIN_ERROR.getValue();}
//        incidentBill.setInitiator(user.getId());
        //4.交给Service层完成存入（移至工作流后处理）
//        try {
//            incidentBillService.updateByPrimaryKeySelective(incidentBillAndKey.incidentBill);
////            return message;
//        } catch (ServiceException e) {
//            e.printStackTrace();
//            return ResponseMsg.EXCEPTION.getValue();
//        }
        //5.分类操作：向关联的工作流实体发送新建实体（start）或下一步指令（complete task）
        //5.1未创建工作流情况：判断是否已有关联工作流（事件是否已经start），若没有，则新建工作流实体并关联;若有则继续
        //5.1.1查找该ID对应的事件的InstID
        IncidentBill thisIncidentBill;
        try {
            thisIncidentBill=incidentBillService.selectByPrimaryKey(incidentBillAndKey.incidentBill.getId());
        } catch (ServiceException e) {
            e.printStackTrace();
            return ResponseMsg.EXCEPTION.getValue();
        }
        int instID=thisIncidentBill.getInstId();
        //5.1.2判断工作流是否启动，未启动则新建并关联，结束
        if(instID==0){
            //判断权限,仅本人可启动工作流
            if(user.getId()!=thisIncidentBill.getInitiator())return ResponseMsg.PERMISSION_DENIED.getValue();
            WorkflowBean workflowBean =new WorkflowBean();
            workflowBean.setId(incidentBillAndKey.incidentBill.getId().longValue());
            //key不存在时报错退出
            if(incidentBillAndKey.getKey()==null)return ResponseMsg.PARAMETERS_MISSING.getValue();
            workflowBean.setKey(incidentBillAndKey.getKey());
            try{
                //5.1.2.1启动工作流实体,并返回InstID号码
                instID=Integer.parseInt(workflowService.startProcess(workflowBean,user).getProcessInstanceId());
                //查询关联工作流的task ID,并指定下一个执行人角色id
                int taskID=workflowService.findTaskIDByProcInstID(instID);
                workflowService.setAssigneeTask(taskID,incidentBillAndKey.getnextRoleID());
                //5.1.2.2写入关联的实体id
                incidentBillAndKey.incidentBill.setInstId(instID);
                //5.1.2.3改变事件状态为已提交
                incidentBillAndKey.incidentBill.setBillState(2);
                incidentBillAndKey.incidentBill.setStartTime(DateTimeUtils.date2timestamp(new Date()));
                //交给Service层完成存入
                incidentBillService.updateByPrimaryKeySelective(incidentBillAndKey.incidentBill);
                return message;
            }catch(Exception e) {
                e.printStackTrace();
                return ResponseMsg.START_PROCESS_ERROR.getValue();
            }
        }
        //5.2已创建工作流，且工作流未结束情况：则向工作流发出下一步指令（complete task）（通过inst_id查询该流程是否已经进入history）
        if(workflowService.instIsFinished(instID)==0){
            try{
                //5.2.1查询关联工作流的task ID
                int taskID=workflowService.findTaskIDByProcInstID(instID);
                //权限判断，指定角色或以上可操作
                if(user.getRoleList().get(0).getId()<Integer.parseInt(workflowService.findTaskByID(taskID).getAssignee()))return ResponseMsg.PERMISSION_DENIED.getValue();
                //5.2.2完成一步工作
                Map<String,Object> map = new HashMap<>();
                //5.2.2.1visitor判断
                int visitor=0;//默认继续
                if(incidentBillAndKey.getifEnd()){
                    visitor=1;//办结
                }
                map.put("visitor",visitor);
                workflowService.completeTask(taskID,map);
                //5.2.3判断是否已完成，调整事件状态
                if(workflowService.instIsFinished(instID)==1){
                    incidentBillAndKey.incidentBill.setBillState(3);
                    incidentBillAndKey.incidentBill.setFinishTime(DateTimeUtils.date2timestamp(new Date()));
                }else{
                    taskID=workflowService.findTaskIDByProcInstID(instID);
                    workflowService.setAssigneeTask(taskID,incidentBillAndKey.getnextRoleID());
                }
                //交给Service层完成存入
                incidentBillService.updateByPrimaryKeySelective(incidentBillAndKey.incidentBill);
                return message;
            }catch (Exception e) {
                e.printStackTrace();
                return ResponseMsg.COMPLETE_TASK_ERROR.getValue();
            }

        }

        //5.3已创建工作流，且工作流已经结束情况：仅修改
        if(workflowService.instIsFinished(instID)==1){
            //交给Service层完成存入
            try{
                incidentBillService.updateByPrimaryKeySelective(incidentBillAndKey.incidentBill);
            }catch (Exception e) {
                e.printStackTrace();
                return ResponseMsg.EXCEPTION.getValue();
            }

            return message;
        }
        //5.4其它情况报错
        return ResponseMsg.EXCEPTION.getValue();
    }

    /**
     *@description:软删除（撤销）事件单
     *@date:2019/8/19
     *@author:Li Fei
     * @param:要带有id
     * @return:
     */
    @ResponseBody
    @PostMapping(value = "/deleteIncidentBill")
    public String deleteIncidentBill(@RequestBody IncidentBill incidentBill){
        String message = ResponseMsg.SUCCESS.getValue();
        IncidentBill incidentBill1=new IncidentBill();
        try {
            //1.在Service层进行数据校验
            message = incidentBillService.deleteCheck(incidentBill,message);
        } catch (ServiceException e) {
            e.printStackTrace();
            return ResponseMsg.EXCEPTION.getValue();
        }
        //2.设置基本参数,只更删除标记和更改日期，忽略其它不该有的信息
        incidentBill1.setId(incidentBill.getId());
        incidentBill1.setIsDelete(false);
        incidentBill1.setGmtModified(DateTimeUtils.date2timestamp(new Date()));
        //4.交给Service层完成存入(事件关联功能暂时不做)
        try {
            incidentBillService.updateByPrimaryKeySelective(incidentBill1);
            return message;
        } catch (ServiceException e) {
            e.printStackTrace();
            return ResponseMsg.EXCEPTION.getValue();
        }

    }

/**
 *@description:查询事件单
 *@date:2019/8/16
 *@author:Li Fei
 * @param:
 * @return:
 */
    @ResponseBody
    @PostMapping(value = "/searchIncidentBill")
    public List<IncidentBill> searchIncidentBill(@RequestBody SearchIncident searchIncident)throws ControllerException {
        String message = ResponseMsg.SUCCESS.getValue();
        try {
            //1.在Service层进行数据校验，检查起止时间问题
            message = incidentBillService.searchCheck(searchIncident,message);
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ControllerException("Incident Exception:" +message);
        }

        IncidentBillExample incidentBillExample=new IncidentBillExample();
        IncidentBillExample.Criteria criteria=incidentBillExample.createCriteria();
        //2.分类查询
        //2.1全空，返回所有(包括未删除）的记录
        if(searchIncident==null){
//            criteria.andIsDeleteEqualTo(false);
        }
        //2.2有发起人ID,以发起人ID为条件
        if(searchIncident.getInitiator()!=null){
            criteria.andInitiatorEqualTo(searchIncident.getInitiator());
        }
        //2.3有时间，以时间作为条件
        //2.3.1创建时间
        if(searchIncident.getGmtCreateEnd()!=null){
            criteria.andGmtCreateBetween(searchIncident.getGmtCreateStart(),searchIncident.getGmtCreateEnd());
        }
        //2.3.2修改时间
        if(searchIncident.getGmtModifiedStart()!=null){
            criteria.andGmtModifiedBetween(searchIncident.getGmtModifiedStart(),searchIncident.getGmtModifiedEnd());
        }
        //2.3.3事件开始时间
        if(searchIncident.getStartTimeStart()!=null){
            criteria.andStartTimeBetween(searchIncident.getStartTimeStart(),searchIncident.getStartTimeEnd());
        }
        //2.3.4事件结束时间
        if(searchIncident.getFinishTimeStart()!=null){
            criteria.andFinishTimeBetween(searchIncident.getFinishTimeStart(),searchIncident.getFinishTimeEnd());
        }
        //2.4删除标记，根据删除标记查询
        if(searchIncident.getIsDelete()!=null){
            criteria.andIsDeleteEqualTo(searchIncident.getIsDelete());
        }

        List<IncidentBill> list=new ArrayList<IncidentBill>();

        //3传递给业务层
        try{
            list=incidentBillService.selectByExample(incidentBillExample);
        }
        catch(Exception e){
            throw new ControllerException("Incident Exception:" +e.getMessage());
        }
        return list;
        }

    }

