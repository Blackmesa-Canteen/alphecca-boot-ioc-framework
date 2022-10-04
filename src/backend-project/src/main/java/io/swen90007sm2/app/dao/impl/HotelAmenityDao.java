package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Lazy;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.alpheccaboot.exception.NotImplementedException;
import io.swen90007sm2.app.dao.IHotelAmenityDao;
import io.swen90007sm2.app.db.bean.BatchBean;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.model.entity.HotelAmenity;

import java.util.LinkedList;
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
    public void throwConcurrencyException(HotelAmenity entity) {
        throw new NotImplementedException();
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

    @Override
    public List<HotelAmenity> findAllAmenitiesByHotelId(String hotelId) {
        return CRUDTemplate.executeQueryWithMultiRes(
                HotelAmenity.class,
                "SELECT * FROM hotel_hotel_amenity INNER JOIN hotel_amenity USING (amenity_id) WHERE hotel_id = ?",
                hotelId
        );
    }

    @Override
    public void addAmenityIdsToHotel(List<String> amenityIds, String hotelId) {
        String insertAssociationSql = "INSERT INTO hotel_hotel_amenity (amenity_id, hotel_id) values (?, ?)";

        List<BatchBean> batchBeans = new LinkedList<>();
        for (String amenityId : amenityIds) {
            BatchBean batchBean = new BatchBean(
                    insertAssociationSql,
                    amenityId,
                    hotelId
            );

            batchBeans.add(batchBean);
        }

        CRUDTemplate.executeNonQueryBatch(batchBeans);
    }

    @Override
    public int clearAmenityIdsForHotel(String hotelId) {
        return CRUDTemplate.executeNonQuery(
                "DELETE FROM hotel_hotel_amenity WHERE hotel_id = ?",
                hotelId
        );
    }
}