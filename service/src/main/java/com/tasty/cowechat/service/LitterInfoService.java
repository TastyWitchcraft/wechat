package com.tasty.cowechat.service;

import com.alibaba.fastjson.JSONObject;
import com.tasty.cowechat.api.constant.WeChatErrCode;
import com.tasty.cowechat.api.dto.GetUserInfoDTO;
import com.tasty.cowechat.api.service.IWeChatApiService;
import com.tasty.cowechat.api.service.impl.GetUserInfoService;
import com.tasty.cowechat.controller.vo.LitterInfoVO;
import com.tasty.cowechat.controller.vo.UserInfoVO;
import com.tasty.cowechat.controller.vo.response.LitterInfoResponse;
import com.tasty.mybatis.common.util.SpringUtil;
import com.tasty.mybatis.entity.LetterVisitPO;
import com.tasty.mybatis.mapper.IExamineMapper;
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

    @Autowired
    private IExamineMapper examineMapper;

    public LitterInfoVO queryLitterInfo(long letterId) {
        LetterVisitPO po = letterVisitMapper.queryById(letterId);
        LitterInfoVO vo = litterInfoPOTransVO(po);
        return vo;
    }

    public LitterInfoResponse queryLitterInfoList(String statusCd, int pageSize, int pageNo) {
        LitterInfoResponse result = new LitterInfoResponse();
        List<LitterInfoVO> litterInfos = new ArrayList<>();
        Map<String, Object> param = new HashMap<>();
        pageSize = pageSize > 0 ? pageSize : 8;
        param.put("statusCd", statusCd);
        param.put("pageSize", pageSize);
        param.put("start", (pageNo - 1) * pageSize);
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setTotal(letterVisitMapper.count(param));
        result.setLitterInfos(litterInfos);
        List<LetterVisitPO> list = letterVisitMapper.query(param);
        for (LetterVisitPO ele : list) {
            litterInfos.add(litterInfoPOTransVO(ele));
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

    private LitterInfoVO litterInfoPOTransVO(LetterVisitPO po){
        LitterInfoVO vo = new LitterInfoVO();
        vo.setLetterId(po.getLetterId());
        vo.setContents(po.getContents());
        vo.setCreateDate(po.getCreateDate());
        vo.setFileName(po.getFileName());
        vo.setDepartmentId(po.getDepartmentId());
        vo.setDepartmentName(po.getDepartmentName());
        vo.setTitle(po.getTitle());
        vo.setStatusDate(po.getStatusDate());
        vo.setType(po.getType());
        vo.setStatusCd(po.getStatusCd());
        vo.setUserId(po.getUserId());
        vo.setUserName(po.getUserName());
        vo.setDealUserId(po.getDealUserId());
        vo.setDealUserName(po.getDealUserName());
        vo.setExamines(examineMapper.queryByLatterId(po.getLetterId()));
        return vo;
    }
}
