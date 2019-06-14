package com.tasty.common.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tasty.common.constant.ResultCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/8
 */
@Data
public class ResultVO<T> implements Serializable {

	/**
	 * 响应编码
	 */
	private String resultCode;

	/**
	 * 响应消息
	 */
	private String resultMsg;

	/**
	 * 响应结果
	 */
	private T resultData;

	public ResultVO(){}

	private ResultVO(String resultCode){
		this.resultCode = resultCode;
	}

	private ResultVO(String resultCode, T resultData){
		this.resultCode = resultCode;
		this.resultData = resultData;
	}

	private ResultVO(String resultCode, String resultMsg, T resultData){
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
		this.resultData = resultData;
	}

	private ResultVO(String resultCode, String resultMsg){
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

	public void setCodeInfo(ResultCodeEnum codeInfo, String... replace) {
		resultCode = codeInfo.getCode();
		resultMsg = codeInfo.getDesc();
		if (replace != null) {
			for (int i = 0; i < replace.length; i++) {
				resultMsg = resultMsg.replaceAll("\\{" + i + "\\}", replace[i]);
			}
		}
	}

	public static <T> ResultVO<T> success(){
		return new ResultVO<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getDesc());
	}

	public static <T> ResultVO<T> successMessage(String msg){
		return new ResultVO<>(ResultCodeEnum.SUCCESS.getCode(),msg);
	}

	public static <T> ResultVO<T> success(T data){
		return new ResultVO<>(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getDesc(),data);
	}

	public static <T> ResultVO<T> success(String msg, T data){
		return new ResultVO<T>(ResultCodeEnum.SUCCESS.getCode(),msg,data);
	}


	public static <T> ResultVO<T> error(){
		return new ResultVO<>(ResultCodeEnum.ERROR.getCode(), ResultCodeEnum.ERROR.getDesc());
	}

	public static <T> ResultVO<T> error(String errorMessage){
		return new ResultVO<>(ResultCodeEnum.ERROR.getCode(),errorMessage);
	}

	public static <T> ResultVO<T> errorEnum(ResultCodeEnum resultCodeEnum){
		return new ResultVO<>(resultCodeEnum.getCode(), resultCodeEnum.getDesc());
	}

	public static <T> ResultVO<T> error(String errorCode, String errorMessage){
		return new ResultVO<T>(errorCode,errorMessage);
	}

	@JsonIgnore
	public boolean isSuccess(){
		return ResultCodeEnum.SUCCESS.getCode().equals(this.resultCode);
	}
}
