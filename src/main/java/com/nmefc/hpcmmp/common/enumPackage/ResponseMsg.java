package com.nmefc.hpcmmp.common.enumPackage;

/**
 * @Author: QuYuan
 * @Description: 用于向前台提示
 * @Date: Created in 21:33 2019/2/23
 * @Modified By:
 */
public enum ResponseMsg {
    EXCEPTION("出现异常"),
    REQUEST_ERROR("请求错误"),
    SUCCESS("成功"),
    PARAMETERS_MISSING("缺少参数"),
    PAREMETERE_ERROR("参数输入违规"),
    PAREMETERE_DUPLICATION("参数重复"),
    LOGIN_ERROR("用户未注册"),
    PASSWORD_ERROR("密码错误"),
    RECORD_ID_MISSING("日志ID不存在"),
    USER_LOGIN_ERROR("用户登录状态异常");

    private String value;
    public String getValue(){
        return value;
    }
    ResponseMsg(String value) {
        this.value = value;
    }
}
