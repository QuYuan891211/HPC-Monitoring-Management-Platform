package com.nmefc.hpcmmp.service.impl.dutyManagement;

import com.nmefc.hpcmmp.common.enumPackage.ResponseMsg;
import com.nmefc.hpcmmp.dao.dutyManagement.IncidentBillMapper;
import com.nmefc.hpcmmp.entity.dutymanagement.IncidentBill;
import com.nmefc.hpcmmp.entity.dutymanagement.IncidentBillExample;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.dutyManagement.IncidentBillService;
import com.nmefc.hpcmmp.service.impl.BaseServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("incidentService")
public class incidentBillServiceImp extends BaseServiceImp<IncidentBill,IncidentBillExample,Integer> implements IncidentBillService{
    @Autowired
    private IncidentBillMapper incicdentBillMapper;

    @Override
    public String check(IncidentBill record, String response) throws ServiceException {
        if ((record == null) || (record.getCategory() == null) || (record.getAssetId() == null)||record.getCause()==null || record.getBillState() == null){
            return ResponseMsg.PARAMETERS_MISSING.getValue();
        }
        if (record.getCause().isEmpty()){return ResponseMsg.PAREMETERE_ERROR.getValue();}
        return response;
    }


}
