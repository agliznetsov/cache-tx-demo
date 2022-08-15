package com.example.cachedemo;

import javax.cache.Caching;

import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.jcache.embedded.JCacheManager;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.transaction.LockingMode;
import org.infinispan.transaction.TransactionMode;
import org.infinispan.transaction.lookup.EmbeddedTransactionManagerLookup;
import org.infinispan.transaction.tm.EmbeddedTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.cachedemo.transaction.EhcacheTransactionManager;
import com.example.cachedemo.transaction.InfinispanTransactionManager;

import net.sf.ehcache.Cache;
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
	public Cache ehCache(CacheManager ehCacheManager) {
		ehCacheManager.addCache("demo");
		return ehCacheManager.getCache("demo");
	}

	@Bean
	public EhcacheTransactionManager ehcacheTransactionManager(CacheManager ehCacheManager) {
		return new EhcacheTransactionManager(ehCacheManager.getTransactionController());
	}


	@Bean
	public DefaultCacheManager infinispanCacheManager() {
		return new DefaultCacheManager();
	}

	@Bean
	public InfinispanTransactionManager infinispanTransactionManager() {
		return new InfinispanTransactionManager(EmbeddedTransactionManager.getInstance());
	}

	@Bean
	public javax.cache.Cache<Object,Object> infinispanCache(DefaultCacheManager infinispanCacheManager) {
		infinispanCacheManager.defineConfiguration("demo", new ConfigurationBuilder()
				.transaction()
				.autoCommit(false)
				.transactionMode(TransactionMode.TRANSACTIONAL)
				.lockingMode(LockingMode.PESSIMISTIC)
				.transactionManagerLookup(new EmbeddedTransactionManagerLookup())
				.build());

		var cache = infinispanCacheManager.getCache("demo");

		var provider = Caching.getCachingProvider("org.infinispan.jcache.embedded.JCachingProvider");
		var manager = (JCacheManager)provider.getCacheManager();
		var jcache = manager.getOrCreateCache("demo", cache);

		return jcache;
	}
}
