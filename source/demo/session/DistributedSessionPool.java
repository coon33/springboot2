package com.anywide.dawdler.clientplug.web.session;
import javax.servlet.http.HttpServletRequest;
public class DistributedSessionPool implements SessionPool {
	DistributedSessionManager dm = DistributedSessionManager.getInstance();
	@Override
	public String getSessionId(HttpServletRequest request) {
		return (String)request.getAttribute(IdentityInterceptor.cookieName);
	}

	@Override
	public Object getAttribute(HttpServletRequest request, String attributeName) {
		return dm.getAttribute(getSessionId(request), attributeName,null);
	}

	@Override
	public void setAttribute(HttpServletRequest request, String attributeName, Object object) {
		dm.setAttributeNotify(getSessionId(request), attributeName, object);
	}

	@Override
	public void removeAttribute(HttpServletRequest request, String attributeName) {
		dm.removeAttribute(getSessionId(request), attributeName);
	}

	@Override
	public <T> T getAttribute(HttpServletRequest request, String attributeName, Class<T> type) {
		return dm.getAttribute(getSessionId(request), attributeName, type);
	}

	@Override
	public void removeSession(HttpServletRequest request) {
		dm.removeSession(getSessionId(request),true);
	} 
		 
}