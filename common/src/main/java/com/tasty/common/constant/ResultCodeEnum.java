package com.tasty.common.constant;

import lombok.Getter;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/8
 */
@Getter
public enum ResultCodeEnum {
	// 枚举值
	SUCCESS("0", "SUCCESS"),
	ERROR("-1", "ERROR");

	private String code;
	private String desc;

	ResultCodeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
