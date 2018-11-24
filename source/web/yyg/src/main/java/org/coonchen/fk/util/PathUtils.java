package org.coonchen.fk.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class PathUtils {
	
	public static String getResourcePath(String properties){
		try {
			return URLDecoder.decode(Thread.currentThread().getContextClassLoader().getResource(properties).getPath(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return Thread.currentThread().getContextClassLoader().getResource(properties).getPath().replace("%20", " ");
	}
}
