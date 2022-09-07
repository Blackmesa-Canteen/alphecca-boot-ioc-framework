package io.swen90007sm2.app.blo.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.util.BeanUtils;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.alpheccaboot.exception.NotImplementedException;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.blo.*;
import io.swen90007sm2.app.common.constant.CommonConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.common.factory.IdFactory;
import io.swen90007sm2.app.common.util.CurrencyUtil;
import io.swen90007sm2.app.common.util.TimeUtil;
import io.swen90007sm2.app.dao.ITransactionDao;
import io.swen90007sm2.app.dao.impl.RoomOrderDao;
import io.swen90007sm2.app.dao.impl.TransactionDao;
import io.swen90007sm2.app.model.entity.*;
import io.swen90007sm2.app.model.pojo.Money;
import io.swen90007sm2.app.model.pojo.RoomBookingBean;
import io.swen90007sm2.app.model.vo.RoomOrderVo;
import io.swen90007sm2.app.model.vo.TransactionVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    IRoomOrderBlo roomOrderBlo;

    @AutoInjected
    IHotelBlo hotelBlo;

    @Override
    public void doMakeBooking(String customerId, String hotelId, Date start, Date end, Map<String, Integer> roomIdNumberMap) {

        // pre generate transactionId
        Long newTransactionId = IdFactory.genSnowFlakeId();
        // pre define total price for transaction
        double totalPrice = 0.0;

        List<RoomOrder> newRoomOrders = new LinkedList<>();

        RoomOrderDao roomOrderDao = BeanManager.getLazyBeanByClass(RoomOrderDao.class);
        TransactionDao transactionDao = BeanManager.getLazyBeanByClass(TransactionDao.class);

        // Atom operation: check Room rest number + make new room order
        synchronized (this) {
            // get the existing room orders for this hotel at this date range
            List<RoomOrder> existingRoomOrders = roomOrderBlo.getRoomOrdersByHotelIdAndDateRange(
                    hotelId, start, end, CommonConstant.TRANSACTION_CONFIRMED);

            // check remain room: vacant_num - [(room_orders_of_this_hotel_and_date.ordered_count)] >= roomBooking.number ?
            for (String targetRoomId : roomIdNumberMap.keySet()) {
                Integer targetRoomBookedNumber = roomIdNumberMap.get(targetRoomId);

                Room room = roomBlo.getRoomEntityByRoomId(targetRoomId);
                int totalCount = room.getVacantNum();

                for (RoomOrder roomOrder : existingRoomOrders) {
                    if (roomOrder.getRoomId().equals(targetRoomId)) {
                        totalCount = totalCount - roomOrder.getOrderedCount();
                    }
                }

                totalCount = totalCount - targetRoomBookedNumber;
                if (totalCount < 0) {
                    throw new RequestException(
                            String.format("Out of stock. Room name [%s] only have [%d] vacant rooms left.",
                                    room.getName(), totalCount + targetRoomBookedNumber),
                            StatusCodeEnume.ROOM_IS_OCCUPIED.getCode()
                    );
                }

                // create room orders
                RoomOrder roomOrder = new RoomOrder();
                Long idLong = IdFactory.genSnowFlakeId();
                roomOrder.setId(idLong);
                roomOrder.setCustomerId(customerId);
                roomOrder.setRoomOrderId(idLong.toString());
                roomOrder.setRoomId(targetRoomId);
                roomOrder.setHotelId(hotelId);
                roomOrder.setOrderedCount(targetRoomBookedNumber);
                roomOrder.setTransactionId(newTransactionId.toString());
                roomOrder.setPricePerRoom(room.getPricePerNight());
                roomOrder.setCurrency(CommonConstant.AUD_CURRENCY);

                newRoomOrders.add(roomOrder);

                // calc price for this room
                long deltaDays = TimeUtil.getDeltaBetweenDate(start, end, TimeUnit.DAYS);
                totalPrice += room.getPricePerNight().doubleValue() * deltaDays;
            }

            // batch insert new room orders
            roomOrderDao.insertRoomOrderBatch(newRoomOrders);

            Transaction transaction = new Transaction();
            transaction.setId(newTransactionId);
            transaction.setTransactionId(newTransactionId.toString());
            // for simplify, once the order is made, it is confirmed
            transaction.setStatusCode(CommonConstant.TRANSACTION_CONFIRMED);
            transaction.setHotelId(hotelId);
            transaction.setCustomerId(customerId);
            transaction.setStartDate(start);
            transaction.setEndDate(end);
            transaction.setTotalPrice(BigDecimal.valueOf(totalPrice));
            transaction.setCurrency(CommonConstant.AUD_CURRENCY);

            // insert to db
            transactionDao.insertOne(transaction);
        }


    }

    @Override
    public void doUpdateBooking(String transactionId, List<RoomBookingBean> roomBookingBeans) {
        throw new NotImplementedException();
    }

    @Override
    public void doCancelBooking(String transactionId) {
        Transaction transaction = getTransactionEntityByTransactionId(transactionId);
        transaction.setStatusCode(CommonConstant.TRANSACTION_CANCELLED);

        TransactionDao transactionDao = BeanManager.getLazyBeanByClass(TransactionDao.class);
        transactionDao.updateOne(transaction);
    }

    @Override
    public Transaction getTransactionEntityByTransactionId(String transactionId) {
        TransactionDao transactionDao = BeanManager.getLazyBeanByClass(TransactionDao.class);
        return transactionDao.findOneByBusinessId(transactionId);
    }

    @Override
    public TransactionVo getTransactionInfoByTransactionId(String transactionId, String currencyName) {
        throw new NotImplementedException();
    }

    @Override
    public List<Transaction> getTransactionEntitiesByHotelIdAndDateRange(String hotelId, Date startDate, Date endDate) {
        TransactionDao transactionDao = BeanManager.getLazyBeanByClass(TransactionDao.class);
        return transactionDao.findTransactionByHotelIdByDateRange(hotelId, startDate, endDate);
    }

    @Override
    public List<TransactionVo> getTransactionsByHotelIdAndDateRange(String hotelId, Date startDate, Date endDate, String currencyName) {
        throw new NotImplementedException();
    }

    @Override
    public List<TransactionVo> getAllTransactionsForCustomerId(String customerId, Integer statusCode, String currencyName) {
        throw new NotImplementedException();
    }

    @Override
    public List<TransactionVo> getAllTransactionsForHotelId(String hotelId, Integer statusCode, String currencyName) {
        throw new NotImplementedException();
    }

    @Override
    public List<TransactionVo> getAllTransactionsForHotelierId(String hotelierId, String currencyName) {
        Hotelier hotelier = hotelierBlo.getHotelierInfoByUserId(hotelierId);
        if (hotelier == null) {
            throw new RequestException(StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getMessage(),
                    StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getCode());
        }
        String hotelId = hotelier.getHotelId();
        List<TransactionVo> transactionVos;
        Hotel hotel = hotelBlo.getHotelEntityByHotelId(hotelId);
        if (hotel == null) {
            throw new RequestException(
                    StatusCodeEnume.HOTEL_NOT_EXIST.getMessage(),
                    StatusCodeEnume.HOTEL_NOT_EXIST.getCode()
            );
        }
        TransactionDao transactionDao = BeanManager.getLazyBeanByClass(TransactionDao.class);
        List<Transaction> transactions = transactionDao.findAllTransactionsByHotelId(hotelId);

        // copy to vo
        transactionVos = new LinkedList<>();
        for (Transaction transaction : transactions) {
            TransactionVo transactionVo = new TransactionVo();
            BeanUtil.copyProperties(transaction, transactionVo);
            transactionVos.add(transactionVo);
        }

        for (TransactionVo transactionVo : transactionVos) {
            Money money = new Money();
            money.setCurrency(currencyName);
            money.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, transactionVo.getTotalPrice()));
            transactionVo.setMoney(money);

            RoomOrderDao roomOrderDao = BeanManager.getLazyBeanByClass(RoomOrderDao.class);
            List<RoomOrder> roomOrders = roomOrderDao.findRoomOrdersByTransactionId(transactionVo.getTransactionId());
            List<RoomOrderVo> roomOrderVos = new LinkedList<>();
            for (RoomOrder roomOrder : roomOrders) {
                Money roomMoney = new Money();
                roomMoney.setCurrency(currencyName);
                roomMoney.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, roomOrder.getPricePerRoom()));
                RoomOrderVo roomOrderVo = new RoomOrderVo(roomMoney);
                BeanUtil.copyProperties(roomOrder, roomOrderVo);
                roomOrderVos.add(roomOrderVo);
            }
            transactionVo.setRoomOrders(roomOrderVos);
        }

        return transactionVos;
    }

    @Override
    public List<TransactionVo> getAllTransactionsForHotelierIdWithStatusCode(String hotelierId, Integer statusCode, String currencyName) {
        Hotelier hotelier = hotelierBlo.getHotelierInfoByUserId(hotelierId);
        if (hotelier == null) {
            throw new RequestException(StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getMessage(),
                    StatusCodeEnume.USER_NOT_EXIST_EXCEPTION.getCode());
        }
        String hotelId = hotelier.getHotelId();
        List<TransactionVo> transactionVos;
        Hotel hotel = hotelBlo.getHotelEntityByHotelId(hotelId);
        if (hotel == null) {
            throw new RequestException(
                    StatusCodeEnume.HOTEL_NOT_EXIST.getMessage(),
                    StatusCodeEnume.HOTEL_NOT_EXIST.getCode()
            );
        }
        TransactionDao transactionDao = BeanManager.getLazyBeanByClass(TransactionDao.class);
        List<Transaction> transactions = transactionDao.findAllTransactionsByHotelIdWithStatusCode(hotelId, statusCode);

        // copy to vo
        transactionVos = new LinkedList<>();
        for (Transaction transaction : transactions) {
            TransactionVo transactionVo = new TransactionVo();
            BeanUtil.copyProperties(transaction, transactionVo);
            transactionVos.add(transactionVo);
        }

        for (TransactionVo transactionVo : transactionVos) {
            Money money = new Money();
            money.setCurrency(currencyName);
            money.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, transactionVo.getTotalPrice()));
            transactionVo.setMoney(money);

            RoomOrderDao roomOrderDao = BeanManager.getLazyBeanByClass(RoomOrderDao.class);
            List<RoomOrder> roomOrders = roomOrderDao.findRoomOrdersByTransactionId(transactionVo.getTransactionId());
            List<RoomOrderVo> roomOrderVos = new LinkedList<>();
            for (RoomOrder roomOrder : roomOrders) {
                Money roomMoney = new Money();
                roomMoney.setCurrency(currencyName);
                roomMoney.setAmount(CurrencyUtil.convertAUDtoCurrency(currencyName, roomOrder.getPricePerRoom()));
                RoomOrderVo roomOrderVo = new RoomOrderVo(roomMoney);
                BeanUtil.copyProperties(roomOrder, roomOrderVo);
                roomOrderVos.add(roomOrderVo);
            }
            transactionVo.setRoomOrders(roomOrderVos);
        }

        return transactionVos;
    }
}