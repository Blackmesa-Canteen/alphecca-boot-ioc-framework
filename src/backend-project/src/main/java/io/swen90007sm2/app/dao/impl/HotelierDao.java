package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.app.dao.IHotelierDao;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.entity.Hotelier;

public class HotelierDao implements IHotelierDao {


    /**
     * Find Hotelier in database by userID
     *
     * @param userId is business specific Id, not database id, in this case, is userId
     * @return Hotelier Entity
     */
    @Override
    public Hotelier findOneByBusinessId(String userId) {
        Hotelier hotelierBean = CRUDTemplate.executeQueryWithOneRes(
                Hotelier.class,
                "SELECT * FROM hotelier WHERE user_id = ?",
                userId
        );


        return hotelierBean;
    }
}
