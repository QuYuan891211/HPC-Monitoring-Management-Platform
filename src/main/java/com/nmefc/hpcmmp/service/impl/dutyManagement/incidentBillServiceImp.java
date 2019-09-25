package com.nmefc.hpcmmp.service.impl.dutyManagement;

import com.nmefc.hpcmmp.common.enumPackage.ResponseMsg;
import com.nmefc.hpcmmp.common.transPara.SearchIncident;
import com.nmefc.hpcmmp.dao.dutyManagement.IncidentCategoryMapper;
import com.nmefc.hpcmmp.dao.dutyManagement.IncidentStateMapper;
import com.nmefc.hpcmmp.dao.dutyManagement.IncidentBillMapper;
import com.nmefc.hpcmmp.entity.dutymanagement.IncidentBill;
import com.nmefc.hpcmmp.entity.dutymanagement.IncidentBillExample;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.dutyManagement.IncidentBillService;
import com.nmefc.hpcmmp.service.impl.BaseServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("incidentService")
public class incidentBillServiceImp extends BaseServiceImp<IncidentBill, IncidentBillExample,Integer> implements IncidentBillService{
    @Autowired
    private IncidentBillMapper incicdentBillMapper;
    @Autowired
    private IncidentCategoryMapper incidentCategoryMapper;
    @Autowired
    private IncidentStateMapper incidentStateMapper;

    @Override
    public String check(IncidentBill record, String response) throws ServiceException {
        if ((record == null) || (record.getCategory() == null) || (record.getAssetId() == null)||record.getCause()==null || record.getBillState() == null){
            return ResponseMsg.PARAMETERS_MISSING.getValue();
        }
        //检查关联项是否存在
        if (record.getCause().isEmpty()){return ResponseMsg.PAREMETERE_ERROR.getValue();}
        if(incidentCategoryMapper.selectByPrimaryKey(record.getCategory())==null){
            return ResponseMsg.ASSICIATED_DATA_UNEXIST.getValue();
        }
        if(incidentStateMapper.selectByPrimaryKey(record.getBillState())==null){
            return ResponseMsg.ASSICIATED_DATA_UNEXIST.getValue();
        }
        return response;
    }
    /**
     *@description:用于updateIncidentBill的check
     *@date:2019/8/19
     *@author:Li Fei
     * @param:
     * @return:
     */
    public String updateCheck(IncidentBill record, String response) throws ServiceException {
        if ((record == null) || (record.getId() == null)){
            return ResponseMsg.PARAMETERS_MISSING.getValue();
        }
        //检查关联项是否存在
        if(record.getCategory()!=null){
            if(incidentCategoryMapper.selectByPrimaryKey(record.getCategory())==null){
                return ResponseMsg.ASSICIATED_DATA_UNEXIST.getValue();
            }
        }
        if(record.getBillState()!=null){
            if(incidentStateMapper.selectByPrimaryKey(record.getBillState())==null){
                return ResponseMsg.ASSICIATED_DATA_UNEXIST.getValue();
            }
        }
        return response;
    }
    /**
     *@description:用于deleteIncidentBill的check
     *@date:2019/8/19
     *@author:Li Fei
     * @param:
     * @return:
     */
    public String deleteCheck(IncidentBill record, String response) throws ServiceException {
        if ((record == null) || (record.getId() == null)){
            return ResponseMsg.PARAMETERS_MISSING.getValue();
        }
        return response;
    }

    /**
     *@description:用于searchIncidentBill的check
     *@date:2019/8/19
     *@author:Li Fei
     * @param:1.全空；2.有时间标记；3.有发起人id；4.有删除标记
     * @return:1.正常；2.查起止是否齐全；3.正常；4.正常
     */
    public String searchCheck(SearchIncident record, String response)throws ServiceException{
        response=ResponseMsg.SUCCESS.getValue();
        //全空不管

        //创建时间,其一有一空时,报错
        if((record.getGmtCreateStart()==null)==(record.getGmtCreateEnd()==null)){
            response= ResponseMsg.PAREMETERE_ERROR.getValue();
            throw new ServiceException(response);
        }
        //修改时间,其一有一空时,报错
        if((record.getGmtModifiedStart()==null)==(record.getGmtModifiedEnd()==null)){
            response= ResponseMsg.PAREMETERE_ERROR.getValue();
            throw new ServiceException(response);
        }
        //事件发生时间,其一有一空时,报错
        if((record.getStartTimeStart()==null)==(record.getStartTimeEnd()==null)){
            response= ResponseMsg.PAREMETERE_ERROR.getValue();
            throw new ServiceException(response);
        }
        //事件完成时间,其一有一空时,报错
        if((record.getFinishTimeStart()==null)==(record.getFinishTimeEnd()==null)){
            response= ResponseMsg.PAREMETERE_ERROR.getValue();
            throw new ServiceException(response);
        }
        //发起人ID，不管
        //删除标记，不管
        return response;
    }
}
