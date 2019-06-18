package com.tasty.cowechat.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tasty.common.util.HttpUtil;
import com.tasty.common.util.Utils;
import com.tasty.cowechat.api.TokenService;
import com.tasty.cowechat.api.dto.GetUserIdDTO;
import com.tasty.cowechat.api.dto.GetUserInfoDTO;
import com.tasty.cowechat.api.service.WeChatApiAbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/15
 */
@Slf4j
@Component
public class GetUserIdService extends WeChatApiAbstractService<GetUserIdDTO> {

    private static final String URL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";

    @Override
    protected JSONObject http(GetUserIdDTO params) {
        JSONObject json = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("code", params.getCode());
            map.put("access_token", TokenService.getToken());
            String result = HttpUtil.sendGet(URL, map);
            if (!Utils.isEmpty(result)){
                json = JSON.parseObject(result);
            }
        } catch (Exception e){
            log.error("获取用户信息失败！", e);
        }
        return json;
    }
}
