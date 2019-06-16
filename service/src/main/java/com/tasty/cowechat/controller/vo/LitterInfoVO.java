package com.tasty.cowechat.controller.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/16
 */
@Data
public class LitterInfoVO implements Serializable {

    private long id;

    private UserInfoVO userInfo;

    private UserInfoVO dealUserInfo;

    private String name;

    private String contents;

    private String type;

    private long departmentId;

    private String departmentName;

    private String fileName;

//    private String fileUrl;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date statusDate;

    private String statusCd;

}
