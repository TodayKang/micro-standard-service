package com.micro.standard.module.core.web.handler;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.micro.standard.module.common.base.BaseResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局封装response
 */
@Slf4j
@RestControllerAdvice(basePackages = { "com.micro.standard.module.core.web.controller" })
public class HandlerResponseBody implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		String className = returnType.getExecutable().getDeclaringClass().getSimpleName();
		String methodName = returnType.getExecutable().getName();

		Object returnValue = null;
		if (body instanceof String) {
			BaseResponse<String> object = BaseResponse.success(body.toString());
			returnValue = JSON.toJSONString(object);
		} else if (body instanceof BaseResponse) {
			returnValue = body;
		} else {
			returnValue = BaseResponse.success(body);
		}

		log.info("[{}.{}][ReturnValue] {}", className, methodName, JSONObject.toJSON(returnValue));

		return returnValue;
	}

}
