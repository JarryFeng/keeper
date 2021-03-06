package io.github.biezhi.keeper.core.cache.redis;

import io.github.biezhi.keeper.core.cache.Cache;
import io.github.biezhi.keeper.exception.KeeperException;
import io.github.biezhi.keeper.utils.JsonUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author biezhi
 * @date 2019-04-07
 */
public class RedisCache<V> implements Cache<String, V> {

    protected final StringRedisTemplate stringRedisTemplate;

    protected final String prefix;

    public RedisCache(StringRedisTemplate stringRedisTemplate, String prefix) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.prefix = prefix;
    }

    @Override
    public void put(String key, V value) {
        String json = JsonUtil.toJSONString(value);
        stringRedisTemplate.opsForValue().set(prefix + key, json);
    }

    @Override
    public void put(String key, V value, Duration expiresTime) {
        String json = JsonUtil.toJSONString(value);
        stringRedisTemplate.opsForValue().set(prefix + key, json);
        if (null != expiresTime && expiresTime.toMillis() > 0) {
            stringRedisTemplate.expire(prefix + key, expiresTime.toMillis(), TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public V get(String key) {
        throw new KeeperException("please override get method.");
    }

    @Override
    public boolean exists(String key) {
        if (null == key) {
            return false;
        }
        Boolean hasKey = stringRedisTemplate.hasKey(prefix + key);
        return null == hasKey ? false : hasKey;
    }

    @Override
    public boolean expire(String key) {
        if (null == key) {
            return false;
        }
        Long expire = stringRedisTemplate.getExpire(prefix + key);
        return null == expire || expire < 2;
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(prefix + key);
    }

    @Override
    public Set<String> keySet() {
        return stringRedisTemplate.keys(prefix);
    }

    @Override
    public void clear() {
        stringRedisTemplate.delete(keySet());
    }

}
