package com.micro.standard.module.core.web.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@FeignClient(name = "gat-service", url = "${host.gat-service}")
public interface GATFeign {

	@RequestMapping(value = "/pc/doLogin.action", method = RequestMethod.POST)
	<T> JSONObject doLogin(@RequestBody Map<String, T> req);

	@RequestMapping(value = "/forgetPwd/step1/submit.action", method = RequestMethod.POST)
	<T> JSONObject forgetPwd(@RequestBody Map<String, T> req);

}
