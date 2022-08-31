package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.model.entity.Hotel;
import io.swen90007sm2.app.model.param.HotelParam;
import io.swen90007sm2.app.model.param.UpdateHotelParam;
import io.swen90007sm2.app.model.vo.HotelVo;

import java.util.List;

public interface IHotelBlo {

    /**
     * get all hotels, may be used in admin
     */
    List<Hotel> getAllHotels(Integer pageNum, Integer pageSize);

    /**
     * create a new hotel
     * @param hotelierId userId, owner of the hotel
     * @param hotelParam request param
     */
    void doCreateHotel(String hotelierId, HotelParam hotelParam);

    /**
     * edit a hotel of one hotelier
     * @param hotelierId hotelier person id
     */
    void editOwnedHotel(String hotelierId, HotelParam hotelParam);

    /**
     * update a hotel info by hotel id
     */
    void editHotelByHotelId(UpdateHotelParam updateHotelParam);

    /**
     * get Hotel entity by hotel id
     */
    Hotel getHotelEntityByHotelId(String hotelId);

    /**
     * get a hotel info by owner hotelier id
     */
    HotelVo getHotelInfoByOwnerHotelierId(String hotelierId);

    /**
     * returns hotel entity by hotelId
     * @param hotelId hotelId
     * @return hotel entity
     */
    HotelVo getHotelInfoByHotelId(String hotelId);

    /**
     * page query, used in index to show hotels
     * @param pageNum required page num
     * @param pageSize page size
     * @param order see CommonConstant
     * @return list of hotels
     */
    List<HotelVo> getHotelsByPageSortedByCreateTime(Integer pageNum, Integer pageSize, Integer order);

    /**
     * page query, used in index to show hotels
     */
    List<HotelVo> getHotelsByPageSortedByPrice(Integer pageNum, Integer pageSize, Integer order);

    /**
     * page query, used in index to show hotels
     */
    List<HotelVo> getHotelsByPageSortedByRank(Integer pageNum, Integer pageSize, Integer order);

    /**
     * page query, used in search page. Name fuzzy search, case sensitive.
     */
    List<HotelVo> searchHotelsByPageByName(Integer pageNum, Integer pageSize, String name, Integer sortBy, Integer order);

    /**
     * page query, used in search page. postCode string match.
     */
    List<HotelVo> searchHotelsByPageByPostCode(Integer pageNum, Integer pageSize, String postCode, Integer sortBy, Integer order);
}
