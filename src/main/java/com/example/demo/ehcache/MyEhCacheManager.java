package com.example.demo.ehcache;

import org.apache.shiro.cache.ehcache.EhCacheManager;

public class MyEhCacheManager {

	EhCacheManager ehCacheManager = new EhCacheManager();

	public MyEhCacheManager() {
		super();
		ehCacheManager.setCacheManagerConfigFile("classpath");
		ehCacheManager.getCache("cacheName");
	}
	
	
}
