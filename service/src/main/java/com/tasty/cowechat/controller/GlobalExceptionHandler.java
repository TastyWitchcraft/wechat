package com.tasty.cowechat.controller;

import com.tasty.common.result.ResultVO;
import com.tasty.common.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * 统一异常控制类，捕获系统抛出的异常进行封装后返回到前台
 * @Auther: zhu.zexin
 * @Date: 2019/6/19
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultVO handleException(HttpServletRequest request, Exception ex){
    	log.error("请求失败[" + request.getRequestURI() + "]", ex);
        return ResultVO.error(ex.getMessage());
    }

    /**
     * 参数校验异常捕获
     * @param request
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultVO handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException ex){
        log.error("请求失败[" + request.getRequestURI() + "]", ex);
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        String message = "参数异常，请检查参数！";
        if (!Utils.isEmpty(constraintViolations)){
            for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                message = constraintViolation.getMessage();
                break;
            }
        }
        return ResultVO.error(message);
    }
}
