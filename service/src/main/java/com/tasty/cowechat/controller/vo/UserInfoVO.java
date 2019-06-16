package com.tasty.cowechat.controller.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/16
 */
@Data
public class UserInfoVO {

    private String userId;

    private String userName;

    private String mobile;

    /**
     * 职务
     */
    private String position;

    @JSONField(name="userid")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JSONField(name="name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

}
