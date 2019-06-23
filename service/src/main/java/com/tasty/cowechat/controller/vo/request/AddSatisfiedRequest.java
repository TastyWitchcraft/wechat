package com.tasty.cowechat.controller.vo.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/18
 */
@Data
public class AddSatisfiedRequest implements Serializable {

    @Min(value = 1, message = "请输入信访Id")
    private Long letterId;

    private String satisfied;

    private String evaluate;
}
