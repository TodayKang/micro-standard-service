package com.micro.standard.module.common.exception;

public interface ErrorCode {

	Integer getErrorCode();

	String getErrorMessage(Object... params);

}
