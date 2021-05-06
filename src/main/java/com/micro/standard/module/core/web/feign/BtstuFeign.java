package com.micro.standard.module.core.web.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 接口文档参照 {@link https://api.btstu.cn/}
 *
 */

@RestController
@FeignClient(name = "btstu", url = "${host.btstu}")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public interface BtstuFeign {

	@RequestMapping(method = RequestMethod.GET, value = "/qqxt/api.php")
	String getNickNameAndIconByQQ(@RequestParam(required = true, value = "qq") String qq);

	@RequestMapping(method = RequestMethod.GET, value = "/qqinfo/api.php")
	String getInfoByQQ(@RequestParam(required = true, value = "qq") String qq);

}
