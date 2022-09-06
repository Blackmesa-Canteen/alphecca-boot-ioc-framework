package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Lazy;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.alpheccaboot.exception.NotImplementedException;
import io.swen90007sm2.app.common.util.TimeUtil;
import io.swen90007sm2.app.dao.IRoomOrderDao;
import io.swen90007sm2.app.db.bean.BatchBean;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.model.entity.RoomOrder;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 996Worker
 * @create 2022-09-05 15:34
 */
@Dao
@Lazy
public class RoomOrderDao implements IRoomOrderDao {

    @Override
    public void insertRoomOrderBatch(List<RoomOrder> roomOrders) {
        List<BatchBean> batchBeans = new LinkedList<>();
        String sql = "INSERT INTO room_order (id, room_order_id, transaction_id, hotel_id, room_id, customer_id, " +
                "ordered_count, price_per_room, currency) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        for (RoomOrder roomOrder : roomOrders) {
            BatchBean bean = new BatchBean(
                    sql,
                    roomOrder.getId(),
                    roomOrder.getRoomOrderId(),
                    roomOrder.getTransactionId(),
                    roomOrder.getHotelId(),
                    roomOrder.getRoomId(),
                    roomOrder.getCustomerId(),
                    roomOrder.getOrderedCount(),
                    roomOrder.getPricePerRoom(),
                    roomOrder.getCurrency()
            );

            batchBeans.add(bean);
        }

        CRUDTemplate.executeNonQueryBatch(batchBeans);
    }

    @Override
    public int insertOne(RoomOrder entity) {
        return CRUDTemplate.executeNonQuery(
                "INSERT INTO room_order (id, room_order_id, transaction_id, hotel_id, room_id, customer_id, " +
                        "ordered_count, price_per_room, currency) values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                entity.getId(),
                entity.getRoomOrderId(),
                entity.getHotelId(),
                entity.getRoomId(),
                entity.getCustomerId(),
                entity.getOrderedCount(),
                entity.getPricePerRoom(),
                entity.getCurrency()
        );
    }

    @Override
    public int updateOne(RoomOrder entity) {
        return CRUDTemplate.executeNonQuery(
                "UPDATE room_order SET ordered_count = ?, price_per_room = ?, currency = ?, update_time = ? WHERE id = ?",
                entity.getOrderedCount(),
                entity.getPricePerRoom(),
                entity.getCurrency(),
                new java.sql.Date(TimeUtil.now().getTime()),
                entity.getId()
        );
    }

    @Override
    public int deleteOne(RoomOrder entity) {
        return CRUDTemplate.executeNonQuery(
                "DELETE FROM room_order WHERE id = ?",
                entity.getId()
        );
    }

    @Override
    public RoomOrder findOneByBusinessId(String roomOrderId) {
        return CRUDTemplate.executeQueryWithOneRes(
                RoomOrder.class,
                "SELECT * FROM room_order WHERE room_order_id = ?",
                roomOrderId
        );
    }

    @Override
    public List<RoomOrder> findRoomOrdersByTransactionId(String transactionId) {
        return CRUDTemplate.executeQueryWithMultiRes(
                RoomOrder.class,
                "SELECT * FROM room_order WHERE transaction_id = ?",
                transactionId
        );
    }

    @Override
    public List<RoomOrder> findRoomOrdersByCustomerId(String customerId) {
        return CRUDTemplate.executeQueryWithMultiRes(
                RoomOrder.class,
                "SELECT * FROM room_order WHERE customer_id = ?",
                customerId
        );
    }

    @Override
    public List<RoomOrder> findRoomOrdersByHotelId(String hotelId) {
        return CRUDTemplate.executeQueryWithMultiRes(
                RoomOrder.class,
                "SELECT * FROM room_order WHERE hotel_id = ?",
                hotelId
        );
    }

    @Override
    public List<RoomOrder> findRoomOrdersByCustomerIdAndHotelId(String customerId, String hotelId) {
        return CRUDTemplate.executeQueryWithMultiRes(
                RoomOrder.class,
                "SELECT * FROM room_order WHERE customer_id = ? AND hotel_id = ?",
                customerId,
                hotelId
        );
    }

    @Override
    public List<RoomOrder> findRoomOrdersByTransactionIdAndDateRange(String transactionId, Date startDate, Date endDate) {
        throw new NotImplementedException();
    }

    @Override
    public List<RoomOrder> findRoomOrdersByHotelIdAndDateRange(String hotelId, Date startDate, Date endDate, int statusCode) {
        java.sql.Date sqlDateStart = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlDateEnd = new java.sql.Date(endDate.getTime());

        return CRUDTemplate.executeQueryWithMultiRes(
                RoomOrder.class,
                "SELECT create_time, update_time, is_deleted, room_order_id, id, customer_id," +
                        " hotel_id, transaction_id, room_id, ordered_count, price_per_room, currency, status_code" +
                        " FROM transaction INNER JOIN room_order USING (transaction_id)" +
                        " WHERE status_code = ? AND hotel_id = ? AND start_date >= ? AND end_date <= ?",
                statusCode,
                hotelId,
                sqlDateStart,
                sqlDateEnd
        );
    }
}