package io.swen90007sm2.app.controller.hotelier.api;

import io.swen90007sm2.alpheccaboot.annotation.filter.AppliesFilter;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.QueryParam;
import io.swen90007sm2.alpheccaboot.annotation.mvc.RequestJsonBody;
import io.swen90007sm2.alpheccaboot.annotation.validation.Validated;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.alpheccaboot.exception.NotImplementedException;
import io.swen90007sm2.app.blo.IRoomBlo;
import io.swen90007sm2.app.model.entity.Room;
import io.swen90007sm2.app.model.param.CreateRoomParam;
import io.swen90007sm2.app.model.param.UpdateRoomParam;
import io.swen90007sm2.app.model.vo.RoomVo;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import io.swen90007sm2.app.security.helper.TokenHelper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author 996Worker
 * @create 2022-08-30 00:09
 */
@Controller(path = "/api/hotelier/owned_hotel/room")
@Validated
public class RoomController {

    @AutoInjected
    IRoomBlo roomBlo;

    @HandlesRequest(path = "/", method = RequestMethod.POST)
    @AppliesFilter(filterNames = {SecurityConstant.HOTELIER_ROLE_NAME})
    public R createNewRoomToHotel(@RequestJsonBody @Valid CreateRoomParam param) {
        param.setCurrency(param.getCurrency().toUpperCase());
        roomBlo.doCreateRoomToHotel(param);

        return R.ok();
    }

    @HandlesRequest(path = "/", method = RequestMethod.PUT)
    @AppliesFilter(filterNames = {SecurityConstant.HOTELIER_ROLE_NAME})
    public R editRoom(@RequestJsonBody @Valid UpdateRoomParam param) {
        param.setCurrency(param.getCurrency().toUpperCase());
        roomBlo.doUpdateRoom(param);
        return R.ok();
    }

    @HandlesRequest(path = "/", method = RequestMethod.GET)
    @AppliesFilter(filterNames = {SecurityConstant.HOTELIER_ROLE_NAME})
    public R getOwnedHotelRooms(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String userId = authToken.getUserId();
        List<RoomVo> roomVos = roomBlo.getOwnedHotelRoomVos(userId);
        return R.ok().setData(roomVos);
    }

    // edit with lock
    @HandlesRequest(path = "/editing", method = RequestMethod.PUT)
    @AppliesFilter(filterNames = {SecurityConstant.HOTELIER_ROLE_NAME})
    public R editRoomWithLock(@RequestJsonBody @Valid UpdateRoomParam param) {
        param.setCurrency(param.getCurrency().toUpperCase());
        roomBlo.doUpdateRoomWithLock(param);
        return R.ok();
    }

    // get with lock
    @HandlesRequest(path = "/editing", method = RequestMethod.GET)
    @AppliesFilter(filterNames = {SecurityConstant.HOTELIER_ROLE_NAME})
    public R editRoomWithLock(@QueryParam(value = "roomId") String roomId) {
        Room roomEntity = roomBlo.getRoomEntityByRoomIdWithLock(roomId);
        return R.ok().setData(roomEntity);
    }
}