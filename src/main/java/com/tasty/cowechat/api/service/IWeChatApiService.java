package com.tasty.cowechat.api.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/15
 */
public interface IWeChatApiService<T> {

    /**
     * 统一微信接口方法，当token失效时，自动获取token，重新调用接口。
     * @param params
     * @return
     */
    JSONObject service(T params);
}
