package com.nmefc.hpcmmp.common.enumPackage;

/**
 * @Author: QuYuan
 * @Description: 用于向前台提示
 * @Date: Created in 21:33 2019/2/23
 * @Modified By:
 */
public enum Reminding {
    PARAMETERS_MISSING("缺少参数"),
    PAREMETERE_ERROR("参数输入违规");
    private String value;
    public String getValue(){
        return value;
    }
    Reminding(String value) {
        this.value = value;
    }
}
