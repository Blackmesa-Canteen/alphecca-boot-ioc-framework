package io.swen90007sm2.app.controller.hotelier.api;

import io.swen90007sm2.alpheccaboot.annotation.filter.AppliesFilter;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.RequestJsonBody;
import io.swen90007sm2.alpheccaboot.annotation.validation.Validated;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.alpheccaboot.exception.NotImplementedException;
import io.swen90007sm2.app.blo.IHotelBlo;
import io.swen90007sm2.app.blo.IHotelierBlo;
import io.swen90007sm2.app.common.constant.CommonConstant;
import io.swen90007sm2.app.model.param.HotelParam;
import io.swen90007sm2.app.model.vo.HotelVo;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import io.swen90007sm2.app.security.helper.TokenHelper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author 996Worker
 * @description
 * @create 2022-08-23 10:12
 */
@Controller(path = "/api/hotelier/owned_hotel")
@Validated
public class HotelController {

    @AutoInjected
    IHotelBlo hotelBlo;

    /**
     * Regester a new Hotel
     */
    @HandlesRequest(path = "/", method = RequestMethod.POST)
    @AppliesFilter(filterNames = {SecurityConstant.HOTELIER_ROLE_NAME})
    public R registerNewHotel(HttpServletRequest request, @RequestJsonBody @Valid HotelParam param) {
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String userId = authToken.getUserId();
        hotelBlo.doCreateHotel(userId, param);

        return R.ok();
    }

    /**
     * edit an existing hotel
     * the hotel is the one that this hotelier is managing
     */
    @HandlesRequest(path = "/", method = RequestMethod.PUT)
    @AppliesFilter(filterNames = {SecurityConstant.HOTELIER_ROLE_NAME})
    public R editOwnedHotel(HttpServletRequest request, @RequestJsonBody @Valid HotelParam param) {
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String userId = authToken.getUserId();
        hotelBlo.editOwnedHotel(userId, param);

        return R.ok();
    }

    /**
     * get the hotelier owned hotel
     */
    @HandlesRequest(path = "/", method = RequestMethod.GET)
    @AppliesFilter(filterNames = {SecurityConstant.HOTELIER_ROLE_NAME})
    public R getOwnedHotelInfo(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String userId = authToken.getUserId();

        HotelVo hotelVo = hotelBlo.getHotelInfoByOwnerHotelierId(userId, CommonConstant.AUD_CURRENCY, true);

        return R.ok().setData(hotelVo);
    }
}