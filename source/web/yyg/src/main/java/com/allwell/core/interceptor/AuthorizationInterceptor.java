package com.allwell.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.coonchen.fk.interceptor.InterceptorInterface;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.allwell.core.constant.Constant;

@Component
@Order(value=1)
public class AuthorizationInterceptor implements InterceptorInterface {

	private void setProjectUrl(HttpServletRequest request){
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
		request.setAttribute(Constant.SERVER_PATH, basePath);
		request.setAttribute(Constant.IMAGE_PATH, basePath);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		setProjectUrl(request);
		System.out.println("====================================");
		System.out.println("AuthorizationInterceptor"+"preHandle"+request.getRequestURI());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		System.out.println("AuthorizationInterceptor"+"postHandle"+request.getRequestURI());
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		System.out.println("AuthorizationInterceptor"+"afterCompletion"+request.getRequestURI());
	}
}
