package com.micro.standard.module.common.annotation;

import java.util.Objects;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class EnableLogAspect {

	@Resource
	private ObjectMapper objectMapper;

	@Pointcut("@annotation(com.micro.standard.module.common.annotation.EnableLog)")
	public void aspect() {
	}

	@Around(value = "aspect()")
	public Object doAround(ProceedingJoinPoint point) throws Throwable {
		String className = point.getSignature().getDeclaringType().getSimpleName();
		String methodName = point.getSignature().getName();

//		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = attributes.getRequest();

		if (Objects.nonNull(point.getArgs()) && point.getArgs().length > 0) {
			for (Object item : point.getArgs()) {
				log.info("[{}.{}][Args] {}", className, methodName, objectMapper.writeValueAsString(item));
			}
		} else {
			log.info("[{}.{}][Args] {}", className, methodName, null);
		}

		// 异常交给下游处理（例如： HandlerException 处理）
		// 此处不统一封装response，也不打印返回值，交给HandlerResponseBody处理
		Object returnValue = null;
		try {
			returnValue = point.proceed(point.getArgs());
		} catch (Exception e) {
			throw e;
		} finally {

		}

		return returnValue;
	}

}
