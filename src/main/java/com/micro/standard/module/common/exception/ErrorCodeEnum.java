package com.micro.standard.module.common.exception;

import java.text.MessageFormat;

/**
 * 参考 HTTP 请求返回的状态码<br>
 * 100 ~ 199: 服务器接收到请求，需要请求者继续执行操作<br>
 * 200 ~ 299: 成功，操作被成功接收并处理 <br>
 * 300 ~ 399: 重定向，需要进一步的操作以完成请求 <br>
 * 400 ~ 499: 客户端错误，请求包含语法错误或无法完成请求<br>
 * 500 ~ 599: 服务的错误，服务器在处理请求的过程中发生了错误 <br>
 */
public enum ErrorCodeEnum implements ErrorCode {

	SUCCESS(200, "成功"), //
	CMN_INTER_ERR(500, "系统繁忙，请稍后再试"), //
	SYS_RUNTIME_ERROR(501, "系统运行出错，{0}"), //
	CMN_ILLEGAL_ARG(400, "参数不合法，{0}"), //
	CMN_ILLEGAL_OPERATION_ERR(401, "操作不合法，{0}"), //
	CMN_BLANK_ERROR(100, "{0}"), //
	;

	ErrorCodeEnum(Integer errorCode, String errorPattern) {
		this.errorCode = errorCode;
		this.errorPattern = errorPattern;
	}

	/**
	 * 错误码
	 */
	private Integer errorCode;

	/**
	 * 错误信息
	 */
	private String errorPattern;

	@Override
	public Integer getErrorCode() {
		return errorCode;
	}

	@Override
	public String getErrorMessage(Object... params) {
		String errorMessage = null;
		if (params == null || params.length == 0) {
			return errorPattern;
		}

		MessageFormat messageFormat = new MessageFormat(errorPattern);
		errorMessage = messageFormat.format(params);

		return errorMessage;
	}

}
