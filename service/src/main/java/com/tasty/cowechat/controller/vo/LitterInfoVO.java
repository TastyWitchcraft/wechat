package com.tasty.cowechat.controller.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.tasty.mybatis.entity.ExaminePO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/16
 */
@Data
public class LitterInfoVO implements Serializable {

    private long letterId;

    private String userId;

    private String userName;

    private long lateExamineId;

    private String dealUserId;

    private String dealUserName;

    private String title;

    private String contents;

    private String type;

    private long departmentId;

    private String departmentName;

    private String fileName;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date statusDate;

    private String statusCd;

    private List<ExaminePO> examines;

}
