package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.app.dao.IHotelierDao;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.model.entity.BaseEntity;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.entity.Hotelier;

@Dao
public class HotelierDao implements IHotelierDao {

    @Override
    public int insertOne(BaseEntity entity) {
        return 0;
    }

    @Override
    public int updateOne(BaseEntity entity) {
        return 0;
    }

    @Override
    public int deleteOne(BaseEntity entity) {
        return 0;
    }

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
