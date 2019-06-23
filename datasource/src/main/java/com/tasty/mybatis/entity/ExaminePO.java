package com.tasty.mybatis.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date statusDate;

    private String statusCd;

    private String transferUserId;

    private String transferUserName;
}
