package com.tasty.common.util;

import java.util.Map;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/15
 */
public class Utils {

    public static boolean isEmpty(String str){
        return !(str != null && str.length() > 0 && !"null".equalsIgnoreCase(str));
    }

    public static boolean isEmpty(Map map){
        return !(map != null && map.size() > 0);
    }
}
