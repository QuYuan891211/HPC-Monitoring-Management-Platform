package com.nmefc.hpcmmp.utils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author: QuYuan
 * @Description: 用于时间格式转换工具类
 * @Date: Created in 12:34 2019/2/22
 * @Modified By:
 */
public class DateTimeUtils {
/**
 * @description:  Mysql中的datetime类型对于与Java中的Timestamp类型，其它类型传入mysql时会报错
 *
 *
    date：只有日期，没有时间，2016-09-21；

    time：只有时间，没有日期，23:42:31；

    datetime：日期时间都有，2016-09-21 23:42:31 。

    timestamp：可以在进行Insert或者update的时候自动的为你插入时间，时间格式：2016-09-21 23:42:31。
 * @author: QuYuan
 * @date: 12:47 2019/2/22
 * @param: [date]
 * @return: java.sql.Timestamp
 */
    public static Timestamp date2timestamp(Date date){
        return new Timestamp(date.getTime());
    }
}
