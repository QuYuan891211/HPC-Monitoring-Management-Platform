package com.nmefc.hpcmmp.service.dutyManagement;

import com.nmefc.hpcmmp.common.transPara.SearchIncident;
import com.nmefc.hpcmmp.entity.dutymanagement.IncidentBill;
import com.nmefc.hpcmmp.entity.dutymanagement.IncidentBillExample;
import com.nmefc.hpcmmp.exception.ServiceException;
import com.nmefc.hpcmmp.service.BaseService;

public interface IncidentBillService extends BaseService<IncidentBill, IncidentBillExample,Integer> {

    /**
     *@description:用于updateIncidentBill的check
     *@date:2019/8/19
     *@author:Li Fei
     * @param:
     * @return:
     */
    public String updateCheck(IncidentBill record, String response)throws ServiceException;

    /**
     *@description:用于deleteIncidentBill的check
     *@date:2019/8/19
     *@author:Li Fei
     * @param:
     * @return:
     */
    public String deleteCheck(IncidentBill record, String response)throws ServiceException;

    /**
     *@description:用于searchIncidentBill的check
     *@date:2019/8/19
     *@author:Li Fei
     * @param:
     * @return:
     */
    public String searchCheck(SearchIncident record, String response)throws ServiceException;
}
