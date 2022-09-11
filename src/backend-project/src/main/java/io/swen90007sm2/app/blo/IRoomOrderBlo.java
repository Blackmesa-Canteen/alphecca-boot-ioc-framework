package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.model.entity.RoomOrder;
import io.swen90007sm2.app.model.pojo.RoomBookingBean;

import java.util.Date;
import java.util.List;

public interface IRoomOrderBlo {

    void createRoomOrders(String transactionId, String customerId, String hotelId, Date start, Date end, List<RoomBookingBean> roomBookingBeans);
    List<RoomOrder> getRoomOrderEntitiesByTransactionIdAndDateRange(String transactionId, Date startDate, Date endDate);
    List<RoomOrder> getAllRoomOrderEntitiesByHotelId(String hotelId);

    List<RoomOrder> getRoomOrdersByHotelIdAndDateRange(String hotelId, Date startDate, Date endDate, Integer statusCode);
}
