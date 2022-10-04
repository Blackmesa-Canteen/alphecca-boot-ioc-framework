package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Lazy;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.alpheccaboot.exception.InternalException;
import io.swen90007sm2.alpheccaboot.exception.ResourceNotFoundException;
import io.swen90007sm2.app.common.util.TimeUtil;
import io.swen90007sm2.app.dao.IHotelierDao;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.lock.exception.ResourceConflictException;
import io.swen90007sm2.app.model.entity.*;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.pojo.VersionInfoBean;

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
                        "hotel_id = ?, version=? WHERE id = ? and version = ?",
                entity.getPassword(),
                entity.getDescription(),
                entity.getUserName(),
                new java.sql.Timestamp(TimeUtil.now().getTime()),
                entity.getAvatarUrl(),
                entity.getHotelId(),
                entity.getVersion() + 1,
                entity.getId(),
                entity.getVersion()
        );

        if (row == 0) {
            throwConcurrencyException(entity);
        }
        return row;
    }

    @Override
    public void throwConcurrencyException(Hotelier entity) {
        VersionInfoBean currentVersion = CRUDTemplate.executeQueryWithOneRes(
                VersionInfoBean.class,
                "SELECT version, update_time FROM hotelier WHERE id = ?",
                entity.getId()
        );

        if (currentVersion == null) {
            throw new ResourceNotFoundException(
                    "hotelier " + entity.getId() + " has been deleted"
            );
        }

        if (currentVersion.getVersion() == null) {
            throw new InternalException(
                    "Missing version info for optimistic concurrency control in hotelier"
            );
        }

        if (currentVersion.getVersion() > entity.getVersion()) {
            throw new ResourceConflictException(
                    "Rejected: hotelier " + entity.getId() + " has been modified by others at "
                            + currentVersion.getUpdateTime()
            );
        } else {
            throw new InternalException(
                    "unexpected error in throwConcurrencyException for hotelier " + entity.getId()
            );
        }
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
