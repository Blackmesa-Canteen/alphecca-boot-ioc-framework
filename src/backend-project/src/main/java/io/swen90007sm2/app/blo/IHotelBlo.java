package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.model.entity.Hotel;
import io.swen90007sm2.app.model.param.CreateHotelParam;
import io.swen90007sm2.app.model.vo.HotelVo;

import java.util.List;

public interface IHotelBlo {

    /**
     * create a new hotel
     * @param hotelierId userId, owner of the hotel
     * @param createHotelParam request param
     */
    void doCreateHotel(String hotelierId, CreateHotelParam createHotelParam);

    /**
     * edit a hotel of one hotelier
     * @param hotelierId hotelier person id
     */
    void editOwnedHotel(String hotelierId, CreateHotelParam createHotelParam);

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
