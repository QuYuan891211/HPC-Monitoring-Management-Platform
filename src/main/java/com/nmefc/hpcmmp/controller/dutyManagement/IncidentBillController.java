package com.nmefc.hpcmmp.controller.dutyManagement;

import com.nmefc.hpcmmp.common.enumPackage.ResponseMsg;
import com.nmefc.hpcmmp.common.utils.DateTimeUtils;
import com.nmefc.hpcmmp.entity.dutymanagement.IncidentBill;
import com.nmefc.hpcmmp.entity.management.User;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.dutyManagement.IncidentBillService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;


@Controller
@RequestMapping("/incident")
public class IncidentBillController {

    @Autowired
    private IncidentBillService incidentBillService;
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
        incidentBill.setStartTime(DateTimeUtils.date2timestamp(new Date()));
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
}
