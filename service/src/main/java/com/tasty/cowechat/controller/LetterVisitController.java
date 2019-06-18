package com.tasty.cowechat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tasty.common.result.ResultVO;
import com.tasty.common.util.DateUtil;
import com.tasty.common.util.Utils;
import com.tasty.cowechat.api.constant.WeChatErrCode;
import com.tasty.cowechat.api.dto.GetDepartmentInfoDTO;
import com.tasty.cowechat.api.service.IWeChatApiService;
import com.tasty.cowechat.api.service.impl.GetDepartmentInfoService;
import com.tasty.cowechat.controller.vo.DepartmentVO;
import com.tasty.cowechat.controller.vo.LitterInfoVO;
import com.tasty.cowechat.controller.vo.response.LitterInfoResponse;
import com.tasty.cowechat.service.LitterInfoService;
import com.tasty.mybatis.common.util.SpringUtil;
import com.tasty.mybatis.entity.LetterVisitPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/16
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/letterVisist")
public class LetterVisitController {

    @Value("${local.filePath}")
    private String filePath;

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

    @RequestMapping("/queryInfoList")
    public ResultVO queryLetterVisit(String statusCd, int pageSize, int pageNo){
        LitterInfoResponse result = LitterService.queryLitterInfoList(statusCd, pageSize, pageNo);
        return ResultVO.success(result);
    }

    @RequestMapping("/queryInfo")
    public ResultVO queryLetterVisit(long letterId){
        LitterInfoVO result = LitterService.queryLitterInfo(letterId);
        return ResultVO.success(result);
    }

    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    @ResponseBody
    public ResultVO upload(MultipartFile file){
        if (file.isEmpty()) {
            return ResultVO.error("上传失败,请选择文件");
        }
        try {
            String fileName = Utils.getUUID32();
            String path = DateUtil.getNow(DateUtil.PATTERN_DATE);
            file.transferTo(new File(filePath + path));
            return ResultVO.success(path);
        } catch (Exception e){
            log.error("文件上传失败！" + e.getMessage(), e);
            return ResultVO.error("文件上传失败！" + e.getMessage());
        }
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResultVO download(HttpServletResponse response, long letterId) throws Exception{
        InputStream in = null;
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            LetterVisitPO po = LitterService.getletterBaseInfo(letterId);
            String path = po.getFileUrl();
            String fileName = po.getFileName();
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            File file = new File(path);
            in = new FileInputStream(file);
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = in.read(bytes)) > 0){
                out.write(bytes, 0, len);
            }
            return ResultVO.success("下载成功：" + fileName);
        } catch (Exception e) {
            log.error("下载失败：" + e.getMessage(), e);
            return ResultVO.error("下载失败：" + e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
