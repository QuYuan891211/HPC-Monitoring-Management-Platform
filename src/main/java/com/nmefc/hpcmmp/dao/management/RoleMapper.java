package com.nmefc.hpcmmp.dao.management;

import com.nmefc.hpcmmp.dao.BaseMapper;
import com.nmefc.hpcmmp.entity.management.Role;
import com.nmefc.hpcmmp.entity.management.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper extends BaseMapper<Role, RoleExample, Integer> {
}