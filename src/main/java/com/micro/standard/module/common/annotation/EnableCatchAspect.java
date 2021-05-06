package com.micro.standard.module.common.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.micro.standard.module.common.base.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class EnableCatchAspect {

	@Pointcut("@annotation(com.micro.standard.module.common.annotation.EnableCatch)")
	public void aspect() {
	}

	@Around("aspect()")
	public Object doAround(ProceedingJoinPoint point) throws Throwable {
		Object returnValue = null;

		try {
			returnValue = point.proceed(point.getArgs());
		} catch (Throwable e) {
			log.info("EnableCatchAspect Exception:", e);
			returnValue = BaseResponse.exception(e);
		}

		return returnValue;
	}

}
