package com.anywide.dawdler.clientplug.web.session;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: SessionPool.java</p>
 * <p>Description: </p>
 * <p>Copyright: anywide groups 2013-4-1</p>
 * <p>Company: anywide </p>
 * @author srchen email:jackson.song@roiland.com
 * @date 2013-4-1
 * @version 1.0
 */
public interface SessionPool {
	public String getSessionId(HttpServletRequest request);
	public  Object getAttribute(HttpServletRequest request,String attributeName);
	<T> T getAttribute(HttpServletRequest request, String attributeName,Class<T> type);
	public  void setAttribute(HttpServletRequest request,String attributeName,Object object);
	public  void removeAttribute(HttpServletRequest request,String attributeName);
	public  void removeSession(HttpServletRequest request);
	
}

