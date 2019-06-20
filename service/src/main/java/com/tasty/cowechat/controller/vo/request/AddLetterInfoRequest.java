package com.tasty.cowechat.controller.vo.request;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/17
 */
@Data
public class AddLetterInfoRequest implements Serializable {

    @NotBlank(message = "用户ID不能为空")
    private String userId;

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "内容不能为空")
    private String contents;

    @Pattern(regexp = "^[0-2]$", message = "type值非法，【0:问题解决；1：意见建议；2：投诉举报】")
    private String type;

    private long departmentId;

    @NotBlank(message = "部门名称不能为空")
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
