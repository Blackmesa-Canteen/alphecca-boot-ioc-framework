package io.swen90007sm2.app.blo.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.alpheccaboot.exception.NotImplementedException;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.blo.IHotelAmenityBlo;
import io.swen90007sm2.app.blo.IHotelBlo;
import io.swen90007sm2.app.blo.IHotelierBlo;
import io.swen90007sm2.app.blo.IRoomBlo;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;
import io.swen90007sm2.app.cache.util.CacheUtil;
import io.swen90007sm2.app.common.constant.CommonConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.common.factory.IdFactory;
import io.swen90007sm2.app.common.util.CurrencyUtil;
import io.swen90007sm2.app.dao.IHotelAmenityDao;
import io.swen90007sm2.app.dao.IHotelDao;
import io.swen90007sm2.app.dao.IHotelierDao;
import io.swen90007sm2.app.dao.impl.HotelAmenityDao;
import io.swen90007sm2.app.dao.impl.HotelDao;
import io.swen90007sm2.app.dao.impl.HotelierDao;
import io.swen90007sm2.app.db.bean.PageBean;
import io.swen90007sm2.app.db.helper.UnitOfWorkHelper;
import io.swen90007sm2.app.model.entity.Hotel;
import io.swen90007sm2.app.model.entity.HotelAmenity;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.entity.Room;
import io.swen90007sm2.app.model.param.HotelParam;
import io.swen90007sm2.app.model.param.UpdateHotelParam;
import io.swen90007sm2.app.model.pojo.Money;
import io.swen90007sm2.app.model.vo.HotelVo;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Blo
public class HotelBlo implements IHotelBlo {

    @AutoInjected
    IHotelierBlo hotelierBlo;

    @AutoInjected
    IHotelAmenityBlo hotelAmenityBlo;

    @AutoInjected
    IRoomBlo roomBlo;

    @AutoInjected
    @Qualifier(name = CacheConstant.OBJECT_CACHE_BEAN)
    ICacheStorage<String, Object> cache;

    public void doCreateHotel(String hotelierId, HotelParam hotelParam) {

        // check existence, one hotelier can only create one hotel
        Hotelier currentHotelier = hotelierBlo.getHotelierInfoByUserId(hotelierId);
        if (currentHotelier == null) {
            throw new RequestException(
                    StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getMessage(),
                    StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getCode()
            );
        }

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
        hotel.setName(hotelParam.getName());
        hotel.setDescription(hotelParam.getDescription());
        hotel.setAddress(hotelParam.getAddress());
        hotel.setPostCode(hotelParam.getPostCode());
        hotel.setOnSale(hotelParam.getOnSale());

        List<String> amenity_ids = hotelParam.getAmenityIds();
        IHotelAmenityDao amenityDao = BeanManager.getLazyBeanByClass(HotelAmenityDao.class);
        IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);
        IHotelierDao hotelierDao = BeanManager.getLazyBeanByClass(HotelierDao.class);

