package io.swen90007sm2.app.blo.impl;

import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.exception.NotImplementedException;
import io.swen90007sm2.app.blo.ITransactionBlo;
import io.swen90007sm2.app.model.pojo.RoomBookingBean;

import java.util.List;

/**
 * @author 996Worker
 * @description handles room order and transaction
 * @create 2022-09-05 15:49
 */

@Blo
public class TransactionBlo implements ITransactionBlo {

    @Override
    public void doMakeBooking(String customerId, String hotelId, List<RoomBookingBean> roomBookingBeans) {
        throw new NotImplementedException();
    }

    @Override
    public void doUpdateBooking(String customerId, String hotelId, List<RoomBookingBean> roomBookingBeans) {
        throw new NotImplementedException();
    }

    @Override
    public void doCancelBooking(String customerId, String hotelId, List<String> roomIds) {
        throw new NotImplementedException();
    }
}