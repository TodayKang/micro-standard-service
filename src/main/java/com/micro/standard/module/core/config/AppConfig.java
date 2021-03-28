package com.micro.standard.module.core.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

@Configuration
public class AppConfig {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean // 雪花算法
	public Snowflake snowflake() {
		return IdUtil.getSnowflake(1, 1);
	}

	@Bean // Jackson格式化json
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
