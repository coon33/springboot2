package com.anywide.dawdler.clientplug.web.session;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;
public class Ehcahe {
	private static Cache<String,String> cache = null;
	static {
		URL myUrl = Ehcahe.class.getResource("ehcache.xml"); 
		Configuration xmlConfig = new XmlConfiguration(myUrl); 
		CacheManager myCacheManager = CacheManagerBuilder.newCacheManager(xmlConfig); 
		myCacheManager.init();
		cache = myCacheManager.getCache("foo",String.class,String.class);
	}
	public static void main(String[] args) throws InterruptedException {
		put("demo", "hello");
		Map map = new HashMap<>();
		long t1 = System.currentTimeMillis();
			for(int i=0;i<100;i++) {
				map.put("demo"+i, "hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello");
				put("demo"+i, "hello"+i);
			}
		long t2 = System.currentTimeMillis();
		System.out.println(t2-t1);
		for(int i=0;i<100;i++) {
//			map.get("demo"+i);
			System.out.println(get("demo"+i));
		}
		System.out.println(get("demo19"));
		long t3 = System.currentTimeMillis();
		System.out.println(t3-t2);
		Thread.sleep(100000);
//		cache.remove("demo");
//		boolean  b = cache.remove("demo", "hello1");
//		System.out.println(b);
//		System.out.println(get("demo"));
//		Thread.sleep(2000);
//		System.out.println(get("demo"));
//		Thread.sleep(4000);
//		System.out.println(get("demo"));
//		Thread.sleep(6000);
//		System.out.println(get("demo"));
		
//		  PersistentCacheManager persistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
//			        .with(CacheManagerBuilder.persistence(getStoragePath() + File.separator + "myData")) 
//			        .withCache("threeTieredCache",
//			            CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, String.class,
//			                ResourcePoolsBuilder.newResourcePoolsBuilder()
//			                    .heap(10, EntryUnit.ENTRIES) //堆
//			                    .offheap(1,MemoryUnit.MB)    //堆外
//			                    .disk(20, MemoryUnit.GB)      //磁盘
//			                    ).withExpiry(org.ehcache.expiry.Expirations.timeToIdleExpiration(new Duration(3, TimeUnit.DAYS)))
//			        ).build(true);
//			        Cache<Integer, String> threeTieredCache = persistentCacheManager.getCache("threeTieredCache", Integer.class, String.class);
	}
	public  static void put(String key,String value) {
		cache.put(key, value);
	} 
	public  static String get(String key) {
		return cache.get(key);
	} 
	public  static void remove(String key) {
		cache.remove(key);;
	} 
	private static String getStoragePath() {
        // TODO Auto-generated method stub
        return "/Users/jackson.song/Desktop/logs";
    }
}
