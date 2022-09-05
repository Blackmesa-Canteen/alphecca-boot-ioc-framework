package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.model.pojo.RoomBookingBean;

import java.util.List;

public interface ITransactionBlo {

    void doMakeBooking(String customerId, String hotelId, List<RoomBookingBean> roomBookingBeans);

    void doUpdateBooking(String customerId, String hotelId, List<RoomBookingBean> roomBookingBeans);

    void doCancelBooking(String customerId, String hotelId, List<String> roomIds);
}
