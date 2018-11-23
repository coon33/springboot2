package org.coonchen.fk.util;

public class ConvertUtils {


	public static String nullToStr(Object obj){
		if(null==obj) return "";
		return obj.toString();
	}

	public static long nullToLong(Object obj){
		if(null==obj) return 0;
		return Long.parseLong(obj.toString());
	}

	public static int nullToInt(Object obj){
		if(null==obj) return 0;
		return Integer.parseInt(obj.toString());
	}

	public static void main(String[] args) {
		System.out.println(nullToStr(""));
	}
}
