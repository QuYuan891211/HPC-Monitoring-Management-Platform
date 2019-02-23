package com.nmefc.hpcmmp.common.enumPackage;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: QuYuan
 * @Description: 所有需要的正则表达式的枚举
 * @Date: Created in 17:52 2019/2/23
 * @Modified By:
 */
public enum Regex {
    //数字与字母
    LETTERS_NUMBERS("^[a-zA-Z0-9]{1,20}$"),
    //中文
    NAME("^[\\u4E00-\\u9FA5]{2,6}$");

    private String value;
    Regex(String value) {
      this.value = value;
    }

    public boolean getDetectResult(List<String> list){
        Pattern pattern = Pattern.compile(value);
        boolean result = true;
        for(String str:list){
            Matcher matcher = pattern.matcher(str);
            if(!matcher.find()){result = false;}
        }
        return result;
    }
}
