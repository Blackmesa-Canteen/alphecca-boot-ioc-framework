package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.model.entity.Hotelier;

public interface IHotelierDao {
    /**
     * Find Hotelier in database by userID
     * @param userId is business specific Id, not database id, in this case, is userId
     * @return Hotelier Entity
     */
    Hotelier findOneByBusinessId(String userId);
}
