package com.nmefc.hpcmmp.exception;

/**
 * @Author: QuYuan
 * @Description: 全局异常处理
 * @Date: Created in 22:41 2019/2/24
 * @Modified By:
 */
public abstract class SysException extends Exception {
    private String errorMsg;

    public SysException() {
    }

    public SysException(String message) {
        super(message);
        errorMsg = message;
    }

    public SysException(String message, Throwable cause) {
        super(message, cause);
    }

    public SysException(Throwable cause) {
        super(cause);
    }

    public SysException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
