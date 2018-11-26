package com.anywide.dawdler.clientplug.web.session;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anywide.dawdler.clientplug.web.TransactionController;
import com.anywide.dawdler.clientplug.web.interceptor.HandlerInterceptor;
import com.anywide.dawdler.clientplug.web.util.DawdlerClientTool;
import com.anywide.dawdler.core.annotation.Order;
import com.anywide.dawdler.util.DawdlerTool;
@Order(1)
public class IdentityInterceptor implements HandlerInterceptor{
	static String cookieName;
	private static String domain;
	private static String path;
	private static boolean secure;
	static {
		String filePath = DawdlerTool.getcurrentPath()+"identityConfig.properties";
		Properties ps = new Properties();
 		InputStream inStream = null;
        try { 
        		inStream = new FileInputStream(filePath);
 			ps.load(inStream);
 			domain = ps.get("domain").toString();
 			path = ps.get("path").toString();
 			cookieName = ps.get("cookieName").toString();
 			if( ps.get("secure")!=null) {
 				secure = Boolean.parseBoolean(ps.get("secure").toString());
 			}
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
 			if(inStream!=null){
 				try {
 					inStream.close();
 				} catch (IOException e) {
 					e.printStackTrace();
 				}
 			}
    }
    
	}
	private static StandardSessionIdGenerator generator = new StandardSessionIdGenerator();
	private static DistributedSessionManager distributedSessionManager = DistributedSessionManager.getInstance();
	@Override
	public void afterCompletion(TransactionController arg0, Throwable arg1) {
	}

	@Override
	public void postHandle(TransactionController arg0, Throwable arg1) throws Exception {
	}
	
	public boolean preHandleCustomer(TransactionController arg0) throws Exception {
		HttpServletRequest request = arg0.getRequest();
		HttpServletResponse response = arg0.getResponse();
		String sessionkey = getCookieValue(request.getCookies(), cookieName);
		if(sessionkey==null) {
			addCookie(request, response);
		}else {
			boolean exist = distributedSessionManager.existSession(sessionkey);
			if(!exist) 
				addCookie(request, response);
			else {
				request.setAttribute(cookieName, sessionkey);
			}
		}
		return true;
	}
	@Override
	public boolean preHandle(TransactionController arg0) throws Exception {
		return true;
	}
	private String addCookie(HttpServletRequest request,HttpServletResponse response) {
		String sessionkey = generator.generateSessionId();
		CookieManager.setCookie(request, response, cookieName,sessionkey, domain, path, -1, null, -1,secure,true);
		distributedSessionManager.addSession(sessionkey,DawdlerClientTool.get_onlineip(request));
		request.setAttribute(cookieName, sessionkey);
		return sessionkey;
	}
	
	public static String getCookieValue(Cookie[] cookies,String cookiename){
	 	if(cookies==null)return null;
		try {
			for(int i=0;i<cookies.length;i++){
				if(cookies[i].getName().equals(cookiename)){
					return URLDecoder.decode( cookies[i].getValue().trim(),"utf-8");
				}
			}
		} catch (Exception e) {return null;}
		return null;
	}
}
