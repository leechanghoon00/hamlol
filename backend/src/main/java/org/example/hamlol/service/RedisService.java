package org.example.hamlol.service;

import java.time.Duration;

public interface RedisService {
    void setValues(String key, String value);
    void setValuesWithTimeout(String key, String value, Duration timeout);
    String getValues(String key);
    void deleteValues(String key);
}
