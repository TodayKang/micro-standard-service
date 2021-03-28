package com.micro.standard.module.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -2870720336701559139L;

	/**
	 * 错误状态码
	 */
	private Integer errorCode;

	/**
	 * 错误提示
	 */
	private String errorMessage;

	@Override
	public String toString() {
		return String.format("%s[%s]", errorCode, errorMessage);
	}

	public BusinessException(Integer errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public BusinessException(ErrorCode code, Object... args) {
		super(code.getErrorMessage(args));
		this.errorCode = code.getErrorCode();
		this.errorMessage = code.getErrorMessage(args);

	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		this.errorMessage = message;
	}

	public BusinessException(String message) {
		super(message);
		this.errorMessage = message;
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.errorMessage = message;
	}

}
