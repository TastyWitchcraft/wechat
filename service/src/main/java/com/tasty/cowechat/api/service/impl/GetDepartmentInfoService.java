package com.tasty.cowechat.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tasty.common.util.HttpUtil;
import com.tasty.common.util.Utils;
import com.tasty.cowechat.api.TokenService;
import com.tasty.cowechat.api.dto.GetDepartmentInfoDTO;
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
public class GetDepartmentInfoService extends WeChatApiAbstractService<GetDepartmentInfoDTO> {

    private static final String URL = "https://qyapi.weixin.qq.com/cgi-bin/department/list";

    @Override
    protected JSONObject http(GetDepartmentInfoDTO params) {
        JSONObject json = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("access_token", TokenService.getToken());
            if (params.getDepartmentId() != null) {
                map.put("id", params.getDepartmentId());
            }
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
