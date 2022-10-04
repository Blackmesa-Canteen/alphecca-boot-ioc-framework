package io.swen90007sm2.app.blo.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.ioc.Qualifier;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.blo.IHotelierBlo;
import io.swen90007sm2.app.cache.ICacheStorage;
import io.swen90007sm2.app.cache.constant.CacheConstant;
import io.swen90007sm2.app.common.constant.CommonConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.common.factory.IdFactory;
import io.swen90007sm2.app.dao.ICustomerDao;
import io.swen90007sm2.app.dao.IHotelierDao;
import io.swen90007sm2.app.dao.impl.CustomerDao;
import io.swen90007sm2.app.dao.impl.HotelierDao;
import io.swen90007sm2.app.db.bean.PageBean;
import io.swen90007sm2.app.db.helper.UnitOfWorkHelper;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.param.LoginParam;
import io.swen90007sm2.app.model.param.UserRegisterParam;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.AuthRole;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import io.swen90007sm2.app.security.helper.TokenHelper;
import io.swen90007sm2.app.security.util.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Blo
public class HotelierBlo implements IHotelierBlo {

    @AutoInjected
    @Qualifier(name = CacheConstant.OBJECT_CACHE_BEAN)
    ICacheStorage<String, Object> cache;
    @Override
    public AuthToken doLoginAndGenToken(LoginParam loginParam) {
        // get role from db
        String userId = loginParam.getUserId();
        String password = loginParam.getPassword();

        /*
         * Identity Map's cache-Aside implementation
         * <br/>
         * if the data exists in cache, get from cache,
         * if not, get from db.
         * <br/>
         * data in the cache will be expired to guarantee data eventually consistent
         * <br/>
         */
        Hotelier hotelier = getHotelierInfoByUserId(userId);

        // prevent double login, check token cache
        // watch out: different Cache prefix
        if (cache.get(CacheConstant.TOKEN_KEY_PREFIX + userId).isPresent()) {
            throw new RequestException(
                    StatusCodeEnume.ALREADY_LOGIN_EXCEPTION.getMessage(),
                    StatusCodeEnume.ALREADY_LOGIN_EXCEPTION.getCode()
            );
        }

        // check username password with db
        String cypherRecord = hotelier.getPassword();
        boolean isPasswordMatched = SecurityUtil.isOriginMatchCypher(password, cypherRecord);
        if (!isPasswordMatched) {
            throw new RequestException (
                    StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getMessage(),
                    StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getCode());
        }

        // generate token
        String token = TokenHelper.genAuthToken(userId, AuthRole.HOTELIER_ROLE);

        // store the token into token cache with expiration time
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        cache.put(
                CacheConstant.TOKEN_KEY_PREFIX + userId,
                authToken,
                SecurityConstant.DEFAULT_TOKEN_EXPIRATION_TIME_MS,
                TimeUnit.MILLISECONDS);

        // return the token string
        return authToken;
    }

    @Override
    public void doRegisterUser(UserRegisterParam userRegisterParam) {
        String userName = userRegisterParam.getUserName();
        String userId = userRegisterParam.getUserId();

        // check existence
        // will not use cache to prevent inconsistent data
        IHotelierDao hotelierDao = BeanManager.getLazyBeanByClass(HotelierDao.class);
        Hotelier prevResult = hotelierDao.findOneByBusinessId(userId);

        if (prevResult != null) {
            throw new RequestException(
                    StatusCodeEnume.USER_EXIST_EXCEPTION.getMessage(),
                    StatusCodeEnume.USER_EXIST_EXCEPTION.getCode()
            );
        }

        // encrypt password before store it in db
        String cypher = SecurityUtil.encrypt(userRegisterParam.getPassword());

        Hotelier hotelier = new Hotelier();
        hotelier.setId(IdFactory.genSnowFlakeId());
        hotelier.setUserId(userId);
        hotelier.setUserName(userName);
        hotelier.setPassword(cypher);
        hotelier.setDescription("New User");


        UnitOfWorkHelper current = UnitOfWorkHelper.getCurrent();
        current.registerNew(hotelier, hotelierDao);
    }

    @Override
    public void doLogout(AuthToken authToken) {
        cache.remove(CacheConstant.TOKEN_KEY_PREFIX + authToken.getUserId());
    }


    @Override
    // TODO need lock
    public void doUpdateUserPassword(String userId, String originalPassword, String newPassword) {
        Hotelier hotelierBean = getHotelierInfoByUserId(userId);

        String originalCypher = hotelierBean.getPassword();

        if (!SecurityUtil.isOriginMatchCypher(originalPassword, originalCypher)) {
            throw new RequestException(
                    StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getMessage(),
                    StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getCode()
            );
        }
        hotelierBean.setPassword(SecurityUtil.encrypt(newPassword));
        IHotelierDao hotelierDao = BeanManager.getLazyBeanByClass(HotelierDao.class);
        UnitOfWorkHelper.getCurrent().registerDirty(hotelierBean, hotelierDao, CacheConstant.ENTITY_USER_KEY_PREFIX + userId);
        cache.remove(CacheConstant.TOKEN_KEY_PREFIX + userId);
    }

    @Override
    public Hotelier getHotelierInfoByToken(String header) {
        AuthToken authToken = TokenHelper.parseAuthTokenString(header);
        String userId = authToken.getUserId();
        checkTokenValidity(authToken);
        Hotelier hotelierBean = getHotelierInfoByUserId(userId);
        Hotelier res = new Hotelier();
        // Return the hotelier info without password.
        BeanUtil.copyProperties(hotelierBean, res);
        res.setPassword(CommonConstant.NULL);
        return res;
    }

    @Override
    public Hotelier getHotelierInfoByUserId(String userId) {
        Optional<Object> cacheItem = cache.get(CacheConstant.ENTITY_USER_KEY_PREFIX + userId);
        Hotelier hotelier = null;
        if (cacheItem.isEmpty()) {
            synchronized (this) {
                cacheItem = cache.get(CacheConstant.ENTITY_USER_KEY_PREFIX + userId);
                if (cacheItem.isEmpty()) {
                    IHotelierDao hotelierDao = BeanManager.getLazyBeanByClass(HotelierDao.class);
                    hotelier = hotelierDao.findOneByBusinessId(userId);
                    // if no such hotelier
                    if (hotelier == null) {
                        throw new RequestException (
                                StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getMessage(),
                                StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getCode());
                    }

                    // use randomed expiration time to prevent Cache Avalanche
                    cache.put(
                            CacheConstant.ENTITY_USER_KEY_PREFIX + userId,
                            hotelier,
                            RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                            TimeUnit.MILLISECONDS
                    );
                } else {
                    hotelier = (Hotelier) cacheItem.get();
                }
            }
        } else {
            hotelier = (Hotelier) cacheItem.get();
        }
        return hotelier;
    }

    private void checkTokenValidity(AuthToken authToken) {
        String key = CacheConstant.TOKEN_KEY_PREFIX + authToken.getUserId();
        Optional<Object> tokenRecord = cache.get(key);
        if (tokenRecord.isEmpty()) {
            throw new RequestException(
                    StatusCodeEnume.NOT_LOGIN_EXCEPTION.getMessage(),
                    StatusCodeEnume.NOT_LOGIN_EXCEPTION.getCode()
            );
        } else {
            AuthToken tokenBeanInCache = (AuthToken)tokenRecord.get();
            if (!authToken.getToken().equals(tokenBeanInCache.getToken())) {
                throw new RequestException(
                        StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getMessage(),
                        StatusCodeEnume.LOGIN_AUTH_EXCEPTION.getCode()
                );
            }
        }
    }

    @Override
    public PageBean<Hotelier> getHotelierByPage(int pageNo, int pageSize) {

        PageBean<Hotelier> pageBean = null;
        List<Hotelier> hoteliers = null;
        IHotelierDao hotelierDao = BeanManager.getLazyBeanByClass(HotelierDao.class);

        // ensure the data consistency within multi query from db
        synchronized (this) {
            // get total records num, which is important for paging
            int totalRows = hotelierDao.findTotalCount();

            // init page dto
            pageBean = new PageBean<>(
                    pageSize,
                    totalRows,
                    pageNo
            );

            // get start row for page query
            int start = pageBean.getStartRow();

            hoteliers = hotelierDao.findAllByPage(start, pageSize);
        }

        // use result from db to update the cache
        hoteliers.forEach(hotelier -> {
            if (hotelier != null) {
                cache.put(
                        CacheConstant.ENTITY_USER_KEY_PREFIX + hotelier.getUserId(),
                        hotelier,
                        RandomUtil.randomLong(CacheConstant.CACHE_NORMAL_EXPIRATION_PERIOD_MAX),
                        TimeUnit.MILLISECONDS
                );
            }
        });

        // remove sensitive info,
        // need to copy to prevent impact on cached data
        List<Hotelier> res = BeanUtil.copyToList(hoteliers, Hotelier.class);
        res.forEach(hotelier -> hotelier.setPassword(CommonConstant.NULL));
        pageBean.setBeans(res);

        return pageBean;
    }
}
