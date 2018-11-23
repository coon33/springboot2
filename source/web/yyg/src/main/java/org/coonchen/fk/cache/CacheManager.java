package org.coonchen.fk.cache;

/**
 * @ClassName CacheManager
 * @Description 缓存工具
 * @Author coonchen
 * @Date 2018/11/23 0023
 */
public class CacheManager {

	private static CacheManager cacheManager = new CacheManager();
	
	public static CacheManager getInstance(){
		return cacheManager;
	}
	

}
