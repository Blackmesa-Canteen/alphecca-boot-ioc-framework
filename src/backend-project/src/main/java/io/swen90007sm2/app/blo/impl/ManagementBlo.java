package io.swen90007sm2.app.blo.impl;

import cn.hutool.core.bean.BeanUtil;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.blo.ICustomerBlo;
import io.swen90007sm2.app.blo.IHotelBlo;
import io.swen90007sm2.app.blo.IHotelierBlo;
import io.swen90007sm2.app.blo.IManagementBlo;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;
import io.swen90007sm2.app.common.constant.CommonConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.common.factory.IdFactory;
import io.swen90007sm2.app.dao.IHotelDao;
import io.swen90007sm2.app.dao.IHotelierDao;
import io.swen90007sm2.app.dao.impl.HotelDao;
import io.swen90007sm2.app.dao.impl.HotelierDao;
import io.swen90007sm2.app.db.bean.PageBean;
import io.swen90007sm2.app.db.helper.UnitOfWorkHelper;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.entity.Hotel;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.param.*;
import io.swen90007sm2.app.model.vo.HotelVo;
import io.swen90007sm2.app.security.util.SecurityUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Blo
public class ManagementBlo implements IManagementBlo {

    @AutoInjected
    ICustomerBlo customerBlo;

    @AutoInjected
    IHotelierBlo hotelierBlo;

    @AutoInjected
    IHotelBlo hotelBlo;

    @AutoInjected
    @Qualifier(name = CacheConstant.OBJECT_CACHE_BEAN)
    ICacheStorage<String, Object> cache;

//    @AutoInjected

    @Override
    public PageBean<Customer> getCustomerByPage(int pageNo, int pageSize) {
        return customerBlo.getCustomerByPage(pageNo, pageSize);
    }

    @Override
    public void groupHotelierWithExistingHotel(AdminGroupHotelierParam param) {
        Hotelier currentHotelier = hotelierBlo.getHotelierInfoByUserId(param.getHotelOwningHotelierUserId());
        Hotelier hotelierToAdd = hotelierBlo.getHotelierInfoByUserId(param.getHotelierToAddUserId());
        if (currentHotelier == null || hotelierToAdd == null) {
            throw new RequestException(
                    StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getMessage(),
                    StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getCode()
            );
        }

        String hotelId = currentHotelier.getHotelId();
        if (StringUtils.isEmpty(hotelId)) {
            throw new RequestException(
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getMessage(),
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getCode()
            );
        }

        Hotel originalHotel = hotelBlo.getHotelEntityByHotelId(hotelId);

        if(originalHotel == null) {
            throw new RequestException(
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getMessage(),
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getCode()
            );
        }

        // TODO: 2020/11/16 0016 1. check if the hotelier to add has a hotel
        if(!StringUtils.isEmpty(hotelierToAdd.getHotelId())) {
            throw new RequestException(
                    StatusCodeEnume.HOTELIER_ALREADY_HAS_HOTEL.getMessage(),
                    StatusCodeEnume.HOTELIER_ALREADY_HAS_HOTEL.getCode()
            );
        }

        String hotelierToAddId = param.getHotelierToAddUserId();
//        String hotelierToAddName = param.getHotelierToAddUserName();

        hotelierToAdd.setHotelId(hotelId);

        IHotelierDao hotelierDao = BeanManager.getLazyBeanByClass(HotelierDao.class);
        UnitOfWorkHelper.getCurrent().registerDirty(hotelierToAdd, hotelierDao, CacheConstant.ENTITY_USER_KEY_PREFIX + hotelierToAddId);
        cache.remove(CacheConstant.TOKEN_KEY_PREFIX + hotelierToAddId);

//        // check existence
//        // will not use cache to prevent inconsistent data
//        IHotelierDao hotelierDao = BeanManager.getLazyBeanByClass(HotelierDao.class);
//        Hotelier prevResult = hotelierDao.findOneByBusinessId(hotelierToAddId);
//
//        if (prevResult != null) {
//            throw new RequestException(
//                    StatusCodeEnume.USER_EXIST_EXCEPTION.getMessage(),
//                    StatusCodeEnume.USER_EXIST_EXCEPTION.getCode()
//            );
//        }
//
//        // encrypt password before store it in db
//        String cypher = SecurityUtil.encrypt(param.getHotelierToAddPassword());
//
//        Hotelier hotelier = new Hotelier();
//        hotelier.setId(IdFactory.genSnowFlakeId());
//        hotelier.setUserId(hotelierToAddId);
//        hotelier.setUserName(hotelierToAddName);
//        hotelier.setPassword(cypher);
//        hotelier.setDescription("New User");
//
////        UnitOfWorkHelper current = UnitOfWorkHelper.getCurrent();
////        current.registerNew(hotelier, hotelierDao);
//
//
//        hotelier.setHotelId(hotelId);
//        System.out.println("hotelier id: " + hotelId);
//        hotelierDao.insertOne(hotelier);



    }

