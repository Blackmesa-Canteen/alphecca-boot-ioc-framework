package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Lazy;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.app.common.util.TimeUtil;
import io.swen90007sm2.app.dao.IHotelierDao;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.model.entity.*;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.entity.Hotelier;

import java.util.List;

@Dao
@Lazy
public class HotelierDao implements IHotelierDao {

    @Override
    public int insertOne(Hotelier hotelier) {
        int row = CRUDTemplate.executeNonQuery(
                "INSERT INTO hotelier (id, user_id, password, description, user_name) values (?, ?, ?, ?, ?)",
                hotelier.getId(),
                hotelier.getUserId(),
                hotelier.getPassword(),
                hotelier.getDescription(),
                hotelier.getUserName()
        );

        return row;
    }

    @Override
    public List<Hotelier> findAllByHotelId(String hotelId) {
        List<Hotelier> hotelierList = CRUDTemplate.executeQueryWithMultiRes(
                Hotelier.class,
                "SELECT * FROM hotelier WHERE hotel_id = ?",
                hotelId
        );
        return hotelierList;
    }

    @Override
    public int updateOne(Hotelier entity) {
        int row = CRUDTemplate.executeNonQuery(
                "UPDATE hotelier SET password = ?, description = ?, " +
                        "user_name = ?, update_time=?, avatar_url = ?, " +
                        "hotel_id = ? WHERE id = ?",
                entity.getPassword(),
                entity.getDescription(),
                entity.getUserName(),
                new java.sql.Date(TimeUtil.now().getTime()),
                entity.getAvatarUrl(),
                entity.getHotelId(),
                entity.getId()
        );
        return row;
    }

    @Override
    public int deleteOne(Hotelier entity) {
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

    @Override
    public List<Hotelier> findAllByPage(Integer start, Integer rows) {
        List<Hotelier> hoteliers = CRUDTemplate.executeQueryWithMultiRes(
                Hotelier.class,
                "SELECT * FROM hotelier OFFSET ? LIMIT ?",
                start,
                rows
        );

        return hoteliers;
    }

    @Override
    public int findTotalCount() {
        Long totalRows = CRUDTemplate.executeQueryWithOneRes(
                Long.class,
                "SELECT count(*) FROM hotelier");

        return (totalRows != null) ? totalRows.intValue() : 0;
    }
}
