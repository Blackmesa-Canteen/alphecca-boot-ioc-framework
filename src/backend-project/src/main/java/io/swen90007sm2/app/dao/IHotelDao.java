package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.entity.Hotel;

import java.util.List;

public interface IHotelDao extends IBaseDao<Hotel>{

    /**
     * Find total record counts.
     * @return int total records
     */
    int findTotalCount(boolean onSale);

    /**
     * find customers by page, from newest to latest
     * @param start result starts from which row
     * @param rows total rows needed
     * @param onSale show hotels of that sale status
     * @return list
     */
    List<Hotel> findAllByPage(Integer start, Integer rows, boolean onSale);

    /**
     * find customers by page, sort with price
     * @param start result starts from which row
     * @param rows total rows needed
     * @param onSale show hotels of that sale status
     * @return list
     */
    List<Hotel> findAllByPageSortByPrice(Integer start, Integer rows, int sortType, boolean onSale);

    /**
     * find all hotels sorted by rank
     */
    List<Hotel> findAllByPageSortByRank(Integer start, Integer rows, int sortType, boolean onSale);

    /**
     * find hotels by post code
     */
    List<Hotel> findAllByPostCode(boolean onSale, String postCode);

    List<Hotel> findAllByName(boolean onSale, String name);

    /**
     * Find Hotel in database by hotelId
     * @param hotelId is business specific Id, not database id
     * @return Customer Entity
     */
    Hotel findOneByBusinessId(String hotelId);

}
