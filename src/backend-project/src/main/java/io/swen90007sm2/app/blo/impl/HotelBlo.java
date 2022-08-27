package io.swen90007sm2.app.blo.impl;

import cn.hutool.core.util.RandomUtil;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.blo.IHotelBlo;
import io.swen90007sm2.app.blo.IHotelierBlo;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;
import io.swen90007sm2.app.cache.util.CacheUtil;
import io.swen90007sm2.app.common.constant.CommonConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.common.factory.IdFactory;
import io.swen90007sm2.app.dao.IHotelAmenityDao;
import io.swen90007sm2.app.dao.IHotelDao;
import io.swen90007sm2.app.dao.IHotelierDao;
import io.swen90007sm2.app.dao.impl.HotelAmenityDao;
import io.swen90007sm2.app.dao.impl.HotelDao;
import io.swen90007sm2.app.dao.impl.HotelierDao;
import io.swen90007sm2.app.db.bean.PageBean;
import io.swen90007sm2.app.model.entity.BaseEntity;
import io.swen90007sm2.app.model.entity.Hotel;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.param.CreateHotelParam;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Blo
public class HotelBlo implements IHotelBlo {

    @AutoInjected
    IHotelierBlo hotelierBlo;

    @AutoInjected
    @Qualifier(name = CacheConstant.OBJECT_CACHE_BEAN)
    ICacheStorage<String, Object> cache;


    public void doCreateHotel(String hotelierId, CreateHotelParam createHotelParam) {

        // check existence, one hotelier can only create one hotel
        Hotelier currentHotelier = hotelierBlo.getHotelierInfoByUserId(hotelierId);
        if (StringUtils.isNotEmpty(currentHotelier.getHotelId())) {
            throw new RequestException(
                    StatusCodeEnume.HOTELIER_ALREADY_HAS_HOTEL.getMessage(),
                    StatusCodeEnume.HOTELIER_ALREADY_HAS_HOTEL.getCode()
            );
        }

        Hotel hotel = new Hotel();
        Long id = IdFactory.genSnowFlakeId();
        hotel.setId(id);
        hotel.setHotelId(id.toString());
        hotel.setName(createHotelParam.getName());
        hotel.setDescription(createHotelParam.getDescription());
        hotel.setAddress(createHotelParam.getAddress());
        hotel.setPostCode(createHotelParam.getPostCode());
        hotel.setOnSale(createHotelParam.getOnSale());

        List<String> amenity_ids = createHotelParam.getAmenityIds();
        IHotelAmenityDao amenityDao = BeanManager.getLazyBeanByClass(HotelAmenityDao.class);
        IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);
        IHotelierDao hotelierDao = BeanManager.getLazyBeanByClass(HotelierDao.class);

        // get cooresponding hotelier info
        Hotelier hotelier = hotelierBlo.getHotelierInfoByUserId(hotelierId);
        hotelier.setHotelId(hotel.getHotelId());

