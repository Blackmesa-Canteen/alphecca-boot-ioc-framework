package io.swen90007sm2.app.cache.util;

import cn.hutool.core.util.RandomUtil;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Component;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author 996Worker
 * @description utils for cache
 * @create 2022-08-15 21:13
 */
@Component
public class CacheUtil {

    @AutoInjected
    @Qualifier(name = CacheConstant.OBJECT_CACHE_BEAN)
    private static ICacheStorage<String, Object> objectCache;

    @AutoInjected
    @Qualifier(name = CacheConstant.STRING_CACHE_BEAN)
    private static ICacheStorage<String, String> stringCache;

    public static ICacheStorage<String, Object> getObjectCacheInstance() {
        return objectCache;
    }

    public static ICacheStorage<String, String> getStringCacheInstance() {
        return stringCache;
    }

    /**
     * generate key for method call
     * @param methodName method call
     * @param params inputed param
     * @return string key for cache
     */
    private static String genMethodCacheKey(String methodName, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(CacheConstant.METHOD_CACHE_PREFIX);
        sb.append(methodName);
        for (Object param : params) {
            sb.append(param.toString());
        }

        return  sb.toString();
    }

    /**
     * get a method calling result from cache
     */
    public static Object getMethodResultFromCache(String methodName, Object... params) {
        String cacheKey = genMethodCacheKey(methodName, params);

        Optional<Object> res = objectCache.get(cacheKey);
        return res.orElse(null);
    }

    /**
     * put a method call result to cache
     */
    public static void putMethodResultInCache(String methodName, Object result, long timeout, TimeUnit timeUnit, Object... params) {
        String cacheKey = genMethodCacheKey(methodName, params);
        objectCache.put(cacheKey, result, timeout, timeUnit);
    }
}