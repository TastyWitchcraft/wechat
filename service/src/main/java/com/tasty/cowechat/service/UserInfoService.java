package com.tasty.cowechat.service;

import com.alibaba.fastjson.JSONObject;
import com.tasty.common.result.ResultVO;
import com.tasty.cowechat.api.constant.WeChatErrCode;
import com.tasty.cowechat.api.dto.GetUserIdDTO;
import com.tasty.cowechat.api.service.IWeChatApiService;
import com.tasty.cowechat.api.service.impl.GetUserIdService;
import com.tasty.cowechat.api.util.UserInfoUtil;
import com.tasty.cowechat.controller.vo.UserInfoVO;
import com.tasty.mybatis.common.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/18
 */
@Slf4j
@Component
public class UserInfoService {

    @Value("#{'${cowechat.examine.lead.userIds}'.split(',')}")
    private List<String> leadUserIds;

    public ResultVO getUserIdByCode(String code, String type){
        IWeChatApiService service = SpringUtil.getBean(GetUserIdService.class);
        GetUserIdDTO params = new GetUserIdDTO();
        params.setCode(code);
        params.setKey(type);
        JSONObject json = service.service(params);
        if (json != null && WeChatErrCode.SUCC.getCode().equals(json.getString("errcode"))) {
            String userId = json.getString("UserId");
            Map<String, String> data = new HashMap<>();
            data.put("userId", userId);
            data.put("isLead", leadUserIds.contains(userId) ? "0" :"-1");
            return ResultVO.success(data);
        } else {
            return ResultVO.error(json == null ? "请求失败！" : json.getString("errmsg"));
        }
    }

    public ResultVO getLeadUserInfo(){
        List<UserInfoVO> list = new ArrayList<>();
        for (String userId : leadUserIds){
            UserInfoVO vo = UserInfoUtil.getUserInfo(userId, true);
            list.add(vo);
        }
        return ResultVO.success(list);
    }
}
