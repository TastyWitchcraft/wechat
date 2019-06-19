package com.tasty.cowechat.controller.vo;

import lombok.Data;

import javax.validation.constraints.Min;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/16
 */
@Data
public class PageVO {

    @Min(value = 1, message = "请输入有效的值")
    private Integer pageSize;

    @Min(value = 1, message = "请输入有效的页码")
    private Integer pageNo;

    private int total;
}
