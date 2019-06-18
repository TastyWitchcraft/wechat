package com.tasty.mybatis.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/17
 */
@Data
public class LetterVisitPO implements Serializable {

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

    private String fileUrl;

    private Date createDate;

    private Date statusDate;

    private String statusCd;


}
