package com.tasty.cowechat.service;

import com.alibaba.fastjson.JSONObject;
import com.tasty.common.result.ResultVO;
import com.tasty.cowechat.api.constant.WeChatErrCode;
import com.tasty.cowechat.api.dto.GetUserIdDTO;
import com.tasty.cowechat.api.service.IWeChatApiService;
import com.tasty.cowechat.api.service.impl.GetUserIdService;
import com.tasty.cowechat.controller.vo.UserInfoVO;
import com.tasty.mybatis.common.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/18
 */
@Slf4j
@Component
public class UserInfoService {

    public ResultVO getUserIdByCode(String code, String type){
        IWeChatApiService service = SpringUtil.getBean(GetUserIdService.class);
        GetUserIdDTO params = new GetUserIdDTO();
        params.setCode(code);
        params.setKey(type);
        JSONObject json = service.service(params);
        if (json != null && WeChatErrCode.SUCC.getCode().equals(json.getString("errcode"))) {
            return ResultVO.success(json.getString("UserId"));
        } else {
            return ResultVO.error(json == null ? "请求失败！" : json.getString("errmsg"));
        }
    }
}
