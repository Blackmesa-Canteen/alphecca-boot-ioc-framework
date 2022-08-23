package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Lazy;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.app.common.util.TimeUtil;
import io.swen90007sm2.app.dao.IHotelDao;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.model.entity.Hotel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        return HotelMapper.getInstance().insertOne(entity);
    }

    @Override
    public int updateOne(Hotel entity) {
        return HotelMapper.getInstance().updateOne(entity);
    }

    @Override
    public int deleteOne(Hotel entity) {
        return HotelMapper.getInstance().deleteOne(entity);
    }

    @Override
    public int findTotalCount() {
       return HotelMapper.getInstance().findTotalCount();
    }

    @Override
    public List<Hotel> findAllByPage(Integer start, Integer rows) {
       return HotelMapper.getInstance().findAllByPage(start, rows);
    }

    @Override
    public Hotel findOneByBusinessId(String hotelId) {
        return HotelMapper.getInstance().findOneByBusinessId(hotelId);
    }

    /**
     * helper class for lazy load
     */
    private static class HotelMapper {
        // Lazy loaded singleton
        private static volatile HotelMapper instance = null;
        private static final Logger LOGGER = LoggerFactory.getLogger(HotelMapper.class);
        private static HotelMapper getInstance() {

            // check existence without synchronized lock
            if (instance == null) {
                synchronized (HotelMapper.class) {
                    // double check to prevent duplicate instance
                    if (instance == null) {
                        instance = new HotelMapper();
                        LOGGER.info("Lazy loaded: [{}]", instance.getClass().getName());
                    }
                }
            }

            return instance;
        }

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

        public int deleteOne(Hotel entity) {
            int row = CRUDTemplate.executeNonQuery(
                    "DELETE FROM hotel WHERE id = ?",
                    entity.getId()
            );

            return row;
        }

        public int findTotalCount() {
            Long totalRows = CRUDTemplate.executeQueryWithOneRes(
                    Long.class,
                    "SELECT count(*) FROM hotel");

            return (totalRows != null) ? totalRows.intValue() : 0;
        }

        public List<Hotel> findAllByPage(Integer start, Integer rows) {
            List<Hotel> hotels = CRUDTemplate.executeQueryWithMultiRes(
                    Hotel.class,
                    "SELECT * FROM hotel OFFSET ? LIMIT ?",
                    start,
                    rows
            );

            return hotels;
        }

        public Hotel findOneByBusinessId(String hotelId) {
            Hotel hotel = CRUDTemplate.executeQueryWithOneRes(
                    Hotel.class,
                    "SELECT * FROM hotel WHERE hotel_id = ?",
                    hotelId
            );

            return hotel;
        }
    }
}