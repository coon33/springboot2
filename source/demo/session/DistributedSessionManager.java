package com.anywide.dawdler.clientplug.web.session;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.anywide.dawdler.clientplug.web.util.JsonProcessUtil;
import com.anywide.dawdler.util.DawdlerTool;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
public class DistributedSessionManager {
	private static Logger logger = LoggerFactory.getLogger(DistributedSessionManager.class);
	private static DistributedSessionManager cacheManager  = new DistributedSessionManager();
	private static final String USERSESSIONCHANNEL="usersession";
	private Cache<String,Serializable> usersession;
	private DistributedSessionManager() {
		URL url = null;
		try {
			File file = new File(DawdlerTool.getcurrentPath()+"distributedcache.xml");
			url = file.toURI().toURL();
		} catch (MalformedURLException e) {
			logger.error("",e);
			return ;
		}
		Configuration xmlConfig = new XmlConfiguration(url); 
		CacheManager myCacheManager = CacheManagerBuilder.newCacheManager(xmlConfig); 
		myCacheManager.init();
		usersession = myCacheManager.getCache("user",String.class,Serializable.class);
	}
	static public DistributedSessionManager getInstance() {
		return cacheManager;
	}
	
	//仅当当前映射到给定值且条目未过期时，才删除键的条目。
	public boolean removeSession(String sessionkey,boolean notify) {
		boolean b = true;
		if(notify) {
			long t = DistributedSessionRedisUtil.delNotify(sessionkey, USERSESSIONCHANNEL);
			if(t>0) usersession.remove(sessionkey);
			else b = false;
		}else usersession.remove(sessionkey);
		return b;
	}

	public boolean removeSession(String sessionkey) {
		return removeSession(sessionkey,true);
	}
	
	public void removeAttribute(String sessionkey, String attribute,boolean notify) {
		Object obj = usersession.get(sessionkey);
		if (obj != null) {
			Map map = (Map) obj;
			map.remove(attribute);
		}
		if(notify){
			DistributedSessionRedisUtil.removeAttribute(sessionkey, USERSESSIONCHANNEL, attribute);
		}
	}
	
	public void removeAttribute(String sessionkey, String attribute) {
		removeAttribute(sessionkey, attribute, true);
	}

	public void setAttributeNotify(String sessionkey, String attribute, Object value) {
		Object obj = usersession.get(sessionkey);
		if (obj == null) {
			addSession(sessionkey,"unknown");
			obj = usersession.get(sessionkey);
		} 
		Map map = (Map) obj;
		map.put(attribute, value);
		if(isBaseDataType(value.getClass())){
			DistributedSessionRedisUtil.setAttribute(sessionkey, USERSESSIONCHANNEL, attribute,value.toString(),false);
		}else{
			DistributedSessionRedisUtil.setAttribute(sessionkey, USERSESSIONCHANNEL, attribute,JsonProcessUtil.beanToJson(value),true);
		}
	}
	
