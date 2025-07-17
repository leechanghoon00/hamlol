package org.example.hamlol.service;

public interface RedisService {
    void setValues(String key, String value);
    void setValuesWithTimeout(String key, String value, long timeoutMillis);
    String getValues(String key);
    void deleteValues(String key);
}
