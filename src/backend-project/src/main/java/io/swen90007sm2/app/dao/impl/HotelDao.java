package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Lazy;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.app.common.constant.CommonConstant;
import io.swen90007sm2.app.common.util.TimeUtil;
import io.swen90007sm2.app.dao.IHotelDao;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.model.entity.Hotel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 996Worker
 * @description database operation
 * @create 2022-08-23 10:16
 */
@Dao
@Lazy
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
                        "on_sale = ?, min_price = ?, rank = ?, currency = ?,  update_time = ? WHERE id = ?",
                entity.getName(),
                entity.getDescription(),
                entity.getAddress(),
                entity.getPostCode(),
                entity.getOnSale(),
                entity.getMinPrice(),
                entity.getRank(),
                entity.getCurrency(),
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
    public int findTotalCount(boolean onSale) {
        Long totalRows = CRUDTemplate.executeQueryWithOneRes(
                Long.class,
                "SELECT count(*) FROM hotel WHERE on_sale = ?",
                onSale
        );

        return (totalRows != null) ? totalRows.intValue() : 0;
    }

    @Override
    public List<Hotel> findAllByPage(Integer start, Integer rows, boolean onSale) {
        List<Hotel> hotels = CRUDTemplate.executeQueryWithMultiRes(
                Hotel.class,
                "SELECT * FROM hotel WHERE on_sale = ? OFFSET ? LIMIT ? ORDER BY create_time DESC",
                onSale,
                start,
                rows
        );

        return hotels;
    }

    @Override
    public List<Hotel> findAllByPageSortByPrice(Integer start, Integer rows, int sortType, boolean onSale) {
        List<Hotel> hotels = null;

        if (sortType == CommonConstant.SORT_UP) {
            hotels = CRUDTemplate.executeQueryWithMultiRes(
                    Hotel.class,
                    "SELECT * FROM hotel WHERE on_sale = ? OFFSET ? LIMIT ? ORDER BY min_price ASC",
                    onSale,
                    start,
                    rows
            );
        } else {
            hotels = CRUDTemplate.executeQueryWithMultiRes(
                    Hotel.class,
                    "SELECT * FROM hotel WHERE on_sale = ? OFFSET ? LIMIT ? ORDER BY min_price DESC",
                    onSale,
                    start,
                    rows
            );
        }

        return hotels;
    }

    @Override
    public List<Hotel> findAllByPageSortByRank(Integer start, Integer rows, int sortType, boolean onSale) {
        List<Hotel> hotels = null;

        if (sortType == CommonConstant.SORT_UP) {
            hotels = CRUDTemplate.executeQueryWithMultiRes(
                    Hotel.class,
                    "SELECT * FROM hotel WHERE on_sale = ? OFFSET ? LIMIT ? ORDER BY rank ASC",
                    onSale,
                    start,
                    rows
            );
        } else {
            hotels = CRUDTemplate.executeQueryWithMultiRes(
                    Hotel.class,
                    "SELECT * FROM hotel WHERE on_sale = ? OFFSET ? LIMIT ? ORDER BY rank DESC",
                    onSale,
                    start,
                    rows
            );
        }

        return hotels;
    }

    @Override
    public List<Hotel> findAllByPostCode(boolean onSale, String postCode) {
        return CRUDTemplate.executeQueryWithMultiRes(
                Hotel.class,
                "SELECT * FROM hotel WHERE on_sale = ? AND post_code = ? ORDER BY rank DESC",
                onSale,
                postCode
        );
    }

    @Override
    public List<Hotel> findAllByName(boolean onSale, String name) {
        return CRUDTemplate.executeQueryWithMultiRes(
                Hotel.class,
                "SELECT * FROM hotel WHERE on_sale = ? AND name LIKE ? ORDER BY rank DESC",
                onSale,
                "%" + name + "%"
        );
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