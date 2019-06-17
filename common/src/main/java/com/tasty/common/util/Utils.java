package com.tasty.common.util;

import java.util.Map;
import java.util.UUID;

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

    /**
     * 获取32位UUID, 去掉 -
     * @return
     */
    public static String getUUID32(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}
