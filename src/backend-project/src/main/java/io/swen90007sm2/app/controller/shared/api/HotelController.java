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

    @HandlesRequest(path = "/", method = RequestMethod.GET)
    public R getHotelsByPage(@QueryParam("pageNum") Integer pageNum, @QueryParam("pageSize") Integer pageSize,
                             @QueryParam("sortBy") Integer sortBy, @QueryParam("sortOrder") Integer sortOrder) {
        if (pageNum == null || pageSize == null) {
            return R.error(StatusCodeEnume.GENERAL_REQUEST_EXCEPTION.getCode(),
                    "need to define page info in query param"
            );
        }

        List<Hotel> hotels = null;
        if (sortBy == null || sortOrder == null) {
            // by default, sort by create time, DESC
            hotels  = hotelBlo.getHotelsByPageSortedByCreateTime(pageNum, pageSize, CommonConstant.SORT_DOWN);
        } else {
            if (sortBy.equals(CommonConstant.SORT_BY_RANK)) {
                hotels  = hotelBlo.getHotelsByPageSortedByRank(pageNum, pageSize, sortOrder);
            } else if (sortBy.equals(CommonConstant.SORT_BY_PRICE)) {
                hotels  = hotelBlo.getHotelsByPageSortedByPrice(pageNum, pageSize, sortOrder);
            } else {
                hotels  = hotelBlo.getHotelsByPageSortedByCreateTime(pageNum, pageSize, sortOrder);
            }
        }

        return R.ok().setData(hotels);
    }

    @HandlesRequest(path = "/search", method = RequestMethod.GET)
    public R searchHotels(@QueryParam("hotelName") String hotelName, @QueryParam("postCode") String postCode,
                          @QueryParam("sortBy") Integer sortBy, @QueryParam("sortOrder") Integer sortOrder,
                          @QueryParam("pageNum") Integer pageNum, @QueryParam("pageSize") Integer pageSize) {

        if (pageNum == null || pageSize == null) {
            return R.error(StatusCodeEnume.GENERAL_REQUEST_EXCEPTION.getCode(),
                    "need to define page info in query param"
                    );
        }

        List<Hotel> hotels = null;
        if (StringUtils.isNotEmpty(hotelName)) {
            hotels = hotelBlo.searchHotelsByPageByName(
                    pageNum, pageSize, hotelName, sortBy, sortOrder
            );

        } else if (StringUtils.isNotEmpty(postCode)) {
            hotels = hotelBlo.searchHotelsByPageByPostCode(
                    pageNum, pageSize, postCode, sortBy, sortOrder
            );
        } else {
            return R.error(
                    StatusCodeEnume.GENERAL_REQUEST_EXCEPTION.getCode(),
                    "can not get search result by current search query param,"
            );
        }

        return R.ok().setData(hotels);
    }

}