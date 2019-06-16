package com.tasty.cowechat.api.dto;

import lombok.Data;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/15
 */
@Data
public class GetDepartmentInfoDTO {

    /**
     * 部门id。获取指定部门及其下的子部门。 如果不填，默认获取全量组织架构
     */
    private String departmentId;
}
