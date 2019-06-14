package com.tasty.cowechat.controller;

import com.tasty.common.result.ResultVO;
import com.tasty.mybatis.common.util.SeqUtil;
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
        //return ResultVO.success("Hello World!");
        return  ResultVO.success(SeqUtil.getInst().getSequence("TBL_FS"));
    }
}
