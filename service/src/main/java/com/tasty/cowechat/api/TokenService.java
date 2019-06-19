package com.tasty.cowechat.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tasty.common.util.HttpUtil;
import com.tasty.common.util.Utils;
import com.tasty.cowechat.api.constant.WeChatConsts;
import com.tasty.cowechat.api.constant.WeChatErrCode;
import com.tasty.mybatis.common.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/15
 */
@Slf4j
@Component
public class TokenService {

    private static final String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";

    private static final String validUrl = "https://open.work.weixin.qq.com/devtool/getInfoByAccessToken";

    @Value("${cowechat.corpid}")
    private String corpid;

    @Value("${cowechat.corpsecret.examineManage}")
    private String corpsecretEM;

    @Value("${cowechat.corpsecret.letterVisit}")
    private String corpsecretLV;

    @Value("${cowechat.corpsecret.addressList}")
    private String corpsecretAL;

    private static Map<String, String> tokenMap = new HashMap<>();

    private static String getSecret(String tokenKey) throws Exception{
        TokenService service = getInst();
        switch (tokenKey){
            case WeChatConsts.TOKEN_KEY_AL :
                return service.corpsecretAL;
            case WeChatConsts.TOKEN_KEY_EM :
                return service.corpsecretEM;
            case WeChatConsts.TOKEN_KEY_LV :
                return service.corpsecretLV;
                default:
                    throw new Exception("不存在的tokenKey：" + tokenKey);
        }
    }

    public static TokenService getInst(){
        return SpringUtil.getBean(TokenService.class);
    }

    /**
     * 获取token
     * @return
     */
    public static String getToken(String tokenKey){
        return getToken(tokenKey, false);
    }

    /**
     * 获取token
     * @param isExpire token是否已过期
     * @return
     */
    public static synchronized String getToken(String tokenKey, boolean isExpire){
        if (Utils.isEmpty(tokenMap.get(tokenKey)) || isExpire){
            buildToken(tokenKey);
        }
        return tokenMap.get(tokenKey);
    }

    private static void buildToken(String tokenKey) {
        if (Utils.isEmpty(tokenMap.get(tokenKey)) || !validToken(tokenKey)){
            try {
                generateToken(tokenKey);
            } catch (Exception e){
                log.error("access_token获取失败tokenKey：" + tokenKey, e);
            }
        }
    }

    /**
     * 验证token有效性
     * @return
     */
    private static boolean validToken(String tokenKey){
        boolean resultBool = false;
        try {
            Map<String, String> param = new HashMap<>();
            param.put("lang", "zh_CN");
            param.put("f", "json");
            param.put("ajax", "1");
            param.put("random", Math.random() + "");
            param.put("access_token", tokenMap.get(tokenKey));
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
    private static void generateToken(String tokenKey) throws Exception {
        Map<String, String> param = new HashMap<>();
        param.put("corpid", TokenService.getInst().corpid);
        param.put("corpsecret", getSecret(tokenKey));
        String result = HttpUtil.sendGet(url, param);
        if (!Utils.isEmpty(result)){
            JSONObject json = JSON.parseObject(result);
            String errcode = json.getString("errcode");
            if (WeChatErrCode.SUCC.getCode().equals(errcode)){
                tokenMap.put(tokenKey, json.getString("access_token"));
            } else {
                log.error("access_token获取失败：" + json.getString("errmsg"));
            }
        } else {
            log.error("access_token获取失败tokenKey：" + tokenKey);
        }
    }

    public static void main(String args[]){
        try {
            generateToken(WeChatConsts.TOKEN_KEY_EM);
            generateToken(WeChatConsts.TOKEN_KEY_LV);
            System.out.println(tokenMap);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
