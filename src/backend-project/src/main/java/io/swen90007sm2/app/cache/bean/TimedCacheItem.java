package io.swen90007sm2.app.cache.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 996Worker
 * @description bean for cache value
 * @create 2022-08-03 21:02
 */
public class TimedCacheItem<V> implements Serializable {
    /**
     * cache data
     */
    private V data;

    /**
     * cache item expiration time
     */
    private Date expiresAt;
    /**
     * cache item create time
     */
    private Date createdAt;

    public TimedCacheItem(V data, Date expiresAt, Date createdAt) {
        this.data = data;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
    }

    public TimedCacheItem() {
    }

    public V getData() {
        return data;
    }

    public void setData(V data) {
        this.data = data;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "TimedCacheItem{" +
                "data=" + data +
                ", expiresAt=" + expiresAt +
                ", createdAt=" + createdAt +
                '}';
    }
}