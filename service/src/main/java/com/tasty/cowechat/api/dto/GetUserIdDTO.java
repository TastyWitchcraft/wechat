package com.tasty.cowechat.api.dto;

import com.tasty.cowechat.api.constant.WeChatConsts;
import lombok.Data;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/18
 */
@Data
public class GetUserIdDTO extends WeChatDTO {

    public GetUserIdDTO(){
        setKey(WeChatConsts.KEY_AL);
    }

    private String code;
}
