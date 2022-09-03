package io.swen90007sm2.app.controller.shared.api;

import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.QueryParam;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.blo.IHotelBlo;
import io.swen90007sm2.app.common.constant.CommonConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.model.entity.Hotel;
import io.swen90007sm2.app.model.vo.HotelVo;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author 996Worker
 * @description public hotel controller
 * @create 2022-08-25 21:52
 */
@Controller(path = "/api/shared/hotel")
public class HotelController {

    @AutoInjected
    IHotelBlo hotelBlo;

    @HandlesRequest(path = "/query", method = RequestMethod.GET)
    public R getOnSaleHotelInfoByHotelId(@QueryParam("hotelId") String hotelId, @QueryParam("currency") String currency) {
        HotelVo hotelVo = hotelBlo.getHotelInfoByHotelId(hotelId, currency.toUpperCase(), false);
        return R.ok().setData(hotelVo);
    }

    /**
     * used in index, show all hotels
     * @param pageNum page number
     * @param pageSize number of results in one page
     * @param sortBy see CommonConstant.java
     * @param sortOrder see CommonConstant.java
     */
    @HandlesRequest(path = "/", method = RequestMethod.GET)
    public R getOnSaleHotelsByPage(@QueryParam("pageNum") Integer pageNum, @QueryParam("pageSize") Integer pageSize,
                             @QueryParam("sortBy") Integer sortBy, @QueryParam("sortOrder") Integer sortOrder,
                             @QueryParam("currency") String currency) {
        if (pageNum == null || pageSize == null) {
            return R.error(StatusCodeEnume.GENERAL_REQUEST_EXCEPTION.getCode(),
                    "need to define page info in query param"
            );
        }

        if (StringUtils.isEmpty(currency)) {
            currency = CommonConstant.AUD_CURRENCY;
        }

        List<HotelVo> hotelVos = null;
        if (sortBy == null || sortOrder == null) {
            // by default, sort by create time, DESC
            hotelVos  = hotelBlo.getHotelsByPageSortedByCreateTime(currency.toUpperCase(), pageNum, pageSize, CommonConstant.SORT_DOWN);
        } else {
            if (sortBy.equals(CommonConstant.SORT_BY_RANK)) {
                hotelVos  = hotelBlo.getHotelsByPageSortedByRank(currency.toUpperCase(), pageNum, pageSize, sortOrder);
            } else if (sortBy.equals(CommonConstant.SORT_BY_PRICE)) {
                hotelVos  = hotelBlo.getHotelsByPageSortedByPrice(currency.toUpperCase(), pageNum, pageSize, sortOrder);
            } else {
                hotelVos  = hotelBlo.getHotelsByPageSortedByCreateTime(currency.toUpperCase(), pageNum, pageSize, sortOrder);
            }
        }

        return R.ok().setData(hotelVos);
    }

    /**
     * used in searching hotels
     * @param hotelName hotelName. Fuzzy search. Case sensitive.
     * @param postCode Can not show up with hotelName at the same time. Postcode search
     * @param sortBy see CommonConstant.java
     * @param sortOrder see CommonConstant.java
     * @param pageNum page number
     * @param pageSize page size
     * @return
     */
    @HandlesRequest(path = "/search", method = RequestMethod.GET)
    public R searchOnSaleHotels(@QueryParam("hotelName") String hotelName, @QueryParam("postCode") String postCode,
                          @QueryParam("sortBy") Integer sortBy, @QueryParam("sortOrder") Integer sortOrder,
                          @QueryParam("pageNum") Integer pageNum, @QueryParam("pageSize") Integer pageSize,
                          @QueryParam("currency") String currency) {

        if (pageNum == null || pageSize == null) {
            return R.error(StatusCodeEnume.GENERAL_REQUEST_EXCEPTION.getCode(),
                    "need to define page info in query param"
                    );
        }

        if (StringUtils.isEmpty(currency)) {
            currency = CommonConstant.AUD_CURRENCY;
        }

        List<HotelVo> hotelVos = null;
        if (StringUtils.isNotEmpty(hotelName)) {
            hotelVos = hotelBlo.searchHotelsByPageByName(
                    currency.toUpperCase(), pageNum, pageSize, hotelName, sortBy, sortOrder
            );

        } else if (StringUtils.isNotEmpty(postCode)) {
            hotelVos = hotelBlo.searchHotelsByPageByPostCode(
                    currency.toUpperCase(), pageNum, pageSize, postCode, sortBy, sortOrder
            );
        } else {
            return R.error(
                    StatusCodeEnume.GENERAL_REQUEST_EXCEPTION.getCode(),
                    "can not get search result by current search query param,"
            );
        }

        return R.ok().setData(hotelVos);
    }

}