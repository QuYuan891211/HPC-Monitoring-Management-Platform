package com.nmefc.hpcmmp.dao.management;

import com.nmefc.hpcmmp.dao.BaseMapper;
import com.nmefc.hpcmmp.entity.management.User;
import com.nmefc.hpcmmp.entity.management.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User, UserExample, Integer> {
}