package uz.devops.sovchilaruzv2.service.impl;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import uz.devops.sovchilaruzv2.service.interfaces.RedisCacheService;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisCacheServiceImpl implements RedisCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public String getValueForKey(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    @Override
    public String saveValueForKey(String key, String value, int expirationTime) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, expirationTime, TimeUnit.SECONDS);
        return getValueForKey(key);
    }

    @Override
    public String saveValueForKey(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return getValueForKey(key);
    }

    @Override
    public void deleteValueForKey(String key) {
        redisTemplate.delete(key);
    }
}
