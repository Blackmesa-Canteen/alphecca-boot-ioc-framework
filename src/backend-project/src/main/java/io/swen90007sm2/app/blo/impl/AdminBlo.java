package io.swen90007sm2.app.blo.impl;

import cn.hutool.core.util.RandomUtil;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.dao.IAdminDao;
import io.swen90007sm2.app.dao.impl.AdminDao;
import io.swen90007sm2.app.model.entity.Admin;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.model.param.UserUpdateParam;
import io.swen90007sm2.app.security.bean.AuthToken;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Blo
public class AdminBlo implements IAdminBlo {

    @AutoInjected
    @Qualifier(name = CacheConstant.OBJECT_CACHE_BEAN)
    ICacheStorage<String, Object> cache;

    @Override
    public AuthToken doLoginAndGenToken(LoginParam loginParam) {
        String userId = loginParam.getUserId();
        String password = loginParam.getPassword();

        return null;
    }

    @Override
    public void doLogout(AuthToken authToken) {

    }

    @Override
    public void doUpdateUserWithoutPassword(String userId, UserUpdateParam userUpdateParam) {

    }

    @Override
    public void doUpdateUserPassword(String userId, String originalPassword, String newPassword) {

    }

    @Override
    public Admin getAdminInfoByToken(String header) {
        return null;
    }

    /* The system only has one admin, the synchronized is not necessary, but implemented for
    * possible future use. */
    @Override
    public Admin getAdminInfoByUserId(String userId) {
        Optional<Object> cacheItem = cache.get(CacheConstant.ENTITY_USER_KEY_PREFIX + userId);
        Admin admin = null;
        if (cacheItem.isEmpty()) {
            // Not necessary at the moment, but implemented for possible future use.
            synchronized (this) {
                if (cacheItem.isEmpty()) {
                    IAdminDao adminDao = BeanManager.getLazyBeanByClass(AdminDao.class);
                    admin = adminDao.findOneByBusinessId(userId);

                    // If the admin userId is not exist
                    if (admin == null) {
                        throw new RequestException(
                                StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getMessage(),
                                StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getCode()
                        );
                    }

                    // If the userId exist
                    cache.put(CacheConstant.ENTITY_USER_KEY_PREFIX + userId, admin
                                , RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS);
                } else {
                    admin = (Admin) cacheItem.get();
                }
            }
        } else {
            admin = (Admin) cacheItem.get();
        }
        return admin;
    }


}
