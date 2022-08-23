package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.app.common.util.TimeUtil;
import io.swen90007sm2.app.dao.IHotelDao;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.entity.Hotel;

import java.util.List;

/**
 * @author 996Worker
 * @description database operation
 * @create 2022-08-23 10:16
 */
@Dao
public class HotelDao implements IHotelDao{

    @Override
    public int insertOne(Hotel entity) {
        int row = CRUDTemplate.executeNonQuery(
                "INSERT INTO hotel (id, hotel_id, name, description, address, post_code, on_sale) " +
                        "values (?, ?, ?, ?, ?, ?, ?)",
                entity.getId(),
                entity.getHotelId(),
                entity.getName(),
                entity.getDescription(),
                entity.getAddress(),
                entity.getPostCode(),
                entity.getOnSale()
        );

        return row;
    }

    @Override
    public int updateOne(Hotel entity) {
        int row = CRUDTemplate.executeNonQuery(
                "UPDATE hotel SET " +
                        "name = ?, description = ?, address = ?, post_code = ?, " +
                        "on_sale = ?, min_price = ?, rank = ?,  update_time = ? WHERE id = ?",
                entity.getName(),
                entity.getDescription(),
                entity.getAddress(),
                entity.getPostCode(),
                entity.getOnSale(),
                entity.getMinPrice(),
                entity.getRank(),
                new java.sql.Date(TimeUtil.now().getTime()),
                entity.getId()
        );

        return row;
    }

    @Override
    public int deleteOne(Hotel entity) {
        int row = CRUDTemplate.executeNonQuery(
                "DELETE FROM hotel WHERE id = ?",
                entity.getId()
        );

        return row;
    }

    @Override
    public int findTotalCount() {
        Long totalRows = CRUDTemplate.executeQueryWithOneRes(
                Long.class,
                "SELECT count(*) FROM hotel");

        return (totalRows != null) ? totalRows.intValue() : 0;
    }

    @Override
    public List<Hotel> findAllByPage(Integer start, Integer rows) {
        List<Hotel> hotels = CRUDTemplate.executeQueryWithMultiRes(
                Hotel.class,
                "SELECT * FROM hotel OFFSET ? LIMIT ?",
                start,
                rows
        );

        return hotels;
    }

    @Override
    public Hotel findOneByBusinessId(String hotelId) {
        Hotel hotel = CRUDTemplate.executeQueryWithOneRes(
                Hotel.class,
                "SELECT * FROM hotel WHERE hotel_id = ?",
                hotelId
        );

        return hotel;
    }
}