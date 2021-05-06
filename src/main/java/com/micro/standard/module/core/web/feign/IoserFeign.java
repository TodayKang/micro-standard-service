package com.micro.standard.module.core.web.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

/**
 * 接口文档参照 {@link https://api.ioser.net/}
 *
 */

@RestController
@FeignClient(name = "ioser", url = "${host.ioser}")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public interface IoserFeign {

	/**
	 * 同一个AccessToken暂定100次/分钟，500次/小时，5000次/天
	 * 
	 * @return {"code":200,"message":"ok","data":{"access_token":"hGfX18qUU8gFOmX1leAOYbE2eNetXf0t"}}
	 */
	@RequestMapping(value = "/api/get_access_token", method = RequestMethod.GET)
	JSONObject getAccessToken();

	@RequestMapping(value = "/api/get_phone_info", method = RequestMethod.GET)
	JSONObject getPhoneInfo(@RequestParam(required = true, value = "access_token") String accessToken,
			@RequestParam(required = true, value = "phone") String phone);

	@RequestMapping(value = "/api/get_ip_info", method = RequestMethod.GET)
	JSONObject getIPInfo(@RequestParam(required = true, value = "access_token") String accessToken,
			@RequestParam(required = true, value = "ip") String ip);

	/**
	 * 
	 * @param accessToken
	 * @param image       图片base64编码
	 * @param cardSide
	 * @return
	 */
	@RequestMapping(value = "/api/get_idcard_info", method = RequestMethod.GET)
	JSONObject getIdCardInfo(@RequestParam(required = true, value = "access_token") String accessToken,
			@RequestParam(required = true, value = "image") String image,
			@RequestParam(required = true, value = "card_side", defaultValue = "front") String cardSide);

}
