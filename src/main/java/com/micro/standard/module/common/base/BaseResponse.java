package com.micro.standard.module.common.base;

import java.io.Serializable;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import com.micro.standard.module.common.exception.BusinessException;
import com.micro.standard.module.common.exception.ErrorCodeEnum;

import lombok.Data;

@Data
public class BaseResponse<T> implements Serializable {
	private static final long serialVersionUID = 8980301999927705525L;

	private final long timestamp = System.currentTimeMillis();

	/**
	 * 状态码
	 */
	private Integer code;

	/**
	 * 状态信息
	 */
	private String message;

	/**
	 * 返回值
	 */
	private T result;

	public static <T> BaseResponse<T> success() {
		BaseResponse<T> response = new BaseResponse<T>();
		response.setCode(ErrorCodeEnum.SUCCESS.getErrorCode());
		return response;
	}

	public static <T> BaseResponse<T> success(T result) {
		BaseResponse<T> response = new BaseResponse<T>();
		response.setCode(ErrorCodeEnum.SUCCESS.getErrorCode());
		response.setResult(result);
		return response;
	}

	public static <T> BaseResponse<T> success(ErrorCodeEnum codeEnum, Object... params) {
		BaseResponse<T> response = new BaseResponse<T>();
		response.setCode(codeEnum.getErrorCode());
		response.setMessage(codeEnum.getErrorMessage(params));
		return response;
	}

	public static <T> BaseResponse<T> error(ErrorCodeEnum codeEnum, Object... params) {
		BaseResponse<T> response = new BaseResponse<T>();
		response.setCode(codeEnum.getErrorCode());
		response.setMessage(codeEnum.getErrorMessage(params));
		return response;
	}

	public static <T> BaseResponse<T> exception(Throwable t) {
		BaseResponse<T> resp = new BaseResponse<T>();

		if (t instanceof BusinessException) {
			BusinessException e = (BusinessException) t;
			resp.setCode(e.getErrorCode());
			resp.setMessage(e.getErrorMessage());
		} else if (t instanceof IllegalArgumentException) {
			resp.setCode(ErrorCodeEnum.CMN_ILLEGAL_ARG.getErrorCode());
			resp.setMessage(ErrorCodeEnum.CMN_ILLEGAL_ARG.getErrorMessage(t.getMessage()));
		} else if (t instanceof HttpRequestMethodNotSupportedException
				|| t instanceof HttpMessageNotReadableException) {
			resp.setCode(ErrorCodeEnum.CMN_ILLEGAL_OPERATION_ERR.getErrorCode());
			resp.setMessage(t.getMessage());
		} else {
			resp.setCode(ErrorCodeEnum.SYS_RUNTIME_ERROR.getErrorCode());
			resp.setMessage(ErrorCodeEnum.SYS_RUNTIME_ERROR.getErrorMessage(t.getMessage()));
			return resp;
		}

		return resp;
	}

}
