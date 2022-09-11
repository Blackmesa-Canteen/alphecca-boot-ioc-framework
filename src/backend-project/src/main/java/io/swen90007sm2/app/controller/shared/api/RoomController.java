package io.swen90007sm2.app.controller.shared.api;

import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.QueryParam;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.blo.IRoomBlo;
import io.swen90007sm2.app.common.constant.CommonConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.model.vo.RoomVo;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author 996Worker
 * @description
 * @create 2022-08-31 17:01
 */

@Controller(path = "/api/shared/room")
public class RoomController {

    @AutoInjected
    IRoomBlo roomBlo;

    @HandlesRequest(path = "/query", method = RequestMethod.GET)
    public R getOnSaleRoomInfo(@QueryParam("roomId") String roomId, @QueryParam("hotelId") String hotelId,
                         @QueryParam("currency") String currency) {
        if (StringUtils.isEmpty(currency)) {
            currency = CommonConstant.AUD_CURRENCY;
        }
        if (StringUtils.isNotEmpty(roomId)) {
            RoomVo roomVo = roomBlo.getRoomInfoByRoomId(roomId, currency.toUpperCase(), false);
            return R.ok().setData(roomVo);
        } else if (StringUtils.isNotEmpty(hotelId)) {
            List<RoomVo> roomVos = roomBlo.getAllRoomsFromHotelId(hotelId, currency.toUpperCase(), false);
            return R.ok().setData(roomVos);
        } else {
            throw new RequestException(
                    "query room info param should have roomId or hotelId",
                    StatusCodeEnume.GENERAL_REQUEST_EXCEPTION.getCode()
            );
        }
    }


}