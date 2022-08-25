package io.swen90007sm2.app.controller.shared.api;

import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.QueryParam;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.blo.IHotelBlo;

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
        return R.ok();
    }

    @HandlesRequest(path = "/search", method = RequestMethod.GET)
    public R searchHotels(@QueryParam("hotelName") String hotelName, @QueryParam("postCode") String postCode,
                          @QueryParam("sortBy") Integer sortBy, @QueryParam("sortOrder") Integer sortOrder,
                          @QueryParam("pageNum") Integer pageNum, @QueryParam("pageSize") Integer pageSize) {
        return R.ok();
    }

}