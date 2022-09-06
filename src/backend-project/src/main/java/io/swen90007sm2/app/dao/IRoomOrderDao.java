package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.model.entity.RoomOrder;

import java.util.Date;
import java.util.List;

public interface IRoomOrderDao extends IBaseDao<RoomOrder>{

    void insertRoomOrderBatch(List<RoomOrder> roomOrders);

    RoomOrder findOneByBusinessId(String roomOrderId);

    List<RoomOrder> findRoomOrdersByTransactionId(String transactionId);

    List<RoomOrder> findRoomOrdersByCustomerId(String customerId);

    List<RoomOrder> findRoomOrdersByHotelId(String hotelId);

    List<RoomOrder> findRoomOrdersByCustomerIdAndHotelId(String customerId, String hotelId);

    List<RoomOrder> findRoomOrdersByTransactionIdAndDateRange(String transactionId, Date startDate, Date endDate);

    List<RoomOrder> findRoomOrdersByHotelIdAndDateRange(String hotelId, Date startDate, Date endDate, int statusCode);
}
