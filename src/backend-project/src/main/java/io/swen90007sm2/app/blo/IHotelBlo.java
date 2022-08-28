package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.model.entity.Hotel;
import io.swen90007sm2.app.model.param.CreateHotelParam;

import java.util.List;

public interface IHotelBlo {

    /**
     * create a new hotel
     * @param hotelierId userId, owner of the hotel
     * @param createHotelParam request param
     */
    void doCreateHotel(String hotelierId, CreateHotelParam createHotelParam);

    /**
     * returns hotel entity by hotelId
     * @param hotelId hotelId
     * @return hotel entity
     */
    Hotel getHotelInfoByHotelId(String hotelId);

    /**
     * page query, used in index to show hotels
     * @param pageNum required page num
     * @param pageSize page size
     * @param order see CommonConstant
     * @return list of hotels
     */
    List<Hotel> getHotelsByPageSortedByCreateTime(Integer pageNum, Integer pageSize, Integer order);

    /**
     * page query, used in index to show hotels
     */
    List<Hotel> getHotelsByPageSortedByPrice(Integer pageNum, Integer pageSize, Integer order);

    /**
     * page query, used in index to show hotels
     */
    List<Hotel> getHotelsByPageSortedByRank(Integer pageNum, Integer pageSize, Integer order);

    /**
     * page query, used in search page. Name fuzzy search, case sensitive.
     */
    List<Hotel> searchHotelsByPageByName(Integer pageNum, Integer pageSize, String name, Integer sortBy, Integer order);

    /**
     * page query, used in search page. postCode string match.
     */
    List<Hotel> searchHotelsByPageByPostCode(Integer pageNum, Integer pageSize, String postCode, Integer sortBy, Integer order);
}
