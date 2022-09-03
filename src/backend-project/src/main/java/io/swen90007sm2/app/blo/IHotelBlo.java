package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.model.entity.Hotel;
import io.swen90007sm2.app.model.param.HotelParam;
import io.swen90007sm2.app.model.param.UpdateHotelParam;
import io.swen90007sm2.app.model.vo.HotelVo;

import java.math.BigDecimal;
import java.util.List;

public interface IHotelBlo {

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
     * update a hotel minPrice.
     * new price may not be the min price, this method will automatically detect and decide update or not.
     */
    void editeHotelMinPriceByHotelId(String hotelId, String currencyName, BigDecimal newPrice);

    /**
     * used for update hotel's min price, used when a hotel deleted a room
     */
    void updateHotelMinPrice(String hotelId);

    /**
     * get Hotel entity by hotel id
     */
    Hotel getHotelEntityByHotelId(String hotelId);

    /**
     * get a hotel info by owner hotelier id
     */
    HotelVo getHotelInfoByOwnerHotelierId(String hotelierId, String currencyName, Boolean showNotSale);

    /**
     * returns hotel entity by hotelId
     * @param hotelId hotelId
     * @param currencyName currency name
     * @return hotel entity
     */
    HotelVo getHotelInfoByHotelId(String hotelId, String currencyName, Boolean showNotSale);

    /**
     * page query, used in index to show hotels
     * @param currencyName currency name, see CommonConstant
     * @param pageNum required page num
     * @param pageSize page size
     * @param order see CommonConstant
     * @return list of hotels
     */
    List<HotelVo> getHotelsByPageSortedByCreateTime(String currencyName, Integer pageNum, Integer pageSize, Integer order);

    /**
     * page query, used in index to show hotels
     */
    List<HotelVo> getHotelsByPageSortedByPrice(String currencyName, Integer pageNum, Integer pageSize, Integer order);

    /**
     * page query, used in index to show hotels
     */
    List<HotelVo> getHotelsByPageSortedByRank(String currencyName, Integer pageNum, Integer pageSize, Integer order);

    /**
     * page query, used in search page. Name fuzzy search, case sensitive.
     */
    List<HotelVo> searchHotelsByPageByName(String currencyName, Integer pageNum, Integer pageSize, String name, Integer sortBy, Integer order);

    /**
     * page query, used in search page. postCode string match.
     */
    List<HotelVo> searchHotelsByPageByPostCode(String currencyName, Integer pageNum, Integer pageSize, String postCode, Integer sortBy, Integer order);

    /**
     * get all hotels, may be used in admin
     */
    List<HotelVo> getAllHotelsByDate(Integer pageNum, Integer pageSize, Integer order);
}
