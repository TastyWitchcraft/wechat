package com.tasty.cowechat.controller.vo;

import lombok.Data;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/16
 */
@Data
public class PageVO {
    private int pageSize;

    private int pageNo;

    private int total;
}
