package org.coonchen.fk.util;

/**
 * @ClassName StringUtils
 * @Description 字符串工具类
 * @Author coonchen
 * @Date 2018/11/23 0023
 */
public class StringUtils {
	
	
	public static boolean isEmpty(Object obj){
		return (obj==null || "".equals(obj));
	}
	
	public static boolean isNotEmpty(Object obj){
		return !(obj==null || "".equals(obj));
	}
}
