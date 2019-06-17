package com.tasty.mybatis.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/17
 */
@Data
public class ExaminePO implements Serializable {

    private long examineId;

    private long letterId;

    private String dealUserId;

    private String dealUserName;

    private String examineAdvise;

    private Date createDate;

    private Date statusDate;

    private String statusCd;

}
