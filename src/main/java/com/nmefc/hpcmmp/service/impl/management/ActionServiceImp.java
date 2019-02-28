package com.nmefc.hpcmmp.service.impl.management;

import com.nmefc.hpcmmp.common.enumPackage.Regex;
import com.nmefc.hpcmmp.common.enumPackage.ResponseMsg;
import com.nmefc.hpcmmp.entity.management.Action;
import com.nmefc.hpcmmp.entity.management.ActionExample;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.BaseService;
import com.nmefc.hpcmmp.service.impl.BaseServiceImp;
import com.nmefc.hpcmmp.service.management.ActionService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: QuYuan
 * @Description:
 * @Date: Created in 18:38 2019/2/28
 * @Modified By:
 */
@Service("actionService")
public class ActionServiceImp extends BaseServiceImp<Action,ActionExample,Integer> implements ActionService{
    @Override
    public String check(Action action, String response) throws ServiceException {
        //          1.检测必须项
        if(action == null || action.getName() ==null || action.getName().length() == 0 ||action.getUrl() == null ||action.getUrl().length() == 0 || action.getTypeEnum() ==null || action.getMenuIcon() == null ||action.getMenuIcon().length() == 0||action.getIconWidth() == null||action.getIconHeight() == null||action.getMethodTypeEnum()==null) {
            return ResponseMsg.PARAMETERS_MISSING.getValue();
        }


        //        2.检测所填各项是否合规，并补齐默认值
//        检查是否超出长度及违规字符
        List<String> temp1 = new LinkedList<>();
        temp1.add(action.getName());
        if(!Regex.NAME.getDetectResult(temp1)){return ResponseMsg.PAREMETERE_ERROR.getValue();}
        return response;
    }
}
