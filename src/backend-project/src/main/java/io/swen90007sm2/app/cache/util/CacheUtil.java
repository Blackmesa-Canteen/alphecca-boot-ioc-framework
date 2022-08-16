package io.swen90007sm2.app.cache.util;

import cn.hutool.core.util.RandomUtil;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Component;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;

import java.util.Random;

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
}