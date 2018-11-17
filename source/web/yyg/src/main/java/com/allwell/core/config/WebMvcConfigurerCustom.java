package com.allwell.core.config;

import org.coonchen.fk.interceptor.StartupInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
* @ClassName: WebMvcConfigurerCustom 
* @Description: 自定义webmvc设置
* @author coonchen
* @date 2018年9月28日 下午9:26:52 
 */
@Configuration
public class WebMvcConfigurerCustom implements  WebMvcConfigurer  {
	private String[] filter = {"/**.do","/**.html","/**/**.do","/**/**.html","/**/**/**.do","/**/**/**.html"};

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new StartupInterceptor()).addPathPatterns(filter);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}
}
