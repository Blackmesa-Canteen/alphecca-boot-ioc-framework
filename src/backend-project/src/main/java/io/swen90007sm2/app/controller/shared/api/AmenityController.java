package io.swen90007sm2.app.controller.shared.api;

import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.QueryParam;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.blo.IHotelAmenityBlo;
import io.swen90007sm2.app.blo.IRoomAmenityBlo;
import io.swen90007sm2.app.model.entity.HotelAmenity;
import io.swen90007sm2.app.model.entity.RoomAmenity;

import java.util.List;

/**
 * @author 996Worker
 * @description
 * @create 2022-08-25 20:43
 */
@Controller(path = "/api/shared/amenity")
public class AmenityController {

    @AutoInjected
    IHotelAmenityBlo hotelAmenityBlo;

    @AutoInjected
    IRoomAmenityBlo roomAmenityBlo;

    /**
     * list all hotel amenities
     */
    @HandlesRequest(path = "/hotel_amenity/all", method = RequestMethod.GET)
    public R getAllHotelAmenities() {
        List<HotelAmenity> allAmenities = hotelAmenityBlo.getAllAmenities();
        return R.ok().setData(allAmenities);
    }

    /**
     * get a hotel amenity with amenity id
     * @param amenityId string, amenityId. NOT id
     */
    @HandlesRequest(path = "/hotel_amenity", method = RequestMethod.GET)
    public R getHotelAmenityById(@QueryParam(value = "amenityId") String amenityId) {
        HotelAmenity res = hotelAmenityBlo.getHotelAmenityInfoByAmenityId(amenityId);
        return R.ok().setData(res);
    }

    /**
     * get all room amenities
     */
    @HandlesRequest(path = "/room_amenity/all", method = RequestMethod.GET)
    public R getAllRoomAmenities() {
        List<RoomAmenity> roomAmenities = roomAmenityBlo.getAllAmenities();
        return R.ok().setData(roomAmenities);
    }

    @HandlesRequest(path = "/room_amenity", method = RequestMethod.GET)
    public R getRoomAmenityById(@QueryParam(value = "amenityId") String amenityId) {
        RoomAmenity roomAmenity = roomAmenityBlo.getRoomAmenityInfoByAmenityId(amenityId);
        return R.ok().setData(roomAmenity);
    }


}