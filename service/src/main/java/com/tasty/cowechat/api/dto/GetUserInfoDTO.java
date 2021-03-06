package com.tasty.cowechat.api.dto;

import com.tasty.cowechat.api.constant.WeChatConsts;
import lombok.Data;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/15
 */
@Data
public class GetUserInfoDTO extends WeChatDTO {

    public GetUserInfoDTO(){
        setKey(WeChatConsts.KEY_AL);
    }

    private String userId;
}
