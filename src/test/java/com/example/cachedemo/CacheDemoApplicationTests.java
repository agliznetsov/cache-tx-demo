package com.example.cachedemo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CacheDemoApplicationTests {

	@Autowired
	EhcacheService demoService;

	@Test
	void ehcacheTest() {
		var key = "key";
		var oldValue = "oldValue";
		var newValue = "newValue";
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
