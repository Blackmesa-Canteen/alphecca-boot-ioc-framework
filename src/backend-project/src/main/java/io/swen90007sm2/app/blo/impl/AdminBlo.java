package io.swen90007sm2.app.blo.impl;

import cn.hutool.core.util.RandomUtil;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.alpheccaboot.exception.NotImplementedException;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.blo.IAdminBlo;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.common.factory.IdFactory;
import io.swen90007sm2.app.dao.IAdminDao;
import io.swen90007sm2.app.dao.ICustomerDao;
import io.swen90007sm2.app.dao.impl.AdminDao;
import io.swen90007sm2.app.dao.impl.CustomerDao;
import io.swen90007sm2.app.db.helper.UnitOfWorkHelper;
import io.swen90007sm2.app.model.entity.Admin;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.model.param.UserRegisterParam;
import io.swen90007sm2.app.model.param.UserUpdateParam;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.AuthRole;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import io.swen90007sm2.app.security.helper.TokenHelper;
import io.swen90007sm2.app.security.util.SecurityUtil;

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

        Admin admin = getAdminInfoByUserId(userId);

        // Check double login
        if(cache.get(CacheConstant.TOKEN_KEY_PREFIX + userId).isPresent()) {
            throw new RequestException(StatusCodeEnume.ALREADY_LOGIN_EXCEPTION.getMessage(),
                    StatusCodeEnume.ALREADY_LOGIN_EXCEPTION.getCode());
        }

        // check password
        String cypherRecord = admin.getPassword();
        boolean isPasswordCorrect = SecurityUtil.isOriginMatchCypher(password, cypherRecord);
        if(!isPasswordCorrect) {
            throw new RequestException(StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getMessage(),
                    StatusCodeEnume.HOTELIER_AUTH_FAILED_EXCEPTION.getCode());
        }

        // Generate token
        String token = TokenHelper.genAuthToken(userId, AuthRole.ADMIN_ROLE);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        
        cache.put(CacheConstant.TOKEN_KEY_PREFIX + userId,
                authToken, SecurityConstant.DEFAULT_TOKEN_EXPIRATION_TIME_MS, TimeUnit.MILLISECONDS);

        return authToken;
    }

    @Override
    public void doLogout(AuthToken authToken) {
        cache.remove(CacheConstant.TOKEN_KEY_PREFIX + authToken.getUserId());
    }

    @Override
    public void doUpdateUserWithoutPassword(String userId, UserUpdateParam userUpdateParam) {
        throw new NotImplementedException();
    }

    @Override
    public void doUpdateUserPassword(String userId, String originalPassword, String newPassword) {
        throw new NotImplementedException();
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

//    @Override
//    public void doRegisterUser(UserRegisterParam registerParam) {
//        String userName = registerParam.getUserName();
//        String userId = registerParam.getUserId();
//
//        // check existence
//        // will not use cache to prevent inconsistent data
//        // lazy load
//        IAdminDao adminDao = BeanManager.getLazyBeanByClass(AdminDao.class);
//        Admin prevResult = adminDao.findOneByBusinessId(userId);
//
//        if (prevResult != null) {
//            throw new RequestException(
//                    StatusCodeEnume.USER_EXIST_EXCEPTION.getMessage(),
//                    StatusCodeEnume.USER_EXIST_EXCEPTION.getCode()
//            );
//        }
//
//        // encrypt password before store it in db
//        String cypher = SecurityUtil.encrypt(registerParam.getPassword());
//
//        Admin admin = new Admin();
//        admin.setId(IdFactory.genSnowFlakeId());
//        admin.setUserId(userId);
//        admin.setUserName(userName);
//        admin.setPassword(cypher);
//        admin.setDescription("New User");
//        // TODO user unit of work helper
//        UnitOfWorkHelper current = UnitOfWorkHelper.getCurrent();
//        current.registerNew(admin, adminDao);
//    }


}
