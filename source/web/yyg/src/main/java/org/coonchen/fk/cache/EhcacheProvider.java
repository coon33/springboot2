package org.coonchen.fk.cache;



import org.coonchen.fk.constant.Config;
import org.coonchen.fk.log.LogFactory;
import org.coonchen.fk.util.PropertiesUtils;
import org.coonchen.fk.util.StringUtils;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.ehcache.CacheManager;

import java.net.URL;

/**
 * @ClassName EhcacheProvider
 * @Description ehcache
 * @Author chenwei
 * @Date 2018/11/23
 */
public class EhcacheProvider {
	
	private static EhcacheProvider ehcacheProvider = new EhcacheProvider();
	private static CacheManager myCacheManager = null;
	private static LogFactory logFactory = LogFactory.getInstance(EhcacheProvider.class);
	
	
	public static EhcacheProvider getInstance(){
		if(myCacheManager==null) init();
		return ehcacheProvider;
	}

	public static void init(){
		//取得ehcache的配置文件路径
		String ehcachePath = PropertiesUtils.getPropertiesValueByName(Config.ehcache_config_path);
		if(StringUtils.isEmpty(ehcachePath))
			logFactory.error("ehcache的配置文件路径未找到");
		
		final URL myUrl = EhcacheProvider.class.getResource(ehcachePath);
		XmlConfiguration xmlConfig = new XmlConfiguration(myUrl);
		myCacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
		myCacheManager.init();
	}
	
	

}
