package io.swen90007sm2.app.cache;

import io.swen90007sm2.app.cache.bean.TimedCacheItem;
import io.swen90007sm2.app.common.util.Assert;
import io.swen90007sm2.app.common.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author 996Worker
 * @description simple timed cache abstract class, introduce TimedCacheItem into the ICacheStorage interface
 * @create 2022-08-03 20:43
 */
public abstract class AbstractTimedCache<K, V> implements ICacheStorage<K, V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTimedCache.class);

    abstract void __put(K key, TimedCacheItem<V> item);

    abstract boolean __putIfAbsent(K key, TimedCacheItem<V> item);

    abstract Optional<TimedCacheItem<V>> __get(K key);

    @Override
    public Optional<V> get(K key) {
        Assert.notNull(key, "cache key must not null.");

        // check prev key existence, and check expiration
        return __get(key).map(item -> {
            if (item.getExpiresAt() != null && item.getExpiresAt().before(TimeUtil.now())) {
                // if the item is expired
                LOGGER.warn("cache key:[{}] expired. ", key);
                remove(key);
                return null;
            }

            // if not expired, give out result
            return item.getData();
        });
    }

    @Override
    public void put(K key, V value) {
        __put(key, buildCacheItem(value, 0, null));
    }

    @Override
    public void put(K key, V value, long timeout, TimeUnit timeUnit) {
        __put(key, buildCacheItem(value, timeout, timeUnit));
    }

    @Override
    public boolean putIfAbsent(K key, V value) {
        return __putIfAbsent(key, buildCacheItem(value, 0, null));
    }

    @Override
    public boolean putIfAbsent(K key, V value, long timeout, TimeUnit timeUnit) {
        return __putIfAbsent(key, buildCacheItem(value, timeout, timeUnit));
    }


    /**
     * build up time cache item
     * @param value cache v
     * @param period expiration period
     * @param timeUnit time unit
     * @return timedCacheItem
     */
    private TimedCacheItem<V> buildCacheItem(V value, long period, TimeUnit timeUnit) {
        Assert.notNull(value, "cache value can not be null");
        Assert.isTrue(period >= 0, "expiration period should be positive.");

        Date now = TimeUtil.now();
        Date expireAt = null;
        if (timeUnit != null && period > 0) {
            expireAt = TimeUtil.add(now, period, timeUnit);
        }

        return new TimedCacheItem<>(
                value,
                expireAt,
                now
        );
    }
}