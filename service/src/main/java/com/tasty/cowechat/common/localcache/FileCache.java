package com.tasty.cowechat.common.localcache;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/19
 */
public class FileCache {
    /**
     *
     */
    private static final Map<String, String> uploadFilePath = new HashMap<>();

    public synchronized static String getFilePath(String key){
        String filePath = uploadFilePath.get(key);
        uploadFilePath.remove(key);
        return filePath;
    }

    public static void setFilePath(String key, String filePath){
        uploadFilePath.put(key, filePath);
    }
}
