package com.tasty.cowechat.api.util;

import com.alibaba.fastjson.JSONObject;
import com.tasty.cowechat.api.constant.WeChatErrCode;
import com.tasty.cowechat.api.dto.GetUserInfoDTO;
import com.tasty.cowechat.api.service.IWeChatApiService;
import com.tasty.cowechat.api.service.impl.GetUserInfoService;
import com.tasty.cowechat.controller.vo.UserInfoVO;
import com.tasty.mybatis.common.util.SpringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/18
 */
public class UserInfoUtil {

    private static final Map<String, UserInfoVO> userCache = new HashMap<>();

    /**
     * 根据UserId获取用户信息, 可设置是否优先取本地缓存
     * @param userId
     * @param isCache 当 true 时，优先取本地缓存
     * @return
     */
    public static UserInfoVO getUserInfo(String userId, boolean isCache) {
        if (isCache && userCache.get(userId) != null){
            return userCache.get(userId);
        }
        IWeChatApiService service = SpringUtil.getBean(GetUserInfoService.class);
        GetUserInfoDTO params = new GetUserInfoDTO();
        params.setUserId(userId);
        JSONObject json = service.service(params);
        if (json == null || !WeChatErrCode.SUCC.getCode().equals(json.getString("errcode"))) {
            return new UserInfoVO();
        } else {
            UserInfoVO userInfo = json.toJavaObject(UserInfoVO.class);
            userCache.put(userId, userInfo);
            return userInfo;
        }
    }

    /**
     * 根据UserId给微信端获取获取用户信息
     * @param userId
     * @return
     */
    public static UserInfoVO getUserInfo(String userId) {
        return getUserInfo(userId, false);
    }

}
