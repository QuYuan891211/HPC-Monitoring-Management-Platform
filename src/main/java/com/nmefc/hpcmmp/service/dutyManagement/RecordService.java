package com.nmefc.hpcmmp.service.dutyManagement;

import com.nmefc.hpcmmp.common.transPara.ModUserAssociation;
import com.nmefc.hpcmmp.entity.dutymanagement.Record;
import com.nmefc.hpcmmp.entity.dutymanagement.RecordExample;
import com.nmefc.hpcmmp.service.BaseService;

import com.nmefc.hpcmmp.entity.management.User;

import java.util.List;

/**
 * @Author: QuYuan
 * @Description: 值班日志服务接口
 * @Date: Created in 19:31 2019/5/24
 * @Modified By:
 */
public interface RecordService extends BaseService<Record,RecordExample,Integer> {
    /**
     *@description:检测ID是否存在
     *@date:2019.05.30
     *@author:Li Fei
     * @param:
     * @return:
     */
    String checkIDIsExist(Record record, String response);

    /**
     *@description:根据指定record的id找到修改过记录的人。根据record_id从modified_user_association表选择所有关联的user_id
     *@date:2019.06.03
     *@author:Li Fei
     * @param:recordID
     * @return:
     */
    List<User> selectModifiedUser(Integer id);

    /**
     *@description:通过输入record和user的关系id，查找是否存在该关系
     *@date:2019.06.03
     *@author:Li Fei
     * @param:
     * @return:
     */
    boolean checkAssociationExist(Integer rid,Integer uid);


    /**
     *@description:插入record和user的关系
     *@date:2019.06.03
     *@author:Li Fei
     * @param:
     * @return:
     */
    int insertAssociation(Integer rid,Integer uid);

    /**
     *@description:在插入日志后，选择id号最大的id，即选择当前日志id
     *@date:2019.06.04
     *@author:Li Fei
     * @param:
     * @return:
     */
    int checkCurrentID();
}


