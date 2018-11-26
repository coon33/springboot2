package com.anywide.dawdler.clientplug.web.session;
import javax.servlet.http.HttpServletRequest;
/**
 * <p>Title: LocalSessionPool.java</p>
 * <p>Description: </p>
 * <p>Copyright: anywide groups 2013-4-1</p>
 * <p>Company: anywide </p>
 * @author srchen email:jackson.song@roiland.com
 * @date 2013-4-1
 * @version 1.0
 */
public class LocalSessionPool implements SessionPool{
	public  String getSessionId(HttpServletRequest request){
		return request.getSession().getId();
	}
	public Object getAttribute(HttpServletRequest request,String attributeName){
		return request.getSession().getAttribute(attributeName);
	}
	public void setAttribute(HttpServletRequest request,String attributeName,Object object){
		request.getSession().setAttribute(attributeName,object);
	}
	public void removeAttribute(HttpServletRequest request,String attributeName){
		request.getSession().removeAttribute(attributeName);
	}
	@Override
	public <T> T getAttribute(HttpServletRequest request, String attributeName, Class<T> type) {
		return (T)getAttribute(request, attributeName);
	}
	@Override
	public void removeSession(HttpServletRequest request) {
		request.getSession().invalidate();
	}
}

