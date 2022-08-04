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
 *
 * @description simple memory timed cache for k-object
 * @create 2022-08-03 22:23
 */
@Component(beanName = CacheConstant.OBJECT_CACHE_BEAN)
public class MemoryObjectCache extends AbstractTimedCache<String, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryObjectCache.class);

    /**
     * memory cache
     */
    private final ConcurrentHashMap<String,TimedCacheItem<Object>> CACHE
            = new ConcurrentHashMap<>();

    private final Lock lock = new ReentrantLock();


    /**
     * can not init manully, use singleton in the ioc container
     */
    private MemoryObjectCache() {}

    @Override
    void __put(String key, TimedCacheItem<Object> item) {
        Assert.hasText(key, "Cache key must not be blank");
        Assert.notNull(item, "Cache wrapper must not be null");

        lock.lock();

        try {
            // prev is the previous value associated with key, or null if there was no mapping for key
            TimedCacheItem<Object> prev = CACHE.put(key, item);
            LOGGER.debug("cache put: [{}] -> [{}], previous v is: [{}]", key, item, prev);
        } finally {
            lock.unlock();
        }
    }

    @Override
    boolean __putIfAbsent(String key, TimedCacheItem<Object> item) {
        Assert.hasText(key, "Cache key must not be blank");
        Assert.notNull(item, "Cache wrapper must not be null");

        LOGGER.debug("Preparing to put key: [{}], value: [{}]", key, item);

        lock.lock();
        try {
            Optional<Object> valueOptinal = get(key);

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
    Optional<TimedCacheItem<Object>> __get(String key) {
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
    public LinkedHashMap<String, Object> getCacheLinkedHashMap() {
        lock.lock();
        try {
            LinkedHashMap<String, Object> res = new LinkedHashMap<>();
            CACHE.forEach((k, v) -> {
                res.put(k, v.getData());
            });

            return res;
        } finally {
            lock.unlock();
        }

    }
}