        // Atom operations, need lock
        synchronized (this) {
            // firstly, insert new hotel
            hotelDao.insertOne(hotel);
//            UnitOfWorkHelper.getCurrent().registerNew(hotel, hotelDao);

            // get cooresponding hotelier info
            Hotelier originalHotelier = hotelierBlo.getHotelierInfoByUserId(hotelierId);
            Hotelier newHotelier = new Hotelier();
            BeanUtil.copyProperties(originalHotelier, newHotelier);
            newHotelier.setHotelId(hotel.getHotelId());

            // update hotelier info
            hotelierDao.updateOne(newHotelier);
            cache.remove(CacheConstant.ENTITY_USER_KEY_PREFIX + hotelierId);
//            UnitOfWorkHelper.getCurrent().registerDirty(newHotelier, hotelierDao, CacheConstant.ENTITY_USER_KEY_PREFIX + hotelierId);

            // attach amnities to this hotel
            amenityDao.addAmenityIdsToHotel(
                    amenity_ids,
                    hotel.getHotelId()
            );
        }
    }

    @Override
    public HotelVo getHotelInfoByOwnerHotelierId(String hotelierId, String currencyName, Boolean showNotSale) {
        HotelVo hotelVo;
        Hotelier currentHotelier = hotelierBlo.getHotelierInfoByUserId(hotelierId);
        String hotelId = currentHotelier.getHotelId();
        if (StringUtils.isEmpty(hotelId)) {
            throw new RequestException(
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getMessage(),
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getCode()
            );
        }

        Hotel hotel = getHotelEntityByHotelId(hotelId);
        if (hotel == null || (!showNotSale && !hotel.getOnSale())) {
            throw new RequestException(
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getMessage(),
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getCode()
            );
        }

        // generate hotel vo
        hotelVo = new HotelVo();
        // copy properties
        BeanUtil.copyProperties(hotel, hotelVo);

        // embedded value
        Money money = new Money();
        money.setCurrency(currencyName);
        money.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, hotel.getMinPrice()));
        hotelVo.setMoney(money);

        // list amenities
        List<HotelAmenity> amenities = hotelAmenityBlo.getAllAmenitiesByHotelId(hotelId);
        hotelVo.setAmenities(amenities);

        return hotelVo;
    }

    @Override
    public void editOwnedHotel(String hotelierId, HotelParam hotelParam) {
        Hotelier currentHotelier = hotelierBlo.getHotelierInfoByUserId(hotelierId);
        String hotelId = currentHotelier.getHotelId();
        if (StringUtils.isEmpty(hotelId)) {
            throw new RequestException(
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getMessage(),
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getCode()
            );
        }

        Hotel originalHotel = getHotelEntityByHotelId(hotelId);

        if (originalHotel == null) {
            throw new RequestException(
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getMessage(),
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getCode()
            );
        }
        Hotel hotel = new Hotel();
        BeanUtil.copyProperties(originalHotel, hotel);

        if (hotelParam.getOnSale() != null) hotel.setOnSale(hotelParam.getOnSale());
        if (hotelParam.getAddress() != null) hotel.setAddress(hotelParam.getAddress());
        if (hotelParam.getName() != null) hotel.setName(hotelParam.getName());
        if (hotelParam.getDescription() != null) hotel.setName(hotelParam.getDescription());
        if (hotelParam.getPostCode() != null) hotel.setPostCode(hotelParam.getPostCode());

        IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);
        // atom operation
        synchronized (this) {
            // update hotel
//            hotelDao.updateOne(hotel);
            UnitOfWorkHelper.getCurrent().registerDirty(
                    hotel,
                    hotelDao,
                    CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotelId
            );

            // update amenity (atom update the associate table)
            hotelAmenityBlo.updateAmenityIdsForHotel(hotelParam.getAmenityIds(), hotelId);

            // clean up cache
            cache.remove(CacheConstant.VO_HOTEL_KEY_PREFIX + hotelId);
            cache.remove(CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotelId);
        }

    }

    @Override
    public void editHotelByHotelId(UpdateHotelParam updateHotelParam) {
        String hotelId = updateHotelParam.getHotelId();
        if (StringUtils.isEmpty(hotelId)) {
            throw new RequestException(
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getMessage(),
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getCode()
            );
        }

        Hotel originalHotel = getHotelEntityByHotelId(hotelId);

        if (originalHotel == null) {
            throw new RequestException(
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getMessage(),
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getCode()
            );
        }
        Hotel hotel = new Hotel();
        BeanUtil.copyProperties(originalHotel, hotel);

        if (updateHotelParam.getOnSale() != null) hotel.setOnSale(updateHotelParam.getOnSale());
        if (updateHotelParam.getAddress() != null) hotel.setAddress(updateHotelParam.getAddress());
        if (updateHotelParam.getName() != null) hotel.setName(updateHotelParam.getName());
        if (updateHotelParam.getDescription() != null) hotel.setName(updateHotelParam.getName());
        if (updateHotelParam.getPostCode() != null) hotel.setPostCode(updateHotelParam.getPostCode());

        IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);
        // atom operation
        synchronized (this) {
            // update hotel
//            hotelDao.updateOne(hotel);
            UnitOfWorkHelper.getCurrent().registerDirty(
                    hotel,
                    hotelDao,
                    CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotelId
            );

            // update amenity (atom update the associate table)
            hotelAmenityBlo.updateAmenityIdsForHotel(updateHotelParam.getAmenityIds(), hotelId);

            // clean up cache
            cache.remove(CacheConstant.VO_HOTEL_KEY_PREFIX + hotelId);
            cache.remove(CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotelId);
        }
    }

    @Override
    public void editeHotelMinPriceByHotelId(String hotelId, String currencyName, BigDecimal newPrice) {
        if (StringUtils.isEmpty(hotelId)) {
            throw new RequestException(
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getMessage(),
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getCode()
            );
        }

        Hotel originalHotel = getHotelEntityByHotelId(hotelId);
        if (originalHotel == null) {
            throw new RequestException(
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getMessage(),
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getCode()
            );
        }
        Hotel hotel = new Hotel();
        BeanUtil.copyProperties(originalHotel, hotel);

        // calc absolute AUD price
        BigDecimal newPriceInAUD = CurrencyUtil.convertCurrencyToAUD(currencyName, newPrice);
        BigDecimal originalPriceInAUD = originalHotel.getMinPrice();
        if (originalPriceInAUD.equals(CommonConstant.DEFAULT_PRICE_BIG_DECIMAL)
                || originalPriceInAUD.compareTo(newPriceInAUD) > 0) {
            // if the original price is default value, or is greater than new value, update it to new value
            hotel.setMinPrice(newPriceInAUD);
        }

        IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);
        UnitOfWorkHelper.getCurrent().registerDirty(hotel, hotelDao, CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotelId);
    }

    @Override
    public void updateHotelMinPrice(String hotelId) {
        IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);

        synchronized (this) {
            List<Room> rooms = roomBlo.getAllRoomEntitiesFromHotelId(hotelId);
            // filter room, get onSale rooms
            List<Room> onSaleRooms = rooms.stream().filter(Room::getOnSale).collect(Collectors.toList());
            BigDecimal minPrice = CommonConstant.DEFAULT_PRICE_BIG_DECIMAL;
            if (onSaleRooms.size() > 0) {
                // up sort
                onSaleRooms.sort(Comparator.comparing(Room::getPricePerNight));
                minPrice = onSaleRooms.get(0).getPricePerNight();
            }

            editeHotelMinPriceByHotelId(hotelId, CommonConstant.AUD_CURRENCY, minPrice);
        }
    }

    @Override
    public Hotel getHotelEntityByHotelId(String hotelId) {
        Optional<Object> cacheItem = cache.get(CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotelId);
        Hotel hotel = null;
        if (cacheItem.isEmpty()) {
            synchronized (this) {
                cacheItem = cache.get(CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotelId);
                if (cacheItem.isEmpty()) {
                    IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);
                    hotel = hotelDao.findOneByBusinessId(hotelId);
                    if (hotel == null) {
                        throw new RequestException(
                                "hotel not found",
                                StatusCodeEnume.RESOURCE_NOT_FOUND_EXCEPTION.getCode());
                    }

                    cache.put(CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotelId,
                            hotel,
                            RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS
                    );
                } else {
                    hotel = (Hotel) cacheItem.get();
                }
            }
        } else {
            hotel = (Hotel) cacheItem.get();
        }

        return hotel;
    }

    @Override
    public HotelVo getHotelInfoByHotelId(String hotelId, String currencyName, Boolean showNotSale) {
        Optional<Object> cacheItem = cache.get(CacheConstant.VO_HOTEL_KEY_PREFIX + hotelId);
        HotelVo hotelVo = null;
        if (cacheItem.isEmpty()) {
            synchronized (this) {
                cacheItem = cache.get(CacheConstant.VO_HOTEL_KEY_PREFIX + hotelId);
                // if the cache is still empty
                if (cacheItem.isEmpty()) {
                    IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);
                    Hotel hotel = hotelDao.findOneByBusinessId(hotelId);
                    // if no such
                    if (hotel == null || (!showNotSale && !hotel.getOnSale())) {
                        throw new RequestException(
                                "hotel not found",
                                StatusCodeEnume.RESOURCE_NOT_FOUND_EXCEPTION.getCode());
                    }

                    // generate hotel vo
                    hotelVo = new HotelVo();
                    // copy properties
                    BeanUtil.copyProperties(hotel, hotelVo);

                    // embedded value
                    Money money = new Money();
                    money.setCurrency(currencyName);
                    money.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, hotel.getMinPrice()));
                    hotelVo.setMoney(money);

                    // list amenities
                    List<HotelAmenity> amenities = hotelAmenityBlo.getAllAmenitiesByHotelId(hotelId);
                    hotelVo.setAmenities(amenities);

                    // use random expiration time to prevent Cache Avalanche
                    cache.put(
                            CacheConstant.VO_HOTEL_KEY_PREFIX + hotelId,
                            hotelVo,
                            RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS
                    );

                    cache.put(CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotelId,
                            hotel,
                            RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS
                    );
                } else {
                    // data is put by other thread, just get from cache.
                    hotelVo = (HotelVo) cacheItem.get();
                    // embedded value, currency exchange
                    Money money = hotelVo.getMoney();
                    money.setCurrency(currencyName);
                    money.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, hotelVo.getMinPrice()));
                    hotelVo.setMoney(money);
                }
            }

        } else {
            hotelVo = (HotelVo) cacheItem.get();
            // embedded value, currency exchange
            Money money = hotelVo.getMoney();
            money.setCurrency(currencyName);
            money.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, hotelVo.getMinPrice()));
            hotelVo.setMoney(money);
        }

        return hotelVo;
    }

    @Override
    public List<HotelVo> getHotelsByPageSortedByCreateTime(String currencyName, Integer pageNum, Integer pageSize, Integer order) {
        List<HotelVo> hotelVos = new LinkedList<>();
        // cache method res
        Object[] params = {pageNum, pageSize, order};
        String methodName = "getHotelsByPageSortedByCreateTime";
        Object result = CacheUtil.getMethodResultFromCache(methodName, params);
        if (result != null) {
            hotelVos = (List<HotelVo>) result;

            // currency exchange
            hotelVos = hotelVos.stream().peek(hotelVo -> {
                Money money = hotelVo.getMoney();
                money.setCurrency(currencyName);
                money.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, hotelVo.getMinPrice()));
                hotelVo.setMoney(money);
            }).collect(Collectors.toList());
        } else {
            synchronized (this) {
                result = CacheUtil.getMethodResultFromCache(methodName, params);
                if (result == null) {
                    // fetch data from db
                    IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);
                    // only finds on sale hotels
                    int totalRows = hotelDao.findTotalCountByOnSale(true);

                    // physic paging: use database offset to page results
                    PageBean<Hotel> pageBean = new PageBean<>(
                            pageSize,
                            totalRows,
                            pageNum
                    );

                    // calculate start row in database
                    int start = pageBean.getStartRow();

                    List<Hotel> hotels;
                    if (order == null) {
                        hotels = hotelDao.findAllByPageByDateByOnSale(start, pageSize, CommonConstant.SORT_DOWN, true);
                    } else {
                        hotels = hotelDao.findAllByPageByDateByOnSale(start, pageSize, order, true);
                    }

                    for (Hotel hotel : hotels) {
                        // generate hotel vo
                        HotelVo hotelVo = new HotelVo();
                        // copy properties
                        BeanUtil.copyProperties(hotel, hotelVo);

                        // embedded value
                        Money money = new Money();
                        money.setCurrency(currencyName);
                        money.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, hotel.getMinPrice()));
                        hotelVo.setMoney(money);

                        // list amenities
                        List<HotelAmenity> amenities = hotelAmenityBlo.getAllAmenitiesByHotelId(hotel.getHotelId());
                        hotelVo.setAmenities(amenities);

                        // cache the latest item
                        cache.put(
                                CacheConstant.VO_HOTEL_KEY_PREFIX + hotelVo.getHotelId(),
                                hotelVo,
                                RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                                TimeUnit.MILLISECONDS
                        );

                        cache.put(CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotel.getHotelId(),
                                hotel,
                                RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                                TimeUnit.MILLISECONDS
                        );

                        hotelVos.add(hotelVo);
                    }

                    // cache method result
                    CacheUtil.putMethodResultInCache(
                            methodName,
                            hotelVos,
                            RandomUtil.randomLong(CacheConstant.CACHE_POPULAR_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS,
                            params
                    );

                } else {
                    hotelVos = (List<HotelVo>) result;
                    // currency exchange
                    hotelVos = hotelVos.stream().peek(hotelVo -> {
                        Money money = hotelVo.getMoney();
                        money.setCurrency(currencyName);
                        money.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, hotelVo.getMinPrice()));
                        hotelVo.setMoney(money);
                    }).collect(Collectors.toList());
                }
            }
        }

        return hotelVos;
    }

    @Override
    public List<HotelVo> getHotelsByPageSortedByPrice(String currencyName, Integer pageNum, Integer pageSize, Integer order) {
        List<HotelVo> hotelVos = new LinkedList<>();
        // cache method res
        Object[] params = {pageNum, pageSize, order};
        String methodName = "getHotelsByPageSortedByCreateTime";
        Object result = CacheUtil.getMethodResultFromCache(methodName, params);
        if (result != null) {
            hotelVos = (List<HotelVo>) result;
            // currency exchange
            hotelVos = hotelVos.stream().peek(hotelVo -> {
                Money money = hotelVo.getMoney();
                money.setCurrency(currencyName);
                money.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, hotelVo.getMinPrice()));
                hotelVo.setMoney(money);
            }).collect(Collectors.toList());
        } else {
            synchronized (this) {
                result = CacheUtil.getMethodResultFromCache(methodName, params);
                if (result == null) {
                    // fetch data from db
                    IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);
                    // only finds on sale hotels
                    int totalRows = hotelDao.findTotalCountByOnSale(true);

                    // physic paging: use database offset to page results
                    PageBean<Hotel> pageBean = new PageBean<>(
                            pageSize,
                            totalRows,
                            pageNum
                    );

                    // calculate start row in database
                    int start = pageBean.getStartRow();
                    List<Hotel> hotels;
                    if (order == null) {
                        hotels = hotelDao.findAllByPageSortByPriceByOnSale(start, pageSize, CommonConstant.SORT_DOWN, true);
                    } else {
                        hotels = hotelDao.findAllByPageSortByPriceByOnSale(start, pageSize, order, true);
                    }

                    for (Hotel hotel : hotels) {
                        // generate hotel vo
                        HotelVo hotelVo = new HotelVo();
                        // copy properties
                        BeanUtil.copyProperties(hotel, hotelVo);

                        // embedded value
                        Money money = new Money();
                        money.setCurrency(currencyName);
                        money.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, hotel.getMinPrice()));
                        hotelVo.setMoney(money);

                        // list amenities
                        List<HotelAmenity> amenities = hotelAmenityBlo.getAllAmenitiesByHotelId(hotel.getHotelId());
                        hotelVo.setAmenities(amenities);

                        // cache the latest item
                        cache.put(
                                CacheConstant.VO_HOTEL_KEY_PREFIX + hotelVo.getHotelId(),
                                hotelVo,
                                RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                                TimeUnit.MILLISECONDS
                        );

                        cache.put(CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotel.getHotelId(),
                                hotel,
                                RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                                TimeUnit.MILLISECONDS
                        );
                        hotelVos.add(hotelVo);
                    }

                    // cache method result
                    CacheUtil.putMethodResultInCache(
                            methodName,
                            hotelVos,
                            RandomUtil.randomLong(CacheConstant.CACHE_POPULAR_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS,
                            params
                    );

                } else {
                    hotelVos = (List<HotelVo>) result;
                }
            }
        }

        return hotelVos;
    }

    @Override
    public List<HotelVo> getHotelsByPageSortedByRank(String currencyName, Integer pageNum, Integer pageSize, Integer order) {
        List<HotelVo> hotelVos = new LinkedList<>();
        // cache method res
        Object[] params = {pageNum, pageSize, order};
        String methodName = "getHotelsByPageSortedByCreateTime";
        Object result = CacheUtil.getMethodResultFromCache(methodName, params);
        if (result != null) {
            hotelVos = (List<HotelVo>) result;
            // currency exchange
            hotelVos = hotelVos.stream().peek(hotelVo -> {
                Money money = hotelVo.getMoney();
                money.setCurrency(currencyName);
                money.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, hotelVo.getMinPrice()));
                hotelVo.setMoney(money);
            }).collect(Collectors.toList());
        } else {
            synchronized (this) {
                result = CacheUtil.getMethodResultFromCache(methodName, params);
                if (result == null) {
                    // fetch data from db
                    IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);
                    // only finds on sale hotels
                    int totalRows = hotelDao.findTotalCountByOnSale(true);

                    // physic paging: use database offset to page results
                    PageBean<Hotel> pageBean = new PageBean<>(
                            pageSize,
                            totalRows,
                            pageNum
                    );

                    // calculate start row in database
                    int start = pageBean.getStartRow();
                    List<Hotel> hotels;
                    if (order == null) {
                        hotels = hotelDao.findAllByPageSortByRankByOnSale(start, pageSize, CommonConstant.SORT_DOWN, true);
                    } else {
                        hotels = hotelDao.findAllByPageSortByRankByOnSale(start, pageSize, order, true);
                    }

                    for (Hotel hotel : hotels) {
                        // generate hotel vo
                        HotelVo hotelVo = new HotelVo();
                        // copy properties
                        BeanUtil.copyProperties(hotel, hotelVo);

                        // embedded value
                        Money money = new Money();
                        money.setCurrency(currencyName);
                        money.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, hotel.getMinPrice()));
                        hotelVo.setMoney(money);

                        // list amenities
                        List<HotelAmenity> amenities = hotelAmenityBlo.getAllAmenitiesByHotelId(hotel.getHotelId());
                        hotelVo.setAmenities(amenities);

                        // cache the latest item
                        cache.put(
                                CacheConstant.VO_HOTEL_KEY_PREFIX + hotelVo.getHotelId(),
                                hotelVo,
                                RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                                TimeUnit.MILLISECONDS
                        );

                        cache.put(CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotel.getHotelId(),
                                hotel,
                                RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                                TimeUnit.MILLISECONDS
                        );

                        hotelVos.add(hotelVo);
                    }

                    // cache method result
                    CacheUtil.putMethodResultInCache(
                            methodName,
                            hotelVos,
                            RandomUtil.randomLong(CacheConstant.CACHE_POPULAR_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS,
                            params
                    );
                } else {
                    hotelVos = (List<HotelVo>) result;
                    // currency exchange
                    hotelVos = hotelVos.stream().peek(hotelVo -> {
                        Money money = hotelVo.getMoney();
                        money.setCurrency(currencyName);
                        money.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, hotelVo.getMinPrice()));
                        hotelVo.setMoney(money);
                    }).collect(Collectors.toList());
                }
            }
        }

        return hotelVos;
    }

    @Override
    public List<HotelVo> searchHotelsByPageByName(String currencyName, Integer pageNum, Integer pageSize, String name, Integer sortBy, Integer order) {
        List<HotelVo> hotelVos = new LinkedList<>();
        // cache method res
        Object[] params = {name, sortBy, order};
        String methodName = "searchHotelsByPageByName";
        Object result = CacheUtil.getMethodResultFromCache(methodName, params);
        if (result != null) {
            hotelVos = (List<HotelVo>) result;
            // currency exchange
            hotelVos = hotelVos.stream().peek(hotelVo -> {
                Money money = hotelVo.getMoney();
                money.setCurrency(currencyName);
                money.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, hotelVo.getMinPrice()));
                hotelVo.setMoney(money);
            }).collect(Collectors.toList());
        } else {
            synchronized (this) {
                result = CacheUtil.getMethodResultFromCache(methodName, params);
                if (result == null) {
                    // fetch data from db
                    IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);

                    // only get onSale result
                    List<Hotel> hotels = hotelDao.findAllByNameByOnSale(true, name);

                    // need to sort the result, we are using logical paging, we fetch all data from db, and sort.
                    // This is because search data has limited number
                    if (sortBy == null) {
                        // if there is no sort params come in...
                        // by default, sort by rank desc
                        hotels.sort((hotel1, hotel2) -> hotel2.getRank() - hotel1.getRank());

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

                    for (Hotel hotel : hotels) {
                        // generate hotel vo
                        HotelVo hotelVo = new HotelVo();
                        // copy properties
                        BeanUtil.copyProperties(hotel, hotelVo);

                        // embedded value
                        Money money = new Money();
                        money.setCurrency(currencyName);
                        money.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, hotel.getMinPrice()));
                        hotelVo.setMoney(money);

                        // list amenities
                        List<HotelAmenity> amenities = hotelAmenityBlo.getAllAmenitiesByHotelId(hotel.getHotelId());
                        hotelVo.setAmenities(amenities);

                        // cache the latest item
                        cache.put(
                                CacheConstant.VO_HOTEL_KEY_PREFIX + hotelVo.getHotelId(),
                                hotelVo,
                                RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                                TimeUnit.MILLISECONDS
                        );

                        cache.put(CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotel.getHotelId(),
                                hotel,
                                RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                                TimeUnit.MILLISECONDS
                        );

                        hotelVos.add(hotelVo);
                    }

                    // cache method result
                    CacheUtil.putMethodResultInCache(
                            methodName,
                            hotelVos,
                            RandomUtil.randomLong(CacheConstant.CACHE_HOT_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS,
                            params
                    );

                } else {
                    hotelVos = (List<HotelVo>) result;
                    // currency exchange
                    hotelVos = hotelVos.stream().peek(hotelVo -> {
                        Money money = hotelVo.getMoney();
                        money.setCurrency(currencyName);
                        money.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, hotelVo.getMinPrice()));
                        hotelVo.setMoney(money);
                    }).collect(Collectors.toList());
                }
            }
        }

        // logical paging
        int totalRowNum = hotelVos.size();
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
            return hotelVos.subList(startRow, startRow + pageSize);
        } else {
            return hotelVos.subList(startRow, totalRowNum);
        }
    }

    @Override
    public List<HotelVo> searchHotelsByPageByPostCode(String currencyName, Integer pageNum, Integer pageSize, String postCode, Integer sortBy, Integer order) {
        List<HotelVo> hotelVos = new LinkedList<>();
        // cache method res
        Object[] params = {postCode, sortBy, order};
        String methodName = "searchHotelsByPageByPostCode";
        Object result = CacheUtil.getMethodResultFromCache(methodName, params);
        if (result != null) {
            hotelVos = (List<HotelVo>) result;
            // currency exchange
            hotelVos = hotelVos.stream().peek(hotelVo -> {
                Money money = hotelVo.getMoney();
                money.setCurrency(currencyName);
                money.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, hotelVo.getMinPrice()));
                hotelVo.setMoney(money);
            }).collect(Collectors.toList());
        } else {
            synchronized (this) {
                result = CacheUtil.getMethodResultFromCache(methodName, params);
                if (result == null) {
                    // fetch data from db
                    IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);
                    // only get onSale result
                    List<Hotel> hotels = hotelDao.findAllByPostCodeByOnSale(true, postCode);

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

                    for (Hotel hotel : hotels) {
                        // generate hotel vo
                        HotelVo hotelVo = new HotelVo();
                        // copy properties
                        BeanUtil.copyProperties(hotel, hotelVo);

                        // embedded value
                        Money money = new Money();
                        money.setCurrency(currencyName);
                        money.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, hotel.getMinPrice()));
                        hotelVo.setMoney(money);

                        // list amenities
                        List<HotelAmenity> amenities = hotelAmenityBlo.getAllAmenitiesByHotelId(hotel.getHotelId());
                        hotelVo.setAmenities(amenities);

                        // cache the latest item
                        cache.put(
                                CacheConstant.VO_HOTEL_KEY_PREFIX + hotelVo.getHotelId(),
                                hotelVo,
                                RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                                TimeUnit.MILLISECONDS
                        );

                        cache.put(CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotel.getHotelId(),
                                hotel,
                                RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                                TimeUnit.MILLISECONDS
                        );

                        hotelVos.add(hotelVo);
                    }

                    // cache method result
                    CacheUtil.putMethodResultInCache(
                            methodName,
                            hotelVos,
                            RandomUtil.randomLong(CacheConstant.CACHE_HOT_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS,
                            params
                    );
                } else {
                    hotelVos = (List<HotelVo>) result;
                    // currency exchange
                    hotelVos = hotelVos.stream().peek(hotelVo -> {
                        Money money = hotelVo.getMoney();
                        money.setCurrency(currencyName);
                        money.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, hotelVo.getMinPrice()));
                        hotelVo.setMoney(money);
                    }).collect(Collectors.toList());
                }
            }
        }

        // logical paging
        int totalRowNum = hotelVos.size();
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
            return hotelVos.subList(startRow, startRow + pageSize);
        } else {
            return hotelVos.subList(startRow, totalRowNum);
        }
    }

    @Override
    public List<HotelVo> getAllHotelsByDate(Integer pageNum, Integer pageSize, Integer order) {
        // fetch data from db
        IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);
        // only finds on sale hotels
        int totalRows = hotelDao.findTotalCount();

        // physic paging: use database offset to page results
        PageBean<Hotel> pageBean = new PageBean<>(
                pageSize,
                totalRows,
                pageNum
        );

        // calculate start row in database
        int start = pageBean.getStartRow();

        List<Hotel> hotels;
        if (order == null) {
            hotels = hotelDao.findAllByPageByDate(start, pageSize, CommonConstant.SORT_DOWN);
        } else {
            hotels = hotelDao.findAllByPageByDate(start, pageSize, order);
        }

        List<HotelVo> hotelVos = new LinkedList<>();

        for (Hotel hotel : hotels) {
            // generate hotel vo
            HotelVo hotelVo = new HotelVo();
            // copy properties
            BeanUtil.copyProperties(hotel, hotelVo);

            // embedded value
            Money money = new Money();
            money.setCurrency(hotel.getCurrency());
            money.setAmount(hotel.getMinPrice());
            hotelVo.setMoney(money);

            // list amenities
            List<HotelAmenity> amenities = hotelAmenityBlo.getAllAmenitiesByHotelId(hotel.getHotelId());
            hotelVo.setAmenities(amenities);

            hotelVos.add(hotelVo);
        }

        return hotelVos;
    }
}
