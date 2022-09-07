package io.swen90007sm2.app.dao.impl;

import io.swen90007sm2.alpheccaboot.annotation.ioc.Lazy;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Dao;
import io.swen90007sm2.app.common.util.TimeUtil;
import io.swen90007sm2.app.dao.ITransactionDao;
import io.swen90007sm2.app.db.util.CRUDTemplate;
import io.swen90007sm2.app.model.entity.Transaction;

import java.util.Date;
import java.util.List;

/**
 * @author 996Worker
 * @description
 * @create 2022-08-24 21:04
 */
@Dao
@Lazy
public class TransactionDao implements ITransactionDao {

    @Override
    public int insertOne(Transaction entity) {
        java.sql.Date sqlStartDate = new java.sql.Date(entity.getStartDate().getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(entity.getEndDate().getTime());
        return CRUDTemplate.executeNonQuery(
                "INSERT INTO transaction (id, transaction_id, customer_id, " +
                        "hotel_id, status_code, start_date, " +
                        "end_date, total_price, currency) values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                entity.getId(),
                entity.getTransactionId(),
                entity.getCustomerId(),
                entity.getHotelId(),
                entity.getStatusCode(),
                sqlStartDate,
                sqlEndDate,
                entity.getTotalPrice(),
                entity.getCurrency()
        );
    }

    @Override
    public int updateOne(Transaction entity) {
        return CRUDTemplate.executeNonQuery(
                "UPDATE transaction SET status_code = ?, " +
                        "start_date = ?, end_date = ?, total_price = ?, currency = ?, " +
                        "update_time = ? WHERE id = ?",
                entity.getStatusCode(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getTotalPrice(),
                entity.getCurrency(),
                new java.sql.Date(TimeUtil.now().getTime()),
                entity.getId()
        );
    }

    @Override
    public int deleteOne(Transaction entity) {
        return CRUDTemplate.executeNonQuery(
                "DELETE FROM transaction WHERE id = ?",
                entity.getId()
        );
    }

    @Override
    public Transaction findOneByBusinessId(String transactionId) {
        return CRUDTemplate.executeQueryWithOneRes(
                Transaction.class,
                "SELECT * FROM transaction WHERE transaction_id = ?",
                transactionId
        );
    }

    @Override
    public int findTotalCountByHotelId(String hotelId, int statusCode) {
        Long totalRows = CRUDTemplate.executeQueryWithOneRes(
                Long.class,
                "SELECT count(*) FROM transaction WHERE hotel_id = ? and status_code = ?",
                hotelId,
                statusCode
        );

        return (totalRows != null) ? totalRows.intValue() : 0;
    }

    @Override
    public List<Transaction> findTransactionsByHotelIdByPage(String hotelId, Integer start, Integer rows, int statusCode) {
        return CRUDTemplate.executeQueryWithMultiRes(
                Transaction.class,
                "SELECT * FROM transaction WHERE hotel_id = ? and status_code = ? OFFSET ? LIMIT ?",
                hotelId,
                statusCode,
                start,
                rows
        );
    }

    @Override
    public List<Transaction> findAllTransactionsByHotelId(String hotelId) {
        return CRUDTemplate.executeQueryWithMultiRes(
                Transaction.class,
                "SELECT * FROM transaction WHERE hotel_id = ?",
                hotelId
        );
    }

    @Override
    public List<Transaction> findAllTransactionsByHotelIdWithStatusCode(String hotelId, int statusCode) {
        return CRUDTemplate.executeQueryWithMultiRes(
                Transaction.class,
                "SELECT * FROM transaction WHERE hotel_id = ? and status_code = ?",
                hotelId,
                statusCode
        );
    }

    @Override
    public int findTotalCountByCustomerId(String customerId, int statusCode) {
        Long totalRows = CRUDTemplate.executeQueryWithOneRes(
                Long.class,
                "SELECT count(*) FROM transaction WHERE customer_id = ? and status_code = ?",
                customerId,
                statusCode
        );

        return (totalRows != null) ? totalRows.intValue() : 0;
    }

    @Override
    public List<Transaction> findTransactionsByCustomerIdByPage(String customerId, Integer start, Integer rows, int statusCode) {
        return CRUDTemplate.executeQueryWithMultiRes(
                Transaction.class,
                "SELECT * FROM transaction WHERE customer_id = ? and status_code = ? OFFSET ? LIMIT ?",
                customerId,
                statusCode,
                start,
                rows
        );
    }

    @Override
    public List<Transaction> findTransactionByHotelIdByDateRange(String hotelId, Date startDate, Date endDate) {
        java.sql.Date startSqlDate = new java.sql.Date(startDate.getTime());
        java.sql.Date endSqlDate = new java.sql.Date(endDate.getTime());

        return CRUDTemplate.executeQueryWithMultiRes(
                Transaction.class,
                "SELECT * FROM transaction where start_date >= ? AND end_date <= ?",
                startSqlDate,
                endSqlDate
        );
    }
}