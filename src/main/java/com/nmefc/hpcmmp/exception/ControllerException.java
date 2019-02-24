package com.nmefc.hpcmmp.exception;

/**
 * @Author: QuYuan
 * @Description: 控制器层异常处理
 * @Date: Created in 22:50 2019/2/24
 * @Modified By:
 */
public class ControllerException extends SysException {
    public ControllerException() {
        super("控制器层出现异常");
    }

    public ControllerException(String message) {
        super(message);
    }
}
