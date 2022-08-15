package com.example.cachedemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.cachedemo.transaction.EhcacheTransactionManager;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;

@Configuration
public class CacheConfig {

	@Bean
	public CacheManager ehCacheManager() {
		return CacheManager.newInstance(new net.sf.ehcache.config.Configuration()
				.defaultCache(new CacheConfiguration()
						.maxEntriesLocalHeap(100)
						.transactionalMode(CacheConfiguration.TransactionalMode.LOCAL.name())
				)
				.name("tx"));
	}

	@Bean
	public EhcacheTransactionManager ehcacheTransactionManager(CacheManager ehCacheManager) {
		return new EhcacheTransactionManager(ehCacheManager.getTransactionController());
	}

}
