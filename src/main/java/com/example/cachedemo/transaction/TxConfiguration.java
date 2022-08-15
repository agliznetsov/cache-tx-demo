package com.example.cachedemo.transaction;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TxConfiguration {

	@Bean
	@Primary
	public PlatformTransactionManager chainedTransactionManager(EhcacheTransactionManager ehcacheTransactionManager, JpaTransactionManager jpaTransactionManager) {
		return new ChainedTransactionManager(ehcacheTransactionManager, jpaTransactionManager);
	}

	@Bean
	public JpaTransactionManager jpaTransactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

}
