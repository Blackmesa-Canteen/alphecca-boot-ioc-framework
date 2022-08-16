package io.swen90007sm2.app.cache.constant;

public interface CacheConstant {

    String TOKEN_KEY_PREFIX = "token-for-";

    String ENTITY_KEY_PREFIX = "entity-for-";

    String OBJECT_CACHE_BEAN = "object-cache-bean";
    String STRING_CACHE_BEAN = "string-cache-bean";

    // normal data expiration time max is 3 Min
    // need to use random period to prevent cache avalanche
    Long CACHE_NORMAL_EXPIRATION_PERIOD_MAX = (long) (3 * 60 * 1000);
    // Hot data expiration time max is 30 Second,
    // hot data is the data being updated often
    // need to use random period to prevent cache avalanche
    Long CACHE_HOT_EXPIRATION_PERIOD_MAX = (long) (30 * 1000);
}
