package io.swen90007sm2.app.cache.constant;

public interface CacheConstant {

    String TOKEN_KEY_PREFIX = "token-for-";

    String METHOD_CACHE_PREFIX = "mc-";
    String ENTITY_USER_KEY_PREFIX = "e-user-";
    String VO_HOTEL_KEY_PREFIX = "vo-hotel-";
    String VO_ROOM_KEY_PREFIX = "vo-room-";

    String ENTITY_HOTEL_KEY_PREFIX = "e-hotel-";
    String ENTITY_ROOM_KEY_PREFIX = "e-room-";


    String ENTITY_HOTEL_AMENITY_KEY_PREFIX = "e-hotel-am-";
    String ENTITY_ROOM_AMENITY_KEY_PREFIX = "e-room-am-";
    String ENTITY_ROOM_REVIEW_KEY_PREFIX = "e-review-";
    String ENTITY_ROOM_REVIEW_RESPONSE_KEY_PREFIX = "e-review-r-";

    String ENTITY_TRANSACTION_KEY_PREFIX = "e-transaction";
    String ENTITY_ROOM_ORDER_KEY_PREFIX = "e-room";

    String CACHED_HOTEL_AMENITIES = "hotel-amenities";
    String CACHED_ROOM_AMENITIES = "room-amenities";

    String CACHED_ROOMS_FOR_HOTEL = "ca-ro-fo-ho-";
    String CACHED_AMENITIES_FOR_HOTEL = "ca-am-fo-ho-";
    String CACHED_AMENITIES_FOR_ROOM = "ca-am-fo-ro-";

    String OBJECT_CACHE_BEAN = "object-cache-bean";
    String STRING_CACHE_BEAN = "string-cache-bean";

    // normal data expiration time max is 1 Min
    // need to use random period to prevent cache avalanche
    Long CACHE_NORMAL_EXPIRATION_PERIOD_MAX = (long) (60 * 1000);
    // Hot data expiration time max is 20 Second,
    // hot data is the data being updated often
    // need to use random period to prevent cache avalanche
    Long CACHE_HOT_EXPIRATION_PERIOD_MAX = (long) (20 * 1000);

    // used for data that requires consistency, such as search results, 10 seconds
    Long CACHE_POPULAR_EXPIRATION_PERIOD_MAX = (long) (10 * 1000);

    // almost static data, like amenities, have 5 minutes expiration time
    Long CACHE_COLD_EXPIRATION_PERIOD_MAX = (long) (5 * 60 * 60 * 1000);

    Integer CLEANING_THRESHOLD = 127;
}
