package org.coonchen.fk.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.coonchen.fk.interceptor.CollectInterceptor;
import org.coonchen.fk.util.SpringUtils;

/**
* @ClassName: ContextListener 
* @Description: 框架总监听器
* @author coonchen
* @date 2018年9月29日 下午12:46:19 
 */
@WebListener
public class ContextListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		System.out.println("listener start");
		ServletContext servletContext = servletContextEvent.getServletContext();
		SpringUtils.initContext(servletContext);//初始化上下文
		CollectInterceptor.initInterceptor(servletContext);//收集拦截器
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		System.out.println("listener end");
	}

}
