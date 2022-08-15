package com.example.cachedemo;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Component
@Transactional
public class EhcacheService {

	private Cache cache;

	public EhcacheService(CacheManager txCacheManager) {
		txCacheManager.addCache("demo");
		cache = txCacheManager.getCache("demo");
	}

	public String getValue(String key) {
		var element = cache.get(key);
		return element == null ? null : (String) element.getObjectValue();
	}

	public void setValue(String key, String value) {
		cache.put(new Element(key, value));
	}

	public void setValueWithError(String key, String value) {
		cache.put(new Element(key, value));
		throw new RuntimeException("error");
	}

}
