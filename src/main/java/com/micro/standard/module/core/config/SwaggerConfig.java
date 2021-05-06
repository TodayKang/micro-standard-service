package com.micro.standard.module.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@EnableOpenApi
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

	@Bean
	public Docket docket() {
		Docket docket = new Docket(DocumentationType.OAS_30)//
				.apiInfo(apiInfo()).enable(Boolean.TRUE)//
				.select() //
				// apis： 添加swagger接口提取范围
				.apis(RequestHandlerSelectors.basePackage("com.micro.standard.module.core.web"))
				// .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())//
				.build();

		return docket;
	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfoBuilder().title("XX项目接口文档").description("XX项目描述")
				.contact(new Contact("作者", "作者URL", "作者Email")).version("1.0").build();

		apiInfo = ApiInfo.DEFAULT; // 默认

		return apiInfo;
	}

}
