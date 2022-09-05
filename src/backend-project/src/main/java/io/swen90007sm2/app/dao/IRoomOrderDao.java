package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.model.entity.RoomOrder;

import java.util.List;

public interface IRoomOrderDao extends IBaseDao<RoomOrder>{


    RoomOrder findOneByBusinessId(String roomOrderId);

    List<RoomOrder> findRoomOrdersByTransactionId(String transactionId);

    List<RoomOrder> findRoomOrdersByCustomerId(String customerId);

    List<RoomOrder> findRoomOrdersByHotelId(String hotelId);

    List<RoomOrder> findRoomOrdersByCustomerIdAndHotelId(String customerId, String hotelId);
}
