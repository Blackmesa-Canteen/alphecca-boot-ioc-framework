package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.model.entity.RoomOrder;
import io.swen90007sm2.app.model.entity.Transaction;
import io.swen90007sm2.app.model.pojo.RoomBookingBean;
import io.swen90007sm2.app.model.vo.TransactionVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ITransactionBlo {

    void doMakeBooking(String customerId, String hotelId, Date start, Date end, Map<String, Integer> roomIdNumberMap);

    void doUpdateBooking(String customerId, String hotelId, List<RoomBookingBean> roomBookingBeans);

    void doCancelBooking(String customerId, String hotelId, List<String> roomIds);

    Transaction getTransactionEntityByTransactionId(String transactionId);
    List<Transaction> getTransactionEntitiesByHotelIdAndDateRange(String hotelId, Date startDate, Date endDate);

    TransactionVo getTransactionInfoByTransactionId(String transactionId, String currencyName);

    List<TransactionVo> getTransactionsByHotelIdAndDateRange(String hotelId, Date startDate, Date endDate, String currencyName);

    List<TransactionVo> getAllTransactionsForCustomerId(String customerId, Integer statusCode, String currencyName);

    List<TransactionVo> getAllTransactionsForHotelId(String hotelId, Integer statusCode, String currencyName);

    List<TransactionVo> getAllTransactionsForHotelierId(String hotelierId, Integer statusCode, String currencyName);
}
