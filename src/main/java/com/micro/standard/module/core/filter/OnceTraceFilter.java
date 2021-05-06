package com.micro.standard.module.core.filter;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import com.micro.standard.module.common.share.Constants;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.URLDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter(urlPatterns = { "/v1/*" })
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class OnceTraceFilter extends OncePerRequestFilter {

	@Resource
	private Snowflake snowflake;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		long start = System.currentTimeMillis();

		String traceId = Long.toHexString(snowflake.nextId());
		MDC.put(Constants.TRACE_ID, traceId);

		StringBuffer requestURL = request.getRequestURL();
		String queryString = request.getQueryString();
		if (StringUtils.isNotBlank(queryString)) {
			queryString = URLDecoder.decode(queryString, Charset.defaultCharset());
			requestURL.append("?").append(queryString);
		}

		// 打印 URL
		log.info("[request url][{}] {}", request.getMethod(), requestURL.toString());

		filterChain.doFilter(request, response);

		log.info("[{}] [CostTime] {} ms", request.getServletPath(), System.currentTimeMillis() - start);
	}

	@Override
	public void destroy() {
		MDC.clear();
	}

}