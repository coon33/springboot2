package com.anywide.dawdler.clientplug.web.session;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: SessionPoolResolver.java</p>
 * <p>Description: </p>
 * <p>Copyright: anywide groups 2013-4-1</p>
 * <p>Company: anywide </p>
 * @author srchen email:jackson.song@roiland.com
 * @date 2013-4-1
 * @version 1.0
 */ 
public class SessionPoolResolver implements SessionPool{
	private  SessionPoolResolver() {
		sessionPool= new LocalSessionPool();
	}
	private static SessionPoolResolver sessionPoolResolver = new SessionPoolResolver();
	public static SessionPoolResolver getInstance(){
		return sessionPoolResolver;
	}
	private SessionPool sessionPool;

	@Override
	public String getSessionId(HttpServletRequest request) {
		return sessionPool.getSessionId(request);
	}

	@Override
	public Object getAttribute(HttpServletRequest request, String attributeName) {
		return sessionPool.getAttribute(request, attributeName);
	}

	@Override
	public void setAttribute(HttpServletRequest request, String attributeName,
			Object object) {
			sessionPool.setAttribute(request, attributeName, object);
	}

	@Override
	public void removeAttribute(HttpServletRequest request, String attributeName) {
			sessionPool.removeAttribute(request, attributeName);
	}

	@Override
	public <T> T getAttribute(HttpServletRequest request, String attributeName, Class<T> type) {
		return sessionPool.getAttribute(request, attributeName, type);
	}

	@Override
	public void removeSession(HttpServletRequest request) {
		 removeSession(request);
	}
	
}