	public <T> T getAttribute(String sessionkey, String attribute,Class<T> type) {
		Object ele = usersession.get(sessionkey);
		if (ele != null) {
			Map map = (Map) ele;
			if (map != null)
				return (T)map.get(attribute);
		}else{
			String value = DistributedSessionRedisUtil.getAttribute(sessionkey, attribute);
			if(type==null){
				if(value!=null)setAttribute(sessionkey, attribute, value);
				return (T)value; 
			}else{
				if(value!=null){
					Object obj = JsonProcessUtil.jsonToBean(value, type);
					setAttribute(sessionkey, attribute,obj);
					if(attribute.equals("user_data")){
						try {
						HashMap<String,Object> map = JsonProcessUtil.jsonToBean(value,HashMap.class);
						putUserId(Integer.parseInt(map.get("userid").toString()), sessionkey);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					return (T)obj;
				}
			}
		}
		return null;
	}
	
	public void syninfoNotify(String sessionkey, String attribute, Object value) {
		if(isBaseDataType(value.getClass())){
			DistributedSessionRedisUtil.syninfo(sessionkey, USERSESSIONCHANNEL, attribute,value.toString(),false);
		}else{
			DistributedSessionRedisUtil.syninfo(sessionkey, USERSESSIONCHANNEL, attribute,JsonProcessUtil.beanToJson(value),true);
		}
	}
	public void addSession(String sessionkey, String ipaddress) {
		HashMap map = new HashMap();
		map.put("ipaddress",ipaddress);
		usersession.put(sessionkey, map);
		DistributedSessionRedisUtil.addSession(sessionkey, map);
	}
	public boolean existSession(String sessionkey) {
		boolean exist = usersession.containsKey(sessionkey);
		if(!exist){
			exist=DistributedSessionRedisUtil.isExist(sessionkey);
		}
		return exist;
	}
	public void setAttribute(String sessionkey, String attribute, Object value){
		Object ele = usersession.get(sessionkey);
		if (ele == null) {
			addSession(sessionkey, null);
			ele = usersession.get(sessionkey);
		} 
		Map map = (Map) ele;
		map.put(attribute, value);
	}
	public void putUserId(int userid,String key){
		String value = DistributedSessionRedisUtil.get(userid+"");
		if(value != null && !key.equals(value)){
			removeSession(value); 
		}
		DistributedSessionRedisUtil.set(userid+"", key);
	}
	
	
	
	public void removeUser(int userid){
		String key = DistributedSessionRedisUtil.get(userid+"");
		if(key!=null){
//			RedisUtil.removeAttribute(userid+"", USERONLINESCHANNEL, userid+"");
			DistributedSessionRedisUtil.del(userid+"");
			removeSession(key);
		}
	}
	public void handleResponseData(final SynInfo si){
		final Jedis jedis = DistributedSessionRedisUtil.getJedis();
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					jedis.subscribe(new ResponseDataListener(si),USERSESSIONCHANNEL);
				}
			}).start();
		}catch(Exception e){
			
		}
		
	}

	public class ResponseDataListener extends JedisPubSub {
		private SynInfo si = null;
		public ResponseDataListener(SynInfo si){
			this.si = si;
		}
    	// 取得订阅的消息后的处理
    	public void onMessage(String channel, String message) {
    		try {
    			if(channel.equals(USERSESSIONCHANNEL)){
        			Map<String,String> map = JsonProcessUtil.jsonToBean(message,HashMap.class);
        			if(map!=null){
        				String action = map.get("action");
        				String id = map.get("id");
        				String key = map.get("key");
        				String value = map.get("value");
        				Boolean json = map.containsKey("json");
        				if(action.equals(DistributedSessionRedisUtil.NOTIFY_DEL)){
        					removeSession(id, false);  
        				}else if(action.equals(DistributedSessionRedisUtil.NOTIFY_REMOVEATTRIBUTE)){
        					removeAttribute(id, key, false);
        				}else if(action.equals(DistributedSessionRedisUtil.NOTIFY_SETATTRIBUTE)){
        					if(json){ 
        						removeSession(id, false);
        					}else{
        						setAttribute(id, key, value);
        					}
        					
        				}else if(action.equals(DistributedSessionRedisUtil.NOTIFY_SYNINFO)){
        					si.synInfo(value);
        				}
        				
        			}
        		}
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
    		
//    		System.out.println("onMessage:"+channel + "=" + message);
    	}

    	// 初始化订阅时候的处理
    	public void onSubscribe(String channel, int subscribedChannels) {
//    		 System.out.println("onSubscribe:"+channel + "=" + subscribedChannels);
    	}

    	// 取消订阅时候的处理
    	public void onUnsubscribe(String channel, int subscribedChannels) {
    		 System.out.println("onUnsubscribe:"+channel + "=" + subscribedChannels);
    	}

    	// 初始化按表达式的方式订阅时候的处理
    	public void onPSubscribe(String pattern, int subscribedChannels) {
    		 System.out.println("onPSubscribe:"+pattern + "=" + subscribedChannels);
    	}

    	// 取消按表达式的方式订阅时候的处理
    	public void onPUnsubscribe(String pattern, int subscribedChannels) {
    		 System.out.println("onPUnsubscribe:"+pattern + "=" + subscribedChannels);
    	}

    	// 取得按表达式的方式订阅的消息后的处理
    	public void onPMessage(String pattern, String channel, String message) {
    		System.out.println("onPMessage:"+pattern + "=" + channel + "=" + message);
    	}
	}
	
	private static boolean isBaseDataType(Class clazz)
	 {   
	     return 
	     (   
	         clazz.equals(String.class) ||   
	         clazz.isPrimitive() ||
	         clazz.equals(Integer.class)||   
	         clazz.equals(Byte.class) ||   
	         clazz.equals(Long.class) ||   
	         clazz.equals(Double.class) ||   
	         clazz.equals(Float.class) ||   
	         clazz.equals(Character.class) ||   
	         clazz.equals(Short.class) ||   
	         clazz.equals(BigDecimal.class) ||   
	         clazz.equals(BigInteger.class) ||   
	         clazz.equals(Boolean.class) ||   
	         clazz.equals(Date.class)
	     );   
	 }
}