    @Override
    public List<HotelVo> getHotelByPage(int pageNo, int pageSize) {
        return hotelBlo.getAllHotelsByDate(pageNo, pageSize, null);
    }

    @Override
    public void changeHotelStatus(String hotelId) {
        Hotel hotelToModify = hotelBlo.getHotelEntityByHotelId(hotelId);
        if (hotelToModify == null) {
            throw new RequestException(
                    StatusCodeEnume.HOTEL_NOT_EXIST.getMessage(),
                    StatusCodeEnume.HOTEL_NOT_EXIST.getCode()
            );
        }


        boolean currentHotelOnSale = hotelToModify.getOnSale();
        boolean newHotelOnSale = !currentHotelOnSale;
        IHotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);

        synchronized (this) {
            hotelToModify.setOnSale(newHotelOnSale);
            hotelDao.updateOne(hotelToModify);

            cache.remove(CacheConstant.VO_HOTEL_KEY_PREFIX + hotelId);
            cache.remove(CacheConstant.ENTITY_HOTEL_KEY_PREFIX + hotelId);
        }
    }

    @Override
    public void registerNewHotelier(UserRegisterParam param) {
        hotelierBlo.doRegisterUser(param);
//        System.out.println("register new hotelier");

    }

    @Override
    public List<Hotelier> getHoteliersInOneGroupByHotelId(String hotelId) {
        Hotel hotel = hotelBlo.getHotelEntityByHotelId(hotelId);
        if (hotel == null) {
            throw new RequestException(
                    StatusCodeEnume.HOTEL_NOT_EXIST.getMessage(),
                    StatusCodeEnume.HOTEL_NOT_EXIST.getCode()
            );
        }

        synchronized (this) {
            IHotelierDao hotelierDao = BeanManager.getLazyBeanByClass(HotelierDao.class);
            List <Hotelier> hoteliers = hotelierDao.findAllByHotelId(hotelId);
            if (hoteliers.isEmpty()) {
                throw new RequestException(
                        StatusCodeEnume.HOTEL_DOES_NOT_HAVE_HOTELIER.getMessage(),
                        StatusCodeEnume.HOTEL_DOES_NOT_HAVE_HOTELIER.getCode()
                );
            }

            List<Hotelier> res = BeanUtil.copyToList(hoteliers, Hotelier.class);
            res.forEach(hotelier -> {
                hotelier.setPassword(CommonConstant.NULL);
            });

            return res;
        }


    }

    @Override
    public void removeHotelierFromHotelGroup(AdminRemoveHotelierParam param) {
        String hotelierToRemoveId = param.getHotelierId();
        Hotelier hotelierToRemove = hotelierBlo.getHotelierInfoByUserId(hotelierToRemoveId);
        if (hotelierToRemove == null) {
            throw new RequestException(
                    StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getMessage(),
                    StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getCode()
            );
        }
        String hotelId = hotelierToRemove.getHotelId();
        if (StringUtils.isEmpty(hotelId)) {
            throw new RequestException(
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getMessage(),
                    StatusCodeEnume.HOTELIER_NOT_HAS_HOTEL.getCode()
            );
        }

        hotelierToRemove.setHotelId(null);
        IHotelierDao hotelierDao = BeanManager.getLazyBeanByClass(HotelierDao.class);
        UnitOfWorkHelper.getCurrent().registerDirty(hotelierToRemove, hotelierDao, CacheConstant.ENTITY_USER_KEY_PREFIX + hotelierToRemoveId);
        cache.remove(CacheConstant.TOKEN_KEY_PREFIX + hotelierToRemoveId);
    }

    @Override
    public PageBean<Hotelier> getHoteliersByPage(int pageNo, int pageSize) {
        return hotelierBlo.getHotelierByPage(pageNo, pageSize);
    }

    @Override
    public void updateCustomerInfo(String userId, AdminUpdateUserParam param) {
        String userName = param.getUserName();
        String description = param.getDescription();
        String avatarUrl = param.getAvatarUrl();
        UserUpdateParam updateParam = new UserUpdateParam(userName, description, avatarUrl);
        customerBlo.doUpdateUserExceptPassword(userId, updateParam);
    }

    @Override
    public void updateHotelierInfo(String userId, AdminUpdateUserParam param) {
        String userName = param.getUserName();
        String description = param.getDescription();
        String avatarUrl = param.getAvatarUrl();
        UserUpdateParam updateParam = new UserUpdateParam(userName, description, avatarUrl);
        hotelierBlo.doUpdateUserExceptPassword(userId, updateParam);
    }
}
