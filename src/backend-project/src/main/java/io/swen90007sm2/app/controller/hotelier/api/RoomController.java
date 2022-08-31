package io.swen90007sm2.app.controller.hotelier.api;

import io.swen90007sm2.alpheccaboot.annotation.filter.AppliesFilter;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.RequestJsonBody;
import io.swen90007sm2.alpheccaboot.annotation.validation.Validated;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.blo.IRoomBlo;
import io.swen90007sm2.app.model.param.CreateRoomParam;
import io.swen90007sm2.app.model.param.UpdateRoomParam;
import io.swen90007sm2.app.security.constant.SecurityConstant;

import javax.validation.Valid;

/**
 * @author 996Worker
 * @create 2022-08-30 00:09
 */
@Controller(path = "/api/hotelier/room")
@Validated
public class RoomController {

    @AutoInjected
    IRoomBlo roomBlo;

    @HandlesRequest(path = "/", method = RequestMethod.POST)
    @AppliesFilter(filterNames = {SecurityConstant.HOTELIER_ROLE_NAME})
    public R createNewRoomToHotel(@RequestJsonBody @Valid CreateRoomParam param) {
        roomBlo.doCreateRoomToHotel(param);

        return R.ok();
    }

    @HandlesRequest(path = "/", method = RequestMethod.PUT)
    @AppliesFilter(filterNames = {SecurityConstant.HOTELIER_ROLE_NAME})
    public R editRoom(@RequestJsonBody @Valid UpdateRoomParam param) {
        roomBlo.doUpdateRoom(param);

        return R.ok();
    }
}