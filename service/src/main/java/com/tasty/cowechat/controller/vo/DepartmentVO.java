package com.tasty.cowechat.controller.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/16
 */
@Data
public class DepartmentVO implements Serializable {

    private long id;

    private long parentid;

    private String name;

}
