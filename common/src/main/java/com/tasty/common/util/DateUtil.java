package com.tasty.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/17
 */
public class DateUtil {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String PATTERN_DATE = "yyyyMM";

    public static Date getDate(){
        return new Date();
    }

    public static String getNow(){
        return getNow(DEFAULT_PATTERN);
    }

    public static String getNow(String pattern){
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(getDate());
    }
}
