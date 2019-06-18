package com.tasty.cowechat.controller.vo.request;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/17
 */
@Data
public class AddLetterInfoRequest implements Serializable {

    private String userId;

    private String title;

    private String contents;

    private String type;

    private long departmentId;

    private String departmentName;

    private String fileName;

    /**
     * 上传文件后返回的文件标志
     */
    private String fileSign;


    public static void main(String[] args){
        String str = "{\n" +
                "    \"userId\": \"ZhuZeXin\",\n" +
                "    \"title\": \"测试新增\",\n" +
                "    \"contents\": \"新增的xxxxxxxxxxxxxxxxxxrrrrrrrrrrrrrr\",\n" +
                "    \"type\": \"0\",\n" +
                "    \"departmentId\": \"1\",\n" +
                "    \"departmentName\": \"测试部\",\n" +
                "    \"fileName\": \"ceshi.xxl\",\n" +
                "    \"fileSign\": \"RTovdGVzdC90ZXN0LnBkZg==\"\n" +
                "}";
        AddLetterInfoRequest request = JSON.parseObject(str, AddLetterInfoRequest.class);
        System.out.println(request);
    }

}
