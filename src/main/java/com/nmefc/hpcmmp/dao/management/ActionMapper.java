package com.nmefc.hpcmmp.dao.management;

import com.nmefc.hpcmmp.dao.BaseMapper;
import com.nmefc.hpcmmp.entity.management.Action;
import com.nmefc.hpcmmp.entity.management.ActionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActionMapper extends BaseMapper<Action, ActionExample, Integer> {
}