package com.tasty.cowechat.service;

import com.tasty.common.util.Utils;
import com.tasty.cowechat.api.constant.WeChatConsts;
import com.tasty.cowechat.api.util.UserInfoUtil;
import com.tasty.cowechat.common.localcache.FileCache;
import com.tasty.cowechat.controller.vo.LitterInfoVO;
import com.tasty.cowechat.controller.vo.request.AddLetterInfoRequest;
import com.tasty.cowechat.controller.vo.request.AddSatisfiedRequest;
import com.tasty.cowechat.controller.vo.request.QueryLetterInfoListRequest;
import com.tasty.cowechat.controller.vo.response.LitterInfoResponse;
import com.tasty.mybatis.common.constant.SeqConsts;
import com.tasty.mybatis.common.util.SeqUtil;
import com.tasty.mybatis.entity.ExaminePO;
import com.tasty.mybatis.entity.LetterVisitPO;
import com.tasty.mybatis.mapper.IExamineMapper;
import com.tasty.mybatis.mapper.ILetterVisitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/16
 */
@Component
public class LetterInfoService {

    @Value("${local.filePath}")
    private String filePath;

    @Value("#{'${cowechat.examine.lead.userIds}'.split(',')}")
    private List<String> leadUserIds;

    private static final Map<String, String> TO_STATE = new HashMap<>();

    @Autowired
    private ILetterVisitMapper letterVisitMapper;

    @Autowired
    private IExamineMapper examineMapper;

    static {
        TO_STATE.put("0", "'00D'");
        TO_STATE.put("1", "'00D'");
        TO_STATE.put("2", "'00S'");
        TO_STATE.put("3", "'00F'");
        TO_STATE.put("5", "'00S', '00F'");
    }

    public void addLetterInfo(AddLetterInfoRequest request) throws Exception{
        LetterVisitPO po = letterRequestTransPO(request);
        if (!Utils.isEmpty(request.getFileName())){
            String path = FileCache.getFilePath(request.getFileSign());
            if (Utils.isEmpty(path)){
                throw new Exception("上传文件已失效！");
            }
            po.setFileUrl(filePath + path);
        }
        long letterId = SeqUtil.getInst().getSequence(SeqConsts.LETTER_VISIT);
        po.setLetterId(letterId);
        po.setUserName(UserInfoUtil.getUserInfo(request.getUserId()).getUserName());
        letterVisitMapper.insert(po);
    }

    public LetterVisitPO getletterBaseInfo(long letterId) {
        LetterVisitPO po = letterVisitMapper.queryById(letterId);
        return po;
    }

    public LitterInfoVO queryLitterInfo(long letterId) {
        LetterVisitPO po = letterVisitMapper.queryById(letterId);
        LitterInfoVO vo = letterInfoPOTransVO(po);
        return vo;
    }

    public LitterInfoResponse queryLitterInfoList(QueryLetterInfoListRequest request) {
        String statusCds = TO_STATE.get(request.getStatusCd());
        LitterInfoResponse result = new LitterInfoResponse();
        List<LitterInfoVO> litterInfos = new ArrayList<>();
        Map<String, Object> param = new HashMap<>();
        int pageSize = request.getPageSize() > 0 ? request.getPageSize() : 8;
        param.put("statusCd", statusCds);
        param.put("pageSize", pageSize);
        param.put("start", (request.getPageNo() - 1) * pageSize);
        if (WeChatConsts.KEY_LV.equals(request.getType())){
            param.put("userId", request.getUserId());
        } else if (WeChatConsts.KEY_EM.equals(request.getType()) && "0".equals(request.getStatusCd())){
            param.put("isLead", leadUserIds.contains(request.getUserId()));
            param.put("dealUserId", request.getUserId());
        }
        int total = letterVisitMapper.count(param);
        result.setPageNo(request.getPageNo());
        result.setPageSize(pageSize);
        result.setTotal(total);
        result.setLitterInfos(litterInfos);
        if (total > 0){
            List<LetterVisitPO> list = letterVisitMapper.query(param);
            for (LetterVisitPO ele : list) {
                LitterInfoVO vo = letterInfoPOTransVO(ele);
                if (!WeChatConsts.KEY_EM.equals(request.getType())){
                    List<ExaminePO> examines = vo.getExamines();
                    examines.forEach((e) -> {
                        e.setExamineAdvise("*****");
                    });
                }
                litterInfos.add(vo);
            }
        }
        return result;
    }

    @Transactional
    public void addSatisfied(AddSatisfiedRequest request) throws Exception{
        Map<String, Object> param = new HashMap<>();
        param.put("letterId", request.getLetterId());
        param.put("satisfied", request.getSatisfied());
        param.put("evaluate", request.getEvaluate());
        int i = letterVisitMapper.updateSatisfiedById(param);
        if (i <= 0){
            throw new Exception("评论失败！");
        }
    }

    private LitterInfoVO letterInfoPOTransVO(LetterVisitPO po){
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
        vo.setLateExamineId(po.getLateExamineId());
        vo.setDealUserId(po.getDealUserId());
        vo.setDealUserName(po.getDealUserName());
        vo.setSatisfied(po.getSatisfied());
        vo.setEvaluate(po.getEvaluate());
        vo.setExamines(examineMapper.queryByLatterId(po.getLetterId()));
        return vo;
    }

    private LetterVisitPO letterRequestTransPO(AddLetterInfoRequest request){
        LetterVisitPO po = new LetterVisitPO();
        po.setContents(request.getContents());
        po.setTitle(request.getTitle());
        po.setType(request.getType());
        po.setFileName(request.getFileName());
        po.setUserId(request.getUserId());
        po.setDepartmentId(request.getDepartmentId());
        po.setDepartmentName(request.getDepartmentName());
        return po;
    }
}
