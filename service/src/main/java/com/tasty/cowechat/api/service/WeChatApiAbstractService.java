package com.tasty.cowechat.api.service;

import com.alibaba.fastjson.JSONObject;
import com.tasty.cowechat.api.TokenService;
import com.tasty.cowechat.api.constant.WeChatConsts;
import com.tasty.cowechat.api.constant.WeChatErrCode;
import com.tasty.cowechat.api.dto.WeChatDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/15
 */
@Slf4j
public abstract class WeChatApiAbstractService<T> implements IWeChatApiService<T> {

    private static final Map<String, String> TO_TOKEN_KEY = new HashMap<>();

    private String tokenKey;

    static {
        TO_TOKEN_KEY.put(WeChatConsts.KEY_EM, WeChatConsts.TOKEN_KEY_EM);
        TO_TOKEN_KEY.put(WeChatConsts.KEY_LV, WeChatConsts.TOKEN_KEY_LV);
        TO_TOKEN_KEY.put(WeChatConsts.KEY_AL, WeChatConsts.TOKEN_KEY_AL);
    }

    @Override
    public JSONObject service(T params){
        this.tokenKey = TO_TOKEN_KEY.get(((WeChatDTO)params).getKey());
        return service(params, 1);
    }

    private JSONObject service(T params, int i){
        JSONObject json = http(params);
        String errcode = json.getString("errcode");
        boolean isExpire = WeChatErrCode.TOKEN_EXPIRE.getCode().equals(errcode) || WeChatErrCode.TOKEN_INVALID.getCode().equals(errcode);
        if (isExpire && i-- > 0){
            TokenService.getToken(this.tokenKey, true);
            json = service(params, i);
        }
        return json;
    }

    /**
     * 调用微信接口
     * @param params
     * @return
     */
    protected abstract JSONObject http(T params);

    protected String getToken(){
        return TokenService.getToken(this.tokenKey);
    }
}
