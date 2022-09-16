package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.model.entity.Hotel;

import java.util.List;

public interface IHotelDao extends IBaseDao<Hotel>{

    /**
     * Find total record counts based on onSale status
     * @return int total records
     */
    int findTotalCountByOnSale(boolean onSale);

    /**
     * Find total counts
     * @return
     */
    int findTotalCount();


    /**
     * find all, no worry about onsale. used for admin
     */
    List<Hotel> findAllByPageByDate(Integer start, Integer rows, int sortType);

    /**
     * find customers by page, from newest to latest
     * @param start result starts from which row
     * @param rows total rows needed
     * @param sortType up or down, defined in common constant
     * @param onSale show hotels of that sale status
     * @return list
     */
    List<Hotel> findAllByPageByDateByOnSale(Integer start, Integer rows, int sortType, boolean onSale);

    /**
     * find customers by page, sort with price
     * @param start result starts from which row
     * @param rows total rows needed
     * @param onSale show hotels of that sale status
     * @return list
     */
    List<Hotel> findAllByPageSortByPriceByOnSale(Integer start, Integer rows, int sortType, boolean onSale);

    /**
     * find all hotels sorted by rank
     */
    List<Hotel> findAllByPageSortByRankByOnSale(Integer start, Integer rows, int sortType, boolean onSale);

    /**
     * find hotels by post code
     */
    List<Hotel> findAllByPostCodeByOnSale(boolean onSale, String postCode);

    List<Hotel> findAllByNameByOnSale(boolean onSale, String name);

    /**
     * Find Hotel in database by hotelId
     * @param hotelId is business specific Id, not database id
     * @return Customer Entity
     */
    Hotel findOneByBusinessId(String hotelId);

}
