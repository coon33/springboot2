package com.anywide.dawdler.clientplug.web.session;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
public class ListenerObject<K, V> implements CacheEventListener<K, V> {

	@Override
	public void onEvent(CacheEvent<? extends K, ? extends V> arg0) {
		System.out.println(arg0.getKey()+"\t"+arg0.getType());
	}

}
