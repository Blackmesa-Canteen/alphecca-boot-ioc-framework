package io.swen90007sm2.app.cache;

import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * cache helper, helpful in auth
 * @param <K> key
 * @param <V> value
 *
 * @author xiaotian
 */
public interface ICacheStorage<K, V> {

    /**
     * get value based on key.
     *
     * Optional is a wrapper, wraps the data that can be null.
     * @param key cache key must not be null
     * @return val
     */
    Optional<V> get(K key);

    /**
     * put a cache without time expired
     * @param key key must not be null
     * @param value v
     */
    void put(K key, V value);

    /**
     * put a cache that have time limit
     * @param key k not null
     * @param value v
     * @param timeout expiration time
     * @param timeUnit time unit
     */
    void put(K key, V value, long timeout, TimeUnit timeUnit);

    /**
     * true if the key is absent and the value is set, false if the key is present
     * before and do nothing
     * @param key k not null
     * @param value v
     * @return whether putted the data
     */
    boolean putIfAbsent(K key, V value);

    /**
     * true if the key is absent and the value is set, false if the key is present
     *      * before and do nothing
     * @param key k not null
     * @param value v
     * @param timeout expiration time
     * @param timeUnit timue unit
     * @return whether putted the data
     */
    boolean putIfAbsent(K key, V value, long timeout, TimeUnit timeUnit);

    /**
     * remove a key from cache
     * @param key must not be null
     */
    void remove(K key);

    void clear();

    /**
     * convert cache into a linkedHashMap
     * @return LinkedHashMap
     */
    LinkedHashMap<K, V> getCacheLinkedHashMap();
}
