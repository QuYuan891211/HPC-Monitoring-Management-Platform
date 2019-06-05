package com.nmefc.hpcmmp.dao.dutyManagement;

import com.nmefc.hpcmmp.common.transPara.ModUserAssociation;
import com.nmefc.hpcmmp.dao.BaseMapper;
import com.nmefc.hpcmmp.entity.dutymanagement.Record;
import com.nmefc.hpcmmp.entity.dutymanagement.RecordExample;
import java.util.List;

import com.nmefc.hpcmmp.entity.management.User;
import org.apache.ibatis.annotations.Param;

public interface RecordMapper extends BaseMapper<Record,RecordExample,Integer> {

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
    List<ModUserAssociation> checkAssociationExist(ModUserAssociation mua);

    /**
     *@description:插入record和user的关系
     *@date:2019.06.03
     *@author:Li Fei
     * @param:
     * @return:
     */
    int insertAssociation(ModUserAssociation mua);

    /**
     *@description:在插入日志后，选择id号最大的id，即选择当前日志id
     *@date:2019.06.04
     *@author:Li Fei
     * @param:
     * @return:
     */
    int checkCurrentID();
}