package com.tasty.cowechat.api.service;

import com.alibaba.fastjson.JSONObject;
import com.tasty.cowechat.api.TokenService;
import com.tasty.cowechat.api.constant.WeChatErrCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/15
 */
@Slf4j
public abstract class WeChatApiAbstractService<T> implements IWeChatApiService<T> {

    @Override
    public JSONObject service(T params){
        return service(params, 1);
    }

    private JSONObject service(T params, int i){
        JSONObject json = http(params);
        String errcode = json.getString("errcode");
        boolean isExpire = WeChatErrCode.TOKEN_EXPIRE.getCode().equals(errcode) || WeChatErrCode.TOKEN_INVALID.getCode().equals(errcode);
        if (isExpire && i-- > 0){
            TokenService.getToken(true);
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
}
