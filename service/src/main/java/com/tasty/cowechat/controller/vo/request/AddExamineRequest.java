package com.tasty.cowechat.controller.vo.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/18
 */
@Data
public class AddExamineRequest implements Serializable {

    /**
     * 保留字段，上一次的审核id，以防同一流程，多人发起审核！
     */
    private long preExamineId;

    private long letterId;

    private String dealUserId;

    private String nextDealUserId;

    private String examineAdvise;

    /**
     * 审批结果 0：反馈意见 1：成功归档 2：失败归档
     */
    private int result;

}
