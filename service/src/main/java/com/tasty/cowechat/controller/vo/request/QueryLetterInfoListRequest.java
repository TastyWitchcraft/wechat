package com.tasty.cowechat.controller.vo.request;

import com.tasty.cowechat.controller.vo.PageVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/18
 */
@Data
public class QueryLetterInfoListRequest extends PageVO implements Serializable {

    private String statusCd;

    private String userId;

}
