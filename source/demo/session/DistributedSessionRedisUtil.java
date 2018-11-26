package com.anywide.dawdler.clientplug.web.session;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.anywide.dawdler.clientplug.web.util.JsonProcessUtil;
import com.anywide.dawdler.util.DawdlerTool;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool; 
import redis.clients.jedis.JedisPoolConfig;
public final class DistributedSessionRedisUtil {
    private static JedisPool jedisPool = null;
    public static  String NOTIFY_DEL="del";
    public static  String NOTIFY_SETATTRIBUTE="setAttribute";
    public static  String NOTIFY_REMOVEATTRIBUTE="removeAttribute";
    public static String NOTIFY_SYNINFO="syninfo";
    /**
     * 初始化Redis连接池
     */
    static {
    	Properties ps= new Properties();
 		String path =DawdlerTool.getcurrentPath()+"distributedSessionRedis.properties";
 		InputStream inStream = null;
        try { 
        		inStream = new FileInputStream(path);
 			ps.load(inStream);
 			String addr=ps.get("addr").toString();
 			String auth=ps.get("auth").toString();
 			int port= Integer.parseInt(ps.get("port").toString());
 			int max_idle= Integer.parseInt(ps.get("max_idle").toString());
 			int max_wait= Integer.parseInt(ps.get("max_wait").toString());
 			int timeout= Integer.parseInt(ps.get("timeout").toString());
 			boolean test_on_borrow= Boolean.parseBoolean(ps.get("test_on_borrow").toString());
 			NOTIFY_DEL= ps.get("notify_del").toString();
 			NOTIFY_SETATTRIBUTE= ps.get("notify_setattribute").toString();
 			NOTIFY_REMOVEATTRIBUTE= ps.get("notify_removeattribute").toString();
 			NOTIFY_SYNINFO = ps.get("notify_syninfo").toString();
            JedisPoolConfig config = new JedisPoolConfig();
            //config.setMaxActive(MAX_ACTIVE);
            config.setMaxIdle(max_idle);
            config.setMaxWaitMillis(max_wait);
            config.setTestOnBorrow(test_on_borrow);
            jedisPool = new JedisPool(config, addr, port, timeout,auth);
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
    
    /**
     * 获取Jedis实例
     * @return
     */
    public static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static long del(String key){
    	Jedis redis = getJedis();
    	try{
	    	long t =redis.del(key);
	    	return t;
	    }finally{
			redis.close();
		}
    	
    }
    
    public static long delNotify(String id,String channel){
    	Jedis redis = getJedis();
    	try{
    	long t =redis.del(id);
    	if(t>0){
    		redis.publish(channel,getInstruct(NOTIFY_DEL,id,null,null));
    	}
    	return t;
    	}finally{
			redis.close();
		}
    }
    public static long removeAttribute(String id,String channel,String key){
    	Jedis redis = getJedis();
    	try{
    	long t =redis.hdel(id, key);
    	if(t>0){
    		redis.publish(channel,getInstruct(NOTIFY_REMOVEATTRIBUTE,id,key,null));
    	}
    	return t;
	    } finally{
			redis.close();
		}
    	
    }
    public static void setAttribute(String id,String channel,String key,String value,boolean json){
    	Jedis redis = getJedis();
    	try {
    		redis.hdel(id,key);
	    	long t =redis.hset(id, key, value);
	    	if(t>0){
	    		redis.publish(channel,getInstruct(NOTIFY_SETATTRIBUTE,id,key,value,json));
	    	}
    	} finally{
			redis.close();
		}
    }
    
    public static void syninfo(String id,String channel,String key,String value,boolean json){
    	Jedis redis = getJedis();
    	try {
    		redis.hdel(id,key);
	    	long t =redis.hset(id, key, value);
	    	if(t>0){
	    		redis.publish(channel,getInstruct(NOTIFY_SYNINFO,id,key,value,json));
	    	}
    	} finally{
			redis.close();
		}
    }
    
    
    public static String getAttribute(String id,String key){
    	Jedis redis = getJedis();
    	try {
    		String value =redis.hget(id, key);
    		return value;
		} finally{
			redis.close();
		}
    	
    }
    public static void addSession(String id,Map map){
    	Jedis redis = getJedis();
    	try {
    		boolean exist =redis.exists(id);
    		if(!exist){
    			redis.hmset(id, map);
    		}
		} finally{
			redis.close();
		}
    }
    
    public static boolean isExist(String id){
    	Jedis redis = getJedis();
    	try {
    		boolean exist =redis.exists(id);
    		return exist; 
		} finally{
			redis.close();
		}
    }
    
    
    public static String getInstruct(String action,String id,String key,String value,boolean json){
    	Map<String,Object> map = new HashMap();
    	map.put("id",id);
    	map.put("action",action);
    	if(key!=null)
    		map.put("key", key);
    	if(value!=null)
    		map.put("value", value);
    	if(json)
    		map.put("json","true");
    	return JsonProcessUtil.beanToJson(map);
    }
    
    public static String getInstruct(String action,String id,String key,String value){
    	return getInstruct(action, id, key, value,false);
    }
     
    public static String set(String key,String value){
    	Jedis redis = getJedis();
    	String s = redis.set(key, value);
    	redis.close();
    	return s;
    }
    
    public static String get(String key){
    	Jedis redis = getJedis();
    	String s = redis.get(key);
    	redis.close();
    	return s;
    }
    
}