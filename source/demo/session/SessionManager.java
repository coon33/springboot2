package com.anywide.dawdler.clientplug.web.session;
import java.net.URLDecoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class SessionManager {
	private static StandardSessionIdGenerator generator = new StandardSessionIdGenerator();
	private static DistributedSessionManager distributedSessionManager = DistributedSessionManager.getInstance();
//	private static String cookieName = "_dawdler_key";
	public static String getSessionKey(HttpServletRequest request,String sessionkey){
		return null;
	}
	public static String optionSession(HttpServletRequest request,HttpServletResponse response,String securitykey,String cookiename,String path,String domain,int maxage){
		String sessionkey = getCookieValue(request.getCookies(), cookiename);
		boolean exist = distributedSessionManager.getInstance().existSession(sessionkey);
		if(exist)return sessionkey; 
		sessionkey = generator.generateSessionId();
		CookieManager.setCookie(request, response, cookiename,sessionkey, domain, path, maxage, null, -1, false,true);
		return null;
	}
 
	public static  void addConversation(HttpServletRequest request,HttpServletResponse response,String securitykey,String cookiename,String value,String path,String domain,int maxage){
		CookieManager.setCookie(request, response, cookiename,value, domain, path, maxage, null, -1, false,true);
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
