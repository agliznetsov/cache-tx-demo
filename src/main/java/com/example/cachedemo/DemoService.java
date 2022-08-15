package com.example.cachedemo;

public interface DemoService {

	String getValue(String key);

	void setValue(String key, String value);

	void setValueWithError(String key, String value);

}
