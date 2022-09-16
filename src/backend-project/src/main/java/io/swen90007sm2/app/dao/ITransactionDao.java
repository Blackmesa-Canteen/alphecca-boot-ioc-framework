package io.swen90007sm2.app.dao;

import io.swen90007sm2.app.model.entity.Transaction;

import java.util.Date;
import java.util.List;

public interface ITransactionDao extends IBaseDao<Transaction>{

    /**
     * Find room by roomId
     * @param transactionId is business specific Id, not database id
     * @return Transaction
     */
    Transaction findOneByBusinessId(String transactionId);

    /**
     * find record count of transactions that belongs to a hotel
     * @param hotelId owner hotel ID
     * @return record count
     */
    int findTotalCountByHotelId(String hotelId, int statusCode);

    /**
     * find all transactions for a hotel
     */
    List<Transaction> findTransactionsByHotelIdByPage(String hotelId, Integer start, Integer rows, int statusCode);

    int findTotalCountByCustomerId(String customerId, int statusCode);

    /**
     * find all transactions for a customer
     */
    List<Transaction> findTransactionsByCustomerIdByPage(String customerId, Integer start, Integer rows, int statusCode);

    List<Transaction> findTransactionByHotelIdByDateRange(String hotelId, Date startDate, Date endDate);

    List<Transaction> findAllTransactionsByHotelId(String hotelId);

    List<Transaction> findAllTransactionsByHotelIdWithStatusCode(String hotelId, int statusCode);

    List<Transaction> findTransactionsByCustomerId(String customerId);

    List<Transaction> findTransactionsByCustomerIdWithStatusCode(String customerId, int statusCode);
}
