package com.tasty.cowechat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tasty.common.result.ResultVO;
import com.tasty.common.util.Utils;
import com.tasty.cowechat.api.constant.WeChatErrCode;
import com.tasty.cowechat.api.dto.GetDepartmentInfoDTO;
import com.tasty.cowechat.api.service.IWeChatApiService;
import com.tasty.cowechat.api.service.impl.GetDepartmentInfoService;
import com.tasty.cowechat.controller.vo.DepartmentVO;
import com.tasty.cowechat.controller.vo.response.LitterInfoResponse;
import com.tasty.cowechat.service.LitterInfoService;
import com.tasty.mybatis.common.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/16
 */
@RestController
@RequestMapping("/letterVisist")
public class LetterVisitController {

    @Autowired
    private LitterInfoService LitterService;

    /**
     * 查询部门列表
     * @param departmentId 部门id。获取指定部门及其下的子部门。 如果不填，默认获取全量组织架构
     * @return
     */
    @RequestMapping("/getDepartmentInfo")
    public ResultVO getDepartmentInfo(String departmentId){
        IWeChatApiService service = SpringUtil.getBean(GetDepartmentInfoService.class);
        GetDepartmentInfoDTO params = new GetDepartmentInfoDTO();
        if (!Utils.isEmpty(departmentId)){
            params.setDepartmentId(departmentId);
        }
        JSONObject json = service.service(params);
        if (json == null){
            return ResultVO.error("获取部门信息失败！");
        } else if (!WeChatErrCode.SUCC.getCode().equals(json.getString("errcode"))){
            return ResultVO.error(json.getString("errmsg"));
        } else {
            List<DepartmentVO> list = JSON.parseArray(json.getString("department"), DepartmentVO.class);
            return ResultVO.success(list);
        }
    }

    @RequestMapping("/queryInfo")
    public ResultVO queryLetterVisit(String statusCd, int pageSize, int pageNo){
        LitterInfoResponse result = LitterService.queryLitterInfo(statusCd, pageSize, pageNo);
        return ResultVO.success(result);
    }

}