        synchronized (this) {
            // firstly, insert new hotel
            hotelDao.insertOne(hotel);

            // update hotelier info
            hotelierDao.updateOne(hotelier);
            cache.remove(CacheConstant.ENTITY_USER_KEY_PREFIX + hotelierId);

            // attach amnities to this hotel
            amenityDao.addAmenityIdsToHotel(
                    amenity_ids,
                    hotel.getHotelId()
            );
        }
    }

    @Override
    public Hotel getHotelInfoByHotelId(String hotelId) {
        Optional<Object> cacheItem = cache.get(CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotelId);
        Hotel hotel = null;
        if (cacheItem.isEmpty()) {
            synchronized (this) {
                cacheItem = cache.get(CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotelId);
                // if the cache is still empty
                if (cacheItem.isEmpty()) {
                    HotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);
                    hotel = hotelDao.findOneByBusinessId(hotelId);
                    // if no such
                    if (hotel == null) {
                        throw new RequestException(
                                "hotel not found",
                                StatusCodeEnume.RESOURCE_NOT_FOUND_EXCEPTION.getCode());
                    }

                    // use random expiration time to prevent Cache Avalanche
                    cache.put(
                            CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotelId,
                            hotel,
                            RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS
                    );
                } else {
                    // data is put by other thread, just get from cache.
                    hotel = (Hotel) cacheItem.get();
                }
            }

        } else {
            hotel = (Hotel) cacheItem.get();
        }

        return hotel;
    }

    @Override
    public List<Hotel> getHotelsByPageSortedByCreateTime(Integer pageNum, Integer pageSize, Integer order) {
        List<Hotel> hotels = null;
        // cache method res
        Object[] params = {pageNum, pageSize, order};
        String methodName = "getHotelsByPageSortedByCreateTime";
        Object result = CacheUtil.getMethodResultFromCache(methodName, params);
        if (result != null) {
            hotels = (List<Hotel>) result;
        } else {
            synchronized (this) {
                result = CacheUtil.getMethodResultFromCache(methodName, params);
                if (result == null) {
                    // fetch data from db
                    IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);
                    // only finds on sale hotels
                    int totalRows = hotelDao.findTotalCount(true);

                    // physic paging: use database offset to page results
                    PageBean<Hotel> pageBean = new PageBean<>(
                            pageSize,
                            totalRows,
                            pageNum
                    );

                    // calculate start row in database
                    int start = pageBean.getStartRow();

                    if (order == null) {
                        hotels = hotelDao.findAllByPageByDate(start, pageSize, CommonConstant.SORT_DOWN, true);
                    } else {
                        hotels = hotelDao.findAllByPageByDate(start, pageSize, order, true);
                    }

                    // cache method result
                    CacheUtil.putMethodResultInCache(
                            methodName,
                            hotels,
                            RandomUtil.randomLong(CacheConstant.CACHE_POPULAR_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS,
                            params
                    );

                    // cache each hotel res
                    hotels.forEach(hotel -> {
                        cache.put(
                                CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotel.getHotelId(),
                                hotel,
                                RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                                TimeUnit.MILLISECONDS
                        );
                    });

                } else {
                    hotels = (List<Hotel>) result;
                }
            }
        }

        return hotels;
    }

    @Override
    public List<Hotel> getHotelsByPageSortedByPrice(Integer pageNum, Integer pageSize, Integer order) {
        List<Hotel> hotels = null;
        // cache method res
        Object[] params = {pageNum, pageSize, order};
        String methodName = "getHotelsByPageSortedByCreateTime";
        Object result = CacheUtil.getMethodResultFromCache(methodName, params);
        if (result != null) {
            hotels = (List<Hotel>) result;
        } else {
            synchronized (this) {
                result = CacheUtil.getMethodResultFromCache(methodName, params);
                if (result == null) {
                    // fetch data from db
                    IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);
                    // only finds on sale hotels
                    int totalRows = hotelDao.findTotalCount(true);

                    // physic paging: use database offset to page results
                    PageBean<Hotel> pageBean = new PageBean<>(
                            pageSize,
                            totalRows,
                            pageNum
                    );

                    // calculate start row in database
                    int start = pageBean.getStartRow();

                    if (order == null) {
                        hotels = hotelDao.findAllByPageSortByPrice(start, pageSize, CommonConstant.SORT_DOWN, true);
                    } else {
                        hotels = hotelDao.findAllByPageSortByPrice(start, pageSize, order, true);
                    }

                    // cache method result
                    CacheUtil.putMethodResultInCache(
                            methodName,
                            hotels,
                            RandomUtil.randomLong(CacheConstant.CACHE_POPULAR_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS,
                            params
                    );

                    // cache each hotel res
                    hotels.forEach(hotel -> {
                        cache.put(
                                CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotel.getHotelId(),
                                hotel,
                                RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                                TimeUnit.MILLISECONDS
                        );
                    });

                } else {
                    hotels = (List<Hotel>) result;
                }
            }
        }

        return hotels;
    }

    @Override
    public List<Hotel> getHotelsByPageSortedByRank(Integer pageNum, Integer pageSize, Integer order) {
        List<Hotel> hotels = null;
        // cache method res
        Object[] params = {pageNum, pageSize, order};
        String methodName = "getHotelsByPageSortedByCreateTime";
        Object result = CacheUtil.getMethodResultFromCache(methodName, params);
        if (result != null) {
            hotels = (List<Hotel>) result;
        } else {
            synchronized (this) {
                result = CacheUtil.getMethodResultFromCache(methodName, params);
                if (result == null) {
                    // fetch data from db
                    IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);
                    // only finds on sale hotels
                    int totalRows = hotelDao.findTotalCount(true);

                    // physic paging: use database offset to page results
                    PageBean<Hotel> pageBean = new PageBean<>(
                            pageSize,
                            totalRows,
                            pageNum
                    );

                    // calculate start row in database
                    int start = pageBean.getStartRow();

                    if (order == null) {
                        hotels = hotelDao.findAllByPageSortByRank(start, pageSize, CommonConstant.SORT_DOWN, true);
                    } else {
                        hotels = hotelDao.findAllByPageSortByRank(start, pageSize, order, true);
                    }

                    // cache method result
                    CacheUtil.putMethodResultInCache(
                            methodName,
                            hotels,
                            RandomUtil.randomLong(CacheConstant.CACHE_POPULAR_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS,
                            params
                    );

                    // cache each hotel res
                    hotels.forEach(hotel -> {
                        cache.put(
                                CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotel.getHotelId(),
                                hotel,
                                RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                                TimeUnit.MILLISECONDS
                        );
                    });

                } else {
                    hotels = (List<Hotel>) result;
                }
            }
        }

        return hotels;
    }

    @Override
    public List<Hotel> searchHotelsByPageByName(Integer pageNum, Integer pageSize, String name, Integer sortBy, Integer order) {
        List<Hotel> hotels = null;
        // cache method res
        Object[] params = {name, sortBy, order};
        String methodName = "searchHotelsByPageByName";
        Object result = CacheUtil.getMethodResultFromCache(methodName, params);
        if (result != null) {
            hotels = (List<Hotel>) result;
        } else {
            synchronized (this) {
                result = CacheUtil.getMethodResultFromCache(methodName, params);
                if (result == null) {
                    // fetch data from db
                    IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);

                    // only get onSale result
                    hotels = hotelDao.findAllByName(true, name);

                    // need to sort the result, we are using logical paging, we fetch all data from db, and sort.
                    // This is because search data has limited number
                    if (sortBy == null) {
                        // if there is no sort params come in...
                        // by default, sort by rank desc
                        hotels.sort((hotel1, hotel2)-> hotel2.getRank() - hotel1.getRank());

                    } else {
                        // sort the result, default is up and by rank
                        if (sortBy.equals(CommonConstant.SORT_BY_PRICE)) {
                            hotels.sort(Comparator.comparing(Hotel::getMinPrice));
                        } else if (sortBy.equals(CommonConstant.SORT_BY_CREATE_TIME)) {
                            hotels.sort(Comparator.comparing(Hotel::getCreateTime));
                        } else {
                            hotels.sort(Comparator.comparingInt(Hotel::getRank));
                        }

                        // if sort down, revert the list
                        if (!order.equals(CommonConstant.SORT_UP)) {
                            Collections.reverse(hotels);
                        }
                    }

                    // cache method result
                    CacheUtil.putMethodResultInCache(
                            methodName,
                            hotels,
                            RandomUtil.randomLong(CacheConstant.CACHE_HOT_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS,
                            params
                            );

                    // cache each hotel res
                    hotels.forEach(hotel -> {
                        cache.put(
                                CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotel.getHotelId(),
                                hotel,
                                RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                                TimeUnit.MILLISECONDS
                        );
                    });

                } else {
                    hotels = (List<Hotel>) result;
                }
            }
        }

        // logical paging
        int totalRowNum = hotels.size();
        int totalPageNum = (totalRowNum % pageSize == 0 ? (totalRowNum / pageSize) : (totalRowNum / pageSize + 1));
        int currentPageNo;
        if (pageNum == null || pageNum <= 0) {
            currentPageNo = 1;
        } else if (pageNum > totalPageNum && totalPageNum > 0) {
            // over range page no, return nothing
            return new ArrayList<>();
        } else {
            currentPageNo = pageNum;
        }

        int startRow = (currentPageNo - 1) * pageSize;

        if (startRow + pageSize <= totalRowNum) {
            return hotels.subList(startRow, startRow + pageSize);
        } else {
            return hotels.subList(startRow, totalRowNum);
        }
    }

    @Override
    public List<Hotel> searchHotelsByPageByPostCode(Integer pageNum, Integer pageSize, String postCode, Integer sortBy, Integer order) {
        List<Hotel> hotels = null;
        // cache method res
        Object[] params = {postCode, sortBy, order};
        String methodName = "searchHotelsByPageByPostCode";
        Object result = CacheUtil.getMethodResultFromCache(methodName, params);
        if (result != null) {
            hotels = (List<Hotel>) result;
        } else {
            synchronized (this) {
                result = CacheUtil.getMethodResultFromCache(methodName, params);
                if (result == null) {
                    // fetch data from db
                    IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);
                    // only get onSale result
                    hotels = hotelDao.findAllByPostCode(true, postCode);

                    // by default, sort by rank DESC
                    if (sortBy == null) {
                        hotels.sort((hotel1, hotel2) ->
                            hotel2.getRank() - hotel1.getRank()
                        );
                    } else {
                        // sort the result, default is up and by rank
                        if (sortBy.equals(CommonConstant.SORT_BY_PRICE)) {
                            hotels.sort(Comparator.comparing(Hotel::getMinPrice));
                        } else if (sortBy.equals(CommonConstant.SORT_BY_CREATE_TIME)) {
                            hotels.sort(Comparator.comparing(Hotel::getCreateTime));
                        } else {
                            hotels.sort(Comparator.comparingInt(Hotel::getRank));
                        }

                        // if sort down, revert the list
                        if (!order.equals(CommonConstant.SORT_UP)) {
                            Collections.reverse(hotels);
                        }
                    }

                    // cache method result
                    CacheUtil.putMethodResultInCache(
                            methodName,
                            hotels,
                            RandomUtil.randomLong(CacheConstant.CACHE_HOT_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS,
                            params
                    );

                    // cache each hotel res
                    hotels.forEach(hotel -> {
                        cache.put(
                                CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotel.getHotelId(),
                                hotel,
                                RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                                TimeUnit.MILLISECONDS
                        );
                    });
                } else {
                    hotels = (List<Hotel>) result;
                }
            }
        }

        // logical paging
        int totalRowNum = hotels.size();
        int totalPageNum = (totalRowNum % pageSize == 0 ? (totalRowNum / pageSize) : (totalRowNum / pageSize + 1));
        int currentPageNo;
        if (pageNum == null || pageNum <= 0) {
            currentPageNo = 1;
        } else if (pageNum > totalPageNum && totalPageNum > 0) {
            // over range page no, return nothing
            return new ArrayList<>();
        } else {
            currentPageNo = pageNum;
        }

        int startRow = (currentPageNo - 1) * pageSize;

        if (startRow + pageSize <= totalRowNum) {
            return hotels.subList(startRow, startRow + pageSize);
        } else {
            return hotels.subList(startRow, totalRowNum);
        }
    }
}
