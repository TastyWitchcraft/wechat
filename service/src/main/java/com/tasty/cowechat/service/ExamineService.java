package com.tasty.cowechat.service;

import com.tasty.common.util.Utils;
import com.tasty.cowechat.api.util.UserInfoUtil;
import com.tasty.cowechat.controller.vo.UserInfoVO;
import com.tasty.cowechat.controller.vo.request.AddExamineRequest;
import com.tasty.mybatis.common.constant.SeqConsts;
import com.tasty.mybatis.common.util.SeqUtil;
import com.tasty.mybatis.entity.ExaminePO;
import com.tasty.mybatis.mapper.IExamineMapper;
import com.tasty.mybatis.mapper.ILetterVisitMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/18
 */
@Slf4j
@Component
public class ExamineService {

    private static final Map<Integer, String> RESULT_TRANS_STATUS_CD = new HashMap<>();

    @Autowired
    private ILetterVisitMapper letterVisitMapper;

    @Autowired
    private IExamineMapper examineMapper;

    static {
        RESULT_TRANS_STATUS_CD.put(0, "00D");
        RESULT_TRANS_STATUS_CD.put(1, "00S");
        RESULT_TRANS_STATUS_CD.put(2, "00F");
    }

    @Transactional(rollbackFor = Exception.class)
    public void addExamine(AddExamineRequest request) throws Exception{
        Map<String, Object> param = new HashMap<>();
        ExaminePO po = requestTransPO(request);
        long examineId = SeqUtil.getInst().getSequence(SeqConsts.EXAMINE);
        po.setExamineId(examineId);
        po.setDealUserName(UserInfoUtil.getUserInfo(request.getDealUserId()).getUserName());
        param.put("letterId", request.getLetterId());
        param.put("examineId", examineId);
        param.put("lateExamineId", request.getPreExamineId());
        String statusCd = RESULT_TRANS_STATUS_CD.get(request.getResult());
        if ("00D".equals(statusCd)){
            String nextDealUserId = request.getNextDealUserId();
            if (Utils.isEmpty(nextDealUserId)){
                ExaminePO preExamine = examineMapper.queryById(request.getPreExamineId());
                nextDealUserId = preExamine.getDealUserId();
                param.put("dealUserName", preExamine.getDealUserName());
            } else {
                UserInfoVO userInfo = UserInfoUtil.getUserInfo(nextDealUserId);
                po.setTransferUserId(nextDealUserId);
                po.setTransferUserName(userInfo.getUserName());
                param.put("dealUserName", userInfo.getUserName());
            }
            param.put("dealUserId", nextDealUserId);
        } else {
            param.put("statusCd", statusCd);
        }
        int i = letterVisitMapper.updateById(param);
        if (i > 0){
            examineMapper.insert(po);
        } else {
            throw new Exception("该信访信息已被处理，请刷新重试！");
        }
    }

    private ExaminePO requestTransPO(AddExamineRequest request){
        ExaminePO po = new ExaminePO();
        po.setLetterId(request.getLetterId());
        po.setDealUserId(request.getDealUserId());
        po.setExamineAdvise(request.getExamineAdvise());
        po.setStatusCd(RESULT_TRANS_STATUS_CD.get(request.getResult()));
        return po;
    }
}
