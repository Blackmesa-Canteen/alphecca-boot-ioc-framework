package io.swen90007sm2.app.blo.impl;

import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.alpheccaboot.exception.NotImplementedException;
import io.swen90007sm2.app.blo.*;
import io.swen90007sm2.app.dao.impl.TransactionDao;
import io.swen90007sm2.app.model.entity.Transaction;
import io.swen90007sm2.app.model.pojo.RoomBookingBean;
import io.swen90007sm2.app.model.vo.TransactionVo;

import java.util.List;

/**
 * @author 996Worker
 * @description handles room order and transaction
 * @create 2022-09-05 15:49
 */

@Blo
public class TransactionBlo implements ITransactionBlo {

    @AutoInjected
    IRoomBlo roomBlo;

    @AutoInjected
    ICustomerBlo customerBlo;

    @AutoInjected
    IHotelierBlo hotelierBlo;

    @AutoInjected
    IHotelBlo hotelBlo;

    @Override
    public void doMakeBooking(String customerId, String hotelId, List<RoomBookingBean> roomBookingBeans) {
        synchronized (this) {
            // check remain room

            // deduct remain room

            // remove caches
        }

            // create room orders

            // create transaction


    }

    @Override
    public void doUpdateBooking(String customerId, String hotelId, List<RoomBookingBean> roomBookingBeans) {
        throw new NotImplementedException();
    }

    @Override
    public void doCancelBooking(String customerId, String hotelId, List<String> roomIds) {
        throw new NotImplementedException();
    }

    @Override
    public Transaction getTransactionEntityByTransactionId(String transactionId) {
        throw new NotImplementedException();
    }

    @Override
    public TransactionVo getTransactionInfoByTransactionId(String transactionId) {
        throw new NotImplementedException();
    }

    @Override
    public List<TransactionVo> getAllTransactionsForCustomerId(String customerId, Integer statusCode) {
        TransactionDao transactionDao = BeanManager.getLazyBeanByClass(TransactionDao.class);
        int total = transactionDao.findTotalCountByCustomerId(customerId, statusCode);
        throw new NotImplementedException();
    }

    @Override
    public List<TransactionVo> getAllTransactionsForHotelId(String hotelId, Integer statusCode) {
        throw new NotImplementedException();
    }

    @Override
    public List<TransactionVo> getAllTransactionsForHotelierId(String hotelierId, Integer statusCode) {
        throw new NotImplementedException();
    }
}