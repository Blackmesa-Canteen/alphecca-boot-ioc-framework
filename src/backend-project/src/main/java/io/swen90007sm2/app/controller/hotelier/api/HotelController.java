package io.swen90007sm2.app.controller.hotelier.api;

import io.swen90007sm2.alpheccaboot.annotation.filter.AppliesFilter;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.app.dao.IHotelDao;
import io.swen90007sm2.app.dao.impl.HotelDao;
import io.swen90007sm2.app.security.constant.SecurityConstant;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 996Worker
 * @description
 * @create 2022-08-23 10:12
 */
@Controller(path = "/api/hotelier/hotel")
public class HotelController {

//    @AutoInjected
//    IHotelDao hotelDao;
    @HandlesRequest(path = "/test_lazy", method = RequestMethod.GET)
    public R logout(HttpServletRequest request) {
//        if (hotelDao == null) {
//            return R.error("hotelDao is lazy, is empty");
//        }

        HotelDao hotelDao = BeanManager.getLazyBeanByClass(HotelDao.class);
        if (hotelDao == null) {
            return R.error("hotelDao is still empty!");
        }
        return R.ok();
    }
}