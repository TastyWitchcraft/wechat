package com.tasty.cowechat.controller.vo.response;

import com.tasty.cowechat.controller.vo.LitterInfoVO;
import com.tasty.cowechat.controller.vo.PageVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/16
 */
@Data
public class LitterInfoResponse extends PageVO implements Serializable {

    private List<LitterInfoVO> litterInfos;
}
