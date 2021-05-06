package com.micro.standard.module.core.config.task;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import com.micro.standard.module.common.share.Constants;

import cn.hutool.core.lang.Snowflake;

/**
 * 异步线程和主线程共用一个traceId
 */
public class MdcTaskDecorator implements TaskDecorator {

	@Resource
	private Snowflake snowflake;

	@Override
	public Runnable decorate(Runnable runnable) {
		Map<String, String> map = MDC.getCopyOfContextMap();

		Runnable task = () -> {
			try {
				MDC.setContextMap(map);
				String traceId = MDC.get(Constants.TRACE_ID);
				if (StringUtils.isBlank(traceId)) {
					traceId = Long.toHexString(snowflake.nextId());
					MDC.put(Constants.TRACE_ID, traceId);
				}

				runnable.run();
			} finally {
				MDC.clear();
			}
		};

		return task;
	}

}
