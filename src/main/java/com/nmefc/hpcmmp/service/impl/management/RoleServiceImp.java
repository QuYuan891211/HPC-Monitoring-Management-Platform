package com.nmefc.hpcmmp.service.impl.management;

import com.nmefc.hpcmmp.entity.management.Role;
import com.nmefc.hpcmmp.entity.management.RoleExample;
import com.nmefc.hpcmmp.service.BaseService;
import com.nmefc.hpcmmp.service.impl.BaseServiceImp;
import com.nmefc.hpcmmp.service.management.RoleService;
import org.springframework.stereotype.Service;

/**
 * @Author: QuYuan
 * @Description: 角色业务逻辑层实现
 * @Date: Created in 16:06 2019/2/25
 * @Modified By:
 */
@Service("roleService")
public class RoleServiceImp extends BaseServiceImp<Role,RoleExample,Integer>  implements RoleService{
}
