package com.tasty.common.util;

import java.io.File;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/7/7
 */
public class FileUtil {

    public static void createFile(String filePath){
        filePath = filePath.substring(0, filePath.lastIndexOf(File.separator) + 1);
        File file = new File(filePath);
        if (!file.exists()){
            file.mkdirs();
        }
    }
}
