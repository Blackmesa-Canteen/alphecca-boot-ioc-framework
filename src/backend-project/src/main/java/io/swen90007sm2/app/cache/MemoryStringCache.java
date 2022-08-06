package io.swen90007sm2.app.cache;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Component;
import io.swen90007sm2.app.cache.bean.TimedCacheItem;
import io.swen90007sm2.app.cache.constant.CacheConstant;
import io.swen90007sm2.app.common.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 996Worker
 * @author johnniang https://github.com/halo-dev/halo-admin
 *
 * @description simple memory timed cache for string k-v
 * @create 2022-08-03 22:23
 */
@Component(beanName = CacheConstant.STRING_CACHE_BEAN)
public class MemoryStringCache extends AbstractTimedCache<String, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryStringCache.class);

    /**
     * memory cache
     */
    private final ConcurrentHashMap<String,TimedCacheItem<String>> CACHE
            = new ConcurrentHashMap<>();

    private final Lock lock = new ReentrantLock();


    /**
     * can not init manully, use singleton in the ioc container
     */
    private MemoryStringCache() {}

    @Override
    void __put(String key, TimedCacheItem<String> item) {
        Assert.hasText(key, "Cache key must not be blank");
        Assert.notNull(item, "Cache wrapper must not be null");

        lock.lock();

        try {
            // prev is the previous value associated with key, or null if there was no mapping for key
            TimedCacheItem<String> prev = CACHE.put(key, item);
            LOGGER.debug("cache put: [{}] -> [{}], previous v is: [{}]", key, item, prev);
        } finally {
            lock.unlock();
        }
    }

    @Override
    boolean __putIfAbsent(String key, TimedCacheItem<String> item) {
        Assert.hasText(key, "Cache key must not be blank");
        Assert.notNull(item, "Cache wrapper must not be null");

        LOGGER.debug("Preparing to put key: [{}], value: [{}]", key, item);

        lock.lock();
        try {
            Optional<String> valueOptinal = get(key);

            if (valueOptinal.isPresent()) {
                LOGGER.debug("key [{}] is already in cache, putIfAbsent Failed", key);
                return false;
            }

            __put(key, item);
            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    Optional<TimedCacheItem<String>> __get(String key) {
        Assert.hasText(key, "key need have text");

        // returns an Optional describing the given value, if non-null, otherwise returns an empty Optional.
        return Optional.ofNullable(CACHE.get(key));
    }

    @Override
    public void remove(String key) {
        Assert.hasText(key, "remove key should has text");

        lock.lock();
        try {
            CACHE.remove(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clear() {
        CACHE.clear();
    }

    /**
     * generate snapshot of current cache map
     * @return current cache map view
     */
    @Override
    public LinkedHashMap<String, String> getCacheLinkedHashMap() {
        lock.lock();
        try {
            LinkedHashMap<String, String> res = new LinkedHashMap<>();
            CACHE.forEach((k, v) -> {
                res.put(k, v.getData());
            });

            return res;
        } finally {
            lock.unlock();
        }

    }
}