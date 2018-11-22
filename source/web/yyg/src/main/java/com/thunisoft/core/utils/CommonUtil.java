package com.thunisoft.core.utils;

public class CommonUtil {
	public static int sysdateInt() {
		int addtime = (int) (System.currentTimeMillis() / 1000);
		return addtime;
	}
}
