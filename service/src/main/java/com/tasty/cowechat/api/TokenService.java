package com.tasty.cowechat.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tasty.common.util.HttpUtil;
import com.tasty.common.util.Utils;
import com.tasty.cowechat.api.constant.WeChatErrCode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/15
 */
@Slf4j
public class TokenService {
    private static String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";

    private static String validUrl = "https://open.work.weixin.qq.com/devtool/getInfoByAccessToken";

    private static String corpid = "wwb4ee61617da59e27";

    private static String corpsecret = "n3m-DxiMiWmrIn653V051ENfvzwVkv3CeLfpZrVixrE";

    private static String token = "";

    public static String getToken(){
        return getToken(false);
    }

    /**
     * 获取token
     * @param isExpire token是否已过期
     * @return
     */
    public synchronized static String getToken(boolean isExpire){
        if (Utils.isEmpty(token) || isExpire){
            buildToken();
        }
        return token;
    }

    private static void buildToken() {
        if (Utils.isEmpty(token) || !validToken()){
            try {
                generateToken();
            } catch (Exception e){
                log.error("access_token获取失败：", e);
            }
        }
    }

    /**
     * 验证token有效性
     * @return
     */
    private static boolean validToken(){
        boolean resultBool = false;
        try {
            Map<String, String> param = new HashMap<>();
            param.put("lang", "zh_CN");
            param.put("f", "json");
            param.put("ajax", "1");
            param.put("random", Math.random() + "");
            param.put("access_token", token);
            String result = HttpUtil.sendGet(validUrl, param);
            if (!Utils.isEmpty(result)){
                JSONObject json = JSON.parseObject(result);
                String data = json.getString("data");
                if (!Utils.isEmpty(data)){
                    resultBool = true;
                }
            }
        } catch (Exception e){
            log.error("校验token失败！", e);
            resultBool = false;
        }
        return resultBool;
    }

    /**
     * 获取新的token
     * @throws IOException
     */
    private static void generateToken() throws IOException {
        Map<String, String> param = new HashMap<>();
        param.put("corpid", corpid);
        param.put("corpsecret", corpsecret);
        String result = HttpUtil.sendGet(url, param);
        if (!Utils.isEmpty(result)){
            JSONObject json = JSON.parseObject(result);
            String errcode = json.getString("errcode");
            if (WeChatErrCode.SUCC.getCode().equals(errcode)){
                token = json.getString("access_token");
            } else {
                log.error("access_token获取失败：" + json.getString("errmsg"));
            }
        }
    }

    public static void main(String args[]){
        try {
            generateToken();
            System.out.println(token);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
