package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Lazy;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.app.dao.IRoomAmenityDao;
import io.swen90007sm2.app.db.bean.BatchBean;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.model.entity.HotelAmenity;
import io.swen90007sm2.app.model.entity.RoomAmenity;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 996Worker
 * @description
 * @create 2022-08-30 13:53
 */
@Dao
@Lazy
public class RoomAmenityDao implements IRoomAmenityDao {

    @Override
    public int insertOne(RoomAmenity entity) {
        return CRUDTemplate.executeNonQuery(
                "INSERT INTO room_amenity (id, amenity_id, icon_url, description) values (?, ?, ?, ?)",
                entity.getId(),
                entity.getAmenityId(),
                entity.getIconUrl(),
                entity.getDescription()
        );
    }

    @Override
    public int updateOne(RoomAmenity entity) {
        return CRUDTemplate.executeNonQuery(
                "UPDATE room_amenity SET icon_url = ?, description = ? WHERE id = ?",
                entity.getIconUrl(),
                entity.getDescription(),
                entity.getId()
        );
    }

    @Override
    public int deleteOne(RoomAmenity entity) {
        return CRUDTemplate.executeNonQuery(
                "DELETE FROM room_amenity WHERE id = ?",
                entity.getId()
        );
    }

    @Override
    public List<RoomAmenity> findAllAmenities() {
        return CRUDTemplate.executeQueryWithMultiRes(
                RoomAmenity.class,
                "SELECT * FROM room_amenity"
        );
    }

    @Override
    public RoomAmenity findAmenityBeAmenityId(String amenityId) {
        return CRUDTemplate.executeQueryWithOneRes(
                RoomAmenity.class,
                "SELECT * FROM room_amenity WHERE amenity_id = ?",
                amenityId
        );
    }

    @Override
    public List<RoomAmenity> findAllAmenitiesByRoomId(String roomId) {
        return CRUDTemplate.executeQueryWithMultiRes(
                RoomAmenity.class,
                "SELECT * FROM room_room_amenity INNER JOIN room_amenity USING (amenity_id) WHERE room_id = ?",
                roomId
        );
    }

    @Override
    public void addAmenityIdsToRoom(List<String> amenityIds, String roomId) {
        String insertAssociationSql = "INSERT INTO room_room_amenity (amenity_id, room_id) values (?, ?)";

        List<BatchBean> batchBeans = new LinkedList<>();
        for (String amenityId : amenityIds) {
            BatchBean batchBean = new BatchBean(
                    insertAssociationSql,
                    amenityId,
                    roomId
            );

            batchBeans.add(batchBean);
        }

        CRUDTemplate.executeNonQueryBatch(batchBeans);
    }

    @Override
    public int clearAmenityIdsForRoom(String roomId) {
        return CRUDTemplate.executeNonQuery(
                "DELETE FROM hotel_amenity WHERE room_id = ?",
                roomId
        );
    }
}