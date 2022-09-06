package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.model.entity.Transaction;
import io.swen90007sm2.app.model.pojo.RoomBookingBean;
import io.swen90007sm2.app.model.vo.TransactionVo;

import java.util.List;

public interface ITransactionBlo {

    void doMakeBooking(String customerId, String hotelId, List<RoomBookingBean> roomBookingBeans);

    void doUpdateBooking(String customerId, String hotelId, List<RoomBookingBean> roomBookingBeans);

    void doCancelBooking(String customerId, String hotelId, List<String> roomIds);

    Transaction getTransactionEntityByTransactionId(String transactionId);
    TransactionVo getTransactionInfoByTransactionId(String transactionId);

    List<TransactionVo> getAllTransactionsForCustomerId(String customerId, Integer statusCode);

    List<TransactionVo> getAllTransactionsForHotelId(String hotelId, Integer statusCode);

    List<TransactionVo> getAllTransactionsForHotelierId(String hotelierId, Integer statusCode);
}
