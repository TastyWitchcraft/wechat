package com.tasty.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/18
 */
@Slf4j
public class Base64Util {


    /**
     * 解密
     * @param str
     * @return
     */
    public static String decode(String str){
        return decode(str, "UTF-8");
    }

    /**
     * 解密
     * @param str
     * @param charsetName
     * @return
     */
    public static String decode(String str, String charsetName) {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] bytes = decoder.decode(str);
            return new String(bytes, charsetName);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            return str;
        }
    }

    /**
     * 加密
     * @param str
     * @return
     */
    public static String encode(String str){
        return encode(str, "UTF-8");
    }

    /**
     * 加密
     * @param str
     * @param charsetName
     * @return
     */
    public static String encode(String str, String charsetName){
        try {
            return Base64.getEncoder().encodeToString(str.getBytes(charsetName));
        } catch (Exception e){
            log.error(e.getMessage(), e);
            return str;
        }
    }

    public static void main(String[] args){
        String str = encode("E:/test/test.pdf");
        System.out.println(str);
        System.out.println(decode(str));
    }
}
