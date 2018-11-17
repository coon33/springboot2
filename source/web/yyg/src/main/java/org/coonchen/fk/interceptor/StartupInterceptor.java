package org.coonchen.fk.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.coonchen.fk.validator.ValidatorInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
* @ClassName: StartupInterceptor 
* @Description: 拦截器启动
* @author coonchen
* @date 2018年9月29日 下午1:16:33 
 */
public class StartupInterceptor extends InterceptorExecutor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(!super.preHandle(request, response, handler)) return false;
		return ValidatorInterceptor.validator(request,response,handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}
}
