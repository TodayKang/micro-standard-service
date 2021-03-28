package com.micro.standard.module.core.web.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.micro.standard.module.common.base.BaseResponse;
import com.micro.standard.module.common.exception.BusinessException;
import com.micro.standard.module.common.exception.ErrorCodeEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局处理异常（不需要做任何配置）
 */
@Slf4j
@RestControllerAdvice
public class HandlerException {

	// Exception
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler({ Exception.class })
	public <T> BaseResponse<T> handleException(Exception e) {
		log.error("handleException:", e);
		BaseResponse<T> resp = new BaseResponse<>();
		resp.setCode(ErrorCodeEnum.SYS_RUNTIME_ERROR.getErrorCode());
		resp.setMessage(ErrorCodeEnum.SYS_RUNTIME_ERROR.getErrorMessage(e.getMessage()));
		return resp;
	}

	// BusinessException
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler({ BusinessException.class })
	public <T> BaseResponse<T> handleBusinessException(BusinessException e) {
		log.error("handleBusinessException:", e);
		BaseResponse<T> resp = new BaseResponse<>();
		resp.setCode(e.getErrorCode());
		resp.setMessage(e.getErrorMessage());
		return resp;
	}

	// IllegalArgumentException
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler({ IllegalArgumentException.class })
	public <T> BaseResponse<T> handleIllegalArgumentException(IllegalArgumentException e) {
		log.error("handleIllegalArgumentException:", e);
		BaseResponse<T> resp = new BaseResponse<>();
		resp.setCode(ErrorCodeEnum.CMN_ILLEGAL_ARG.getErrorCode());
		resp.setMessage(ErrorCodeEnum.CMN_ILLEGAL_ARG.getErrorMessage(e.getMessage()));
		return resp;
	}

	// Request method not supported
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	public <T> BaseResponse<T> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error("handleHttpRequestMethodNotSupportedException:", e);
		BaseResponse<T> resp = new BaseResponse<>();
		resp.setCode(ErrorCodeEnum.CMN_ILLEGAL_OPERATION_ERR.getErrorCode());
		resp.setMessage(e.getMessage());
		return resp;
	}

	// Required request body is missing
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler({ HttpMessageNotReadableException.class })
	public <T> BaseResponse<T> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		log.error("handleHttpMessageNotReadableException:", e);
		BaseResponse<T> resp = new BaseResponse<>();
		resp.setCode(ErrorCodeEnum.CMN_ILLEGAL_OPERATION_ERR.getErrorCode());
		resp.setMessage("Required request body is missing");
		return resp;
	}

}
