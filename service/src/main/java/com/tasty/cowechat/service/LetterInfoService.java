package com.tasty.cowechat.service;

import com.tasty.common.util.Base64Util;
import com.tasty.common.util.Utils;
import com.tasty.cowechat.api.util.UserInfoUtil;
import com.tasty.cowechat.common.localcache.FileCache;
import com.tasty.cowechat.controller.vo.LitterInfoVO;
import com.tasty.cowechat.controller.vo.request.AddLetterInfoRequest;
import com.tasty.cowechat.controller.vo.request.QueryLetterInfoListRequest;
import com.tasty.cowechat.controller.vo.response.LitterInfoResponse;
import com.tasty.mybatis.common.constant.SeqConsts;
import com.tasty.mybatis.common.util.SeqUtil;
import com.tasty.mybatis.entity.LetterVisitPO;
import com.tasty.mybatis.mapper.IExamineMapper;
import com.tasty.mybatis.mapper.ILetterVisitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/16
 */
@Component
public class LetterInfoService {

    @Value("${local.filePath}")
    private String filePath;

    @Autowired
    private ILetterVisitMapper letterVisitMapper;

    @Autowired
    private IExamineMapper examineMapper;

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
        LitterInfoResponse result = new LitterInfoResponse();
        List<LitterInfoVO> litterInfos = new ArrayList<>();
        Map<String, Object> param = new HashMap<>();
        int pageSize = request.getPageSize() > 0 ? request.getPageSize() : 8;
        param.put("statusCd", request.getStatusCd());
        param.put("pageSize", pageSize);
        param.put("start", (request.getPageNo() - 1) * pageSize);
        param.put("userId", request.getUserId());
        result.setPageNo(request.getPageNo());
        result.setPageSize(pageSize);
        result.setTotal(letterVisitMapper.count(param));
        result.setLitterInfos(litterInfos);
        List<LetterVisitPO> list = letterVisitMapper.query(param);
        for (LetterVisitPO ele : list) {
            litterInfos.add(letterInfoPOTransVO(ele));
        }
        return result;
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
