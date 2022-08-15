package com.example.cachedemo;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Transactional
public class InfinispanService implements DemoService {

	@Autowired
	private org.infinispan.Cache cache;


	public String getValue(String key) {
		return (String) cache.get(key);
	}

	public void setValue(String key, String value) {
		cache.put(key, value);
	}

	public void setValueWithError(String key, String value) {
		cache.put(key, value);
		throw new RuntimeException("error");
	}

}
