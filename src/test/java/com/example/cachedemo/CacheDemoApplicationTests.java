package com.example.cachedemo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CacheDemoApplicationTests {
	String key = "key";
	String oldValue = "oldValue";
	String newValue = "newValue";

	@Autowired
	EhcacheService ehcacheService;

	@Autowired
	InfinispanService infinispanService;

	@Test
	void ehcacheTest() {
		rollbackTest(ehcacheService);
	}

	@Test
	void infinispanTest() {
		rollbackTest(infinispanService);
	}

	private void rollbackTest(DemoService demoService) {
		demoService.setValue(key, oldValue);
		assertThat(demoService.getValue(key)).isEqualTo(oldValue);

		try {
			demoService.setValueWithError(key, newValue);
		} catch (Exception e) {
			//expected
		}
		assertThat(demoService.getValue(key)).isEqualTo(oldValue);
	}


}
