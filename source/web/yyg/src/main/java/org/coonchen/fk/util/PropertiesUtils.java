package org.coonchen.fk.util;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class PropertiesUtils {
	
	public static String STR_FILENAME_CONFIG = "config";
	private static Map<String,Properties> mapPros = new ConcurrentHashMap<>();
	
	public static Properties getProperties(String profile) {
		Properties pros = mapPros.get(profile);
		if(pros!=null) return pros;
		
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(PathUtils.getResourcePath(profile+".properties"));
		} catch (FileNotFoundException e1) {
			fin=null;
		}
		Properties properties = new Properties();
		if(fin!=null)
			try {
				properties.load(fin);
				mapPros.put(profile, properties);
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if(fin!=null)
					try {
						fin.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		return properties;
	}
	
	public static String getPropertiesValueByName(String proname) {
		return getPropertiesValueByName(STR_FILENAME_CONFIG,proname);
	}
	public static String getPropertiesValueByName(String profile,String proname) {
		Properties properties = getProperties(profile);
		if(properties!=null && !properties.isEmpty()) {
			String proValue = properties.getProperty(proname, "");
			properties.clear();
			return proValue;
		}
		return "";
	}
	
	public static Map<String,String> getPropertiesGroupByTag(String groupname){
		return getPropertiesGroupByTag(STR_FILENAME_CONFIG,groupname);
	}
	public static Map<String,String> getPropertiesGroupByTag(String profile,String groupname){
		Properties properties = getProperties(profile);
		Map<String,String> mapPropery = null;
		if(properties!=null && !properties.isEmpty()) {
			Enumeration<?> propertyNames = properties.propertyNames();
			
			while(propertyNames.hasMoreElements()) {
				String proName = propertyNames.nextElement().toString();
				if(proName.startsWith(groupname)) {
					if(mapPropery==null) mapPropery = new HashMap<String,String>();
					mapPropery.put(proName, properties.getProperty(proName, ""));
				}
			}
			properties.clear();
		}
		return mapPropery;
	}
}
