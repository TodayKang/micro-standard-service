package com.micro.standard.module.core.web.controller;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Resource;

import com.micro.standard.module.core.web.feign.BtstuFeign;
import com.micro.standard.module.core.web.feign.IoserFeign;
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
import com.micro.standard.module.common.annotation.EnableLog;
import com.micro.standard.module.common.exception.BusinessException;
import com.micro.standard.module.common.exception.ErrorCodeEnum;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/v1/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class WebController {

	@Resource
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Resource
	private ThreadPoolTaskExecutor threadPoolFutureExecutor;

	@Resource
	private BtstuFeign btstuFeign;

	@Resource
	private IoserFeign ioserFeign;

	@Resource
	private Snowflake snowflake;

	@RequestMapping(method = RequestMethod.POST, value = "/testPost")
	public JSONObject testPost(@RequestBody(required = false) JSONObject req) {
		JSONObject object = new JSONObject();
		object.put("loginName", IdUtil.fastSimpleUUID());
		object.put("passwd", IdUtil.fastSimpleUUID());
		object.put("source", "P");

		return object;
	}

	@EnableLog
	@RequestMapping(method = RequestMethod.GET, value = "/testGet")
	public JSONObject testGet(@RequestParam(required = false, value = "loginName") String loginName,
			@RequestParam(required = false, value = "password") String password,
			@RequestParam(required = false, value = "source", defaultValue = "P") String source) {
		Preconditions.checkArgument(StringUtils.isNotBlank(loginName), "登录名为空！");
		Preconditions.checkArgument(StringUtils.isNotBlank(password), "登录密码为空！");

		JSONObject object = new JSONObject();
		object.put("loginName", loginName);
		object.put("password", password);
		object.put("source", source);

		return object;
	}

	@EnableLog
	@RequestMapping(method = RequestMethod.GET, value = "/qq/getInfo")
	public JSONObject getQQInfo(@RequestParam(value = "qq") String qq) throws Exception {
		Preconditions.checkArgument(StringUtils.isNotBlank(qq), "QQ号码不存在！");

		// 获取QQ昵称和头像
		CompletableFuture<JSONObject> task1 = CompletableFuture.supplyAsync(() -> {
			long begin = System.currentTimeMillis();
			String str = btstuFeign.getNickNameAndIconByQQ(qq);
			JSONObject object = JSONObject.parseObject(str);

			long end = System.currentTimeMillis();
			log.info("getNickNameAndIconByQQ[qq={},cost={} ms]", qq, end - begin, object);

			return object;
		}, threadPoolFutureExecutor);

		// QQ信息查询
		CompletableFuture<JSONObject> task2 = CompletableFuture.supplyAsync(() -> {
			long begin = System.currentTimeMillis();
			String str = btstuFeign.getInfoByQQ(qq);
			JSONObject object = JSONObject.parseObject(str);

			long end = System.currentTimeMillis();
			log.info("getInfoByQQ[qq={},cost={} ms]", qq, end - begin, object);
			return object;
		}, threadPoolFutureExecutor);

		JSONObject object1 = task1.get();
		JSONObject object2 = task2.get();

		if (MapUtils.isEmpty(object1) || StringUtils.isNotBlank(object1.getString("msg")) || MapUtils.isEmpty(object2)
				|| StringUtils.isNotBlank(object2.getString("msg"))) {
			throw new BusinessException(ErrorCodeEnum.CMN_INTER_ERR);
		}

		JSONObject data = new JSONObject();
		if (MapUtils.isNotEmpty(object1)) {
			data.put("iconUrl", object1.getString("imgurl"));
		}

		if (MapUtils.isNotEmpty(object2)) {
			data.put("qq", object2.getString("qq"));
			data.put("sex", object2.getString("sex"));
			data.put("nickName", object2.getString("nick"));
			data.put("sign", object2.getString("sign"));
			data.put("age", object2.getString("qAge"));
			data.put("grade", object2.getString("grade"));
		}

		return data;
	}

}
