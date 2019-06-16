package com.tasty.cowechat.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tasty.cowechat.api.constant.WeChatErrCode;
import com.tasty.cowechat.api.dto.GetUserInfoDTO;
import com.tasty.cowechat.api.service.IWeChatApiService;
import com.tasty.cowechat.api.service.impl.GetUserInfoService;
import com.tasty.cowechat.controller.vo.LitterInfoVO;
import com.tasty.cowechat.controller.vo.UserInfoVO;
import com.tasty.cowechat.controller.vo.response.LitterInfoResponse;
import com.tasty.mybatis.common.util.SpringUtil;
import com.tasty.mybatis.mapper.ILetterVisitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/16
 */
@Component
public class LitterInfoService {

    @Autowired
    private ILetterVisitMapper letterVisitMapper;

    public LitterInfoResponse queryLitterInfo(String statusCd, int pageSize, int pageNo) {
        LitterInfoResponse result = new LitterInfoResponse();
        List<LitterInfoVO> litterInfos = new ArrayList<>();
        Map<String, Object> param = new HashMap<>();
        Map<String, UserInfoVO> userCache = new HashMap<>();
        pageSize = pageSize > 0 ? pageSize : 8;
        param.put("statusCd", statusCd);
        param.put("pageSize", pageSize);
        param.put("start", (pageNo - 1) * pageSize);
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setTotal(letterVisitMapper.count(param));
        result.setLitterInfos(litterInfos);
        List<Map<String, Object>> list = letterVisitMapper.query(param);
        for (Map<String, Object> ele : list) {
            LitterInfoVO vo = new LitterInfoVO();
            vo.setId((long) ele.get("ID"));
            vo.setContents((String) ele.get("CONTENTS"));
            vo.setCreateDate((Date) ele.get("CREATE_DATE"));
            vo.setFileName((String) ele.get("FILE_NAME"));
            vo.setDepartmentId((long) ele.get("DEPARTMENT_ID"));
            vo.setDepartmentName((String) ele.get("DEPARTMENT_NAME"));
            vo.setName((String) ele.get("NAME"));
            vo.setStatusDate((Date) ele.get("STATUS_DATE"));
            vo.setType((String) ele.get("TYPE"));
            vo.setStatusCd((String) ele.get("STATUS_CD"));
            String userId = (String) ele.get("USER_ID");
            String dealUserId = (String) ele.get("DEAL_USER_ID");
            vo.setUserInfo(getUserInfo(userId, userCache));
            vo.setDealUserInfo(getUserInfo(dealUserId, userCache));
            litterInfos.add(vo);
        }
        return result;
    }

    private UserInfoVO getUserInfo(String userId, Map<String, UserInfoVO> userCache) {
        if (userCache.get(userId) != null){
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
}
