package com.nmefc.hpcmmp.exception;

/**
 * @Author: QuYuan
 * @Description: 业务逻辑层异常处理
 * @Date: Created in 22:45 2019/2/24
 * @Modified By:
 */
public class ServiceException extends SysException {
    public ServiceException() {
        super("业务层操作失败");
    }

    public ServiceException(String message) {
        super(message);
    }
}
