package org.coonchen.fk.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.coonchen.fk.util.OrderData;
import org.coonchen.fk.util.SpringUtils;
import org.springframework.core.OrderComparator;
import org.springframework.core.annotation.Order;

/**
* @ClassName: CollectInterceptor 
* @Description: 拦截器收集
* @author coonchen
* @date 2018年9月29日 下午12:17:14 
 */
public class CollectInterceptor {
	private static volatile boolean order=false;
	public static List<OrderData<InterceptorInterface>> lstHandlerInterceptorAdapter;

	public static synchronized void initInterceptor(ServletContext servletContext){
		initInterceptor();
	}
	
	public static synchronized void initInterceptor(){
		String[] handlerNames = SpringUtils.getContext().getBeanNamesForType(InterceptorInterface.class);
		if(handlerNames!=null){
			if(lstHandlerInterceptorAdapter==null) lstHandlerInterceptorAdapter = new ArrayList<OrderData<InterceptorInterface>>();
			for(String handlerName : handlerNames){
				InterceptorInterface handlerInterceptorAdapter = (InterceptorInterface) SpringUtils.getBean(handlerName);
				OrderData<InterceptorInterface> orderData = new OrderData<InterceptorInterface>();
				orderData.setData(handlerInterceptorAdapter);
				Order order = handlerInterceptorAdapter.getClass().getAnnotation(Order.class);
				if(order!=null) orderData.setOrder(order.value());
				lstHandlerInterceptorAdapter.add(orderData);
			}
		}
	}
	
	public static List<OrderData<InterceptorInterface>> getLstHandlerInterceptorAdapter(){
		if(lstHandlerInterceptorAdapter==null) return null;
		if(!order){
			OrderComparator.sort(lstHandlerInterceptorAdapter);
			order = true;
		}
		return lstHandlerInterceptorAdapter;
	}

}
