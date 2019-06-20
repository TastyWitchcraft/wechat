package com.tasty.cowechat.controller.vo.request;

import com.tasty.cowechat.controller.vo.PageVO;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/18
 */
@Data
public class QueryLetterInfoListRequest extends PageVO implements Serializable {

    private String statusCd;

    @NotBlank(message = "userId不能为空")
    private String userId;

    //@Pattern(regexp = "^(EM|LV)$", message = "type值非法，【信访管理：EM；信访平台：LV】")
    private String type;

}
