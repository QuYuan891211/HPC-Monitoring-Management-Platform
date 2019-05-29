package com.nmefc.hpcmmp.dao.dutyManagement;

import com.nmefc.hpcmmp.dao.BaseMapper;
import com.nmefc.hpcmmp.entity.dutymanagement.Record;
import com.nmefc.hpcmmp.entity.dutymanagement.RecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RecordMapper extends BaseMapper<Record,RecordExample,Integer> {

}