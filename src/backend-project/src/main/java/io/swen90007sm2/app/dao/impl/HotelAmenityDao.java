package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Lazy;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.app.dao.IHotelAmenityDao;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.model.entity.HotelAmenity;

import java.util.List;

/**
 * @author 996Worker
 * @description
 * @create 2022-08-24 21:44
 */
@Dao
@Lazy
public class HotelAmenityDao implements IHotelAmenityDao {

    @Override
    public int insertOne(HotelAmenity entity) {
        return CRUDTemplate.executeNonQuery(
                "INSERT INTO hotel_amenity (id, amenity_id, icon_url, description) values (?, ?, ?, ?)",
                entity.getId(),
                entity.getAmenityId(),
                entity.getIconUrl(),
                entity.getDescription()
        );
    }

    @Override
    public int updateOne(HotelAmenity entity) {
        return CRUDTemplate.executeNonQuery(
                "UPDATE hotel_amenity SET icon_url = ?, description = ? WHERE id = ?",
                entity.getIconUrl(),
                entity.getDescription(),
                entity.getId()
        );
    }

    @Override
    public int deleteOne(HotelAmenity entity) {
        return CRUDTemplate.executeNonQuery(
                "DELETE FROM hotel_amenity WHERE id = ?",
                entity.getId()
        );
    }

    @Override
    public List<HotelAmenity> findAllAmenities() {
        return CRUDTemplate.executeQueryWithMultiRes(
                HotelAmenity.class,
                "SELECT * FROM hotel_amenity"
        );
    }

    @Override
    public HotelAmenity findOneAmenityByAmenityId(String amenityId) {
        return CRUDTemplate.executeQueryWithOneRes(
                HotelAmenity.class,
                "SELECT * FROM hotel_amenity WHERE amenity_id = ?",
                amenityId
        );
    }
}