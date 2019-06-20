package com.tasty.cowechat.controller.vo.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
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
    @Min(value = 0, message = "请输入上一次审核Id")
    private Long preExamineId;

    @Min(value = 1, message = "请输入信访Id")
    private Long letterId;

    @NotBlank(message = "处理用户ID不能为空")
    private String dealUserId;

    private String nextDealUserId;

    @NotBlank(message = "意见不能为空")
    private String examineAdvise;

    /**
     * 审批结果 0：反馈意见 1：成功归档 2：失败归档
     */
    @Range(min=0, max=2, message = "result值非法，【0：反馈意见 1：成功归档 2：失败归档】")
    private Integer result;

}
