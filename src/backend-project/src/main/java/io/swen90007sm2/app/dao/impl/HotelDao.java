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
    public int findTotalCountByOnSale(boolean onSale) {
        Long totalRows = CRUDTemplate.executeQueryWithOneRes(
                Long.class,
                "SELECT count(*) FROM hotel WHERE on_sale = ?",
                onSale
        );

        return (totalRows != null) ? totalRows.intValue() : 0;
    }

    @Override
    public int findTotalCount() {
        Long totalRows = CRUDTemplate.executeQueryWithOneRes(
                Long.class,
                "SELECT count(*) FROM hotel"
        );

        return (totalRows != null) ? totalRows.intValue() : 0;
    }

    @Override
    public List<Hotel> findAllByPageByDate(Integer start, Integer rows, int sortType) {
        List<Hotel> hotels;
        if (sortType == CommonConstant.SORT_UP) {
            hotels = CRUDTemplate.executeQueryWithMultiRes(
                    Hotel.class,
                    "SELECT * FROM hotel ORDER BY create_time ASC OFFSET ? LIMIT ?",
                    start,
                    rows
            );
        } else {
            hotels = CRUDTemplate.executeQueryWithMultiRes(
                    Hotel.class,
                    "SELECT * FROM hotel ORDER BY create_time DESC OFFSET ? LIMIT ?",
                    start,
                    rows
            );
        }

        return hotels;
    }

    @Override
    public List<Hotel> findAllByPageByDateByOnSale(Integer start, Integer rows, int sortType, boolean onSale) {
        List<Hotel> hotels;
        if (sortType == CommonConstant.SORT_UP) {
            hotels = CRUDTemplate.executeQueryWithMultiRes(
                    Hotel.class,
                    "SELECT * FROM hotel WHERE on_sale = ?  ORDER BY create_time ASC OFFSET ? LIMIT ?",
                    onSale,
                    start,
                    rows
            );
        } else {
            hotels = CRUDTemplate.executeQueryWithMultiRes(
                    Hotel.class,
                    "SELECT * FROM hotel WHERE on_sale = ? ORDER BY create_time DESC OFFSET ? LIMIT ?",
                    onSale,
                    start,
                    rows
            );
        }

        return hotels;
    }

    @Override
    public List<Hotel> findAllByPageSortByPriceByOnSale(Integer start, Integer rows, int sortType, boolean onSale) {
        List<Hotel> hotels = null;

        if (sortType == CommonConstant.SORT_UP) {
            hotels = CRUDTemplate.executeQueryWithMultiRes(
                    Hotel.class,
                    "SELECT * FROM hotel WHERE on_sale = ? ORDER BY min_price ASC OFFSET ? LIMIT ?",
                    onSale,
                    start,
                    rows
            );
        } else {
            hotels = CRUDTemplate.executeQueryWithMultiRes(
                    Hotel.class,
                    "SELECT * FROM hotel WHERE on_sale = ? ORDER BY min_price DESC OFFSET ? LIMIT ?",
                    onSale,
                    start,
                    rows
            );
        }

        return hotels;
    }

    @Override
    public List<Hotel> findAllByPageSortByRankByOnSale(Integer start, Integer rows, int sortType, boolean onSale) {
        List<Hotel> hotels = null;

        if (sortType == CommonConstant.SORT_UP) {
            hotels = CRUDTemplate.executeQueryWithMultiRes(
                    Hotel.class,
                    "SELECT * FROM hotel WHERE on_sale = ? ORDER BY rank ASC OFFSET ? LIMIT ?",
                    onSale,
                    start,
                    rows
            );
        } else {
            hotels = CRUDTemplate.executeQueryWithMultiRes(
                    Hotel.class,
                    "SELECT * FROM hotel WHERE on_sale = ? ORDER BY rank DESC OFFSET ? LIMIT ?",
                    onSale,
                    start,
                    rows
            );
        }

        return hotels;
    }

    @Override
    public List<Hotel> findAllByPostCodeByOnSale(boolean onSale, String postCode) {
        return CRUDTemplate.executeQueryWithMultiRes(
                Hotel.class,
                "SELECT * FROM hotel WHERE on_sale = ? AND post_code = ? ORDER BY rank DESC",
                onSale,
                postCode
        );
    }

    @Override
    public List<Hotel> findAllByNameByOnSale(boolean onSale, String name) {
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