package com.tasty.cowechat.api.constant;

import lombok.Getter;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/15
 */
@Getter
public enum WeChatErrCode {
    SUCC("0", "请求成功"),
    TOKEN_INVALID("40014", "不合法的access_token"),
    TOKEN_EXPIRE("42001", "access_token已过期");
    private String code;
    private String desc;

    WeChatErrCode(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

}
