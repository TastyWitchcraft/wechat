package com.tasty.cowechat.controller;

import com.tasty.common.result.ResultVO;
import com.tasty.cowechat.api.dto.GetUserInfoDTO;
import com.tasty.cowechat.api.service.IWeChatApiService;
import com.tasty.cowechat.api.service.impl.GetUserInfoService;
import com.tasty.mybatis.common.util.SpringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/8
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/hello")
    public ResultVO Hello(){
        return  ResultVO.success("HelloWorld");
    }

    @RequestMapping("/getUserInfo")
    public ResultVO getUserInfo(String userId){
        IWeChatApiService service = SpringUtil.getBean(GetUserInfoService.class);
        GetUserInfoDTO params = new GetUserInfoDTO();
        params.setUserId(userId);
        return ResultVO.success(service.service(params));
    }

}
