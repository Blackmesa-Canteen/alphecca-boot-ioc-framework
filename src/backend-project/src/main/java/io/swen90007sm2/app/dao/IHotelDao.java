package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.entity.Hotel;

import java.util.List;

public interface IHotelDao extends IBaseDao<Hotel>{

    /**
     * Find total record counts.
     * @return int total records
     */
    int findTotalCount();

    /**
     * find customers by page
     * @param start result starts from which row
     * @param rows total rows needed
     * @return list
     */
    List<Hotel> findAllByPage(Integer start, Integer rows);

    /**
     * Find Customer in database by userID
     * @param hotelId is business specific Id, not database id
     * @return Customer Entity
     */
    Hotel findOneByBusinessId(String hotelId);
}
