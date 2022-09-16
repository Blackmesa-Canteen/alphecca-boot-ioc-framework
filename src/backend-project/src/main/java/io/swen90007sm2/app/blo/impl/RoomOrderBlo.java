package io.swen90007sm2.app.blo.impl;

import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.alpheccaboot.exception.NotImplementedException;
import io.swen90007sm2.app.blo.IRoomOrderBlo;
import io.swen90007sm2.app.dao.IRoomDao;
import io.swen90007sm2.app.dao.IRoomOrderDao;
import io.swen90007sm2.app.dao.impl.RoomOrderDao;
import io.swen90007sm2.app.model.entity.RoomOrder;
import io.swen90007sm2.app.model.pojo.RoomBookingBean;

import java.util.Date;
import java.util.List;

/**
 * @author 996Worker
 * @description
 * @create 2022-09-06 16:23
 */
@Blo
public class RoomOrderBlo implements IRoomOrderBlo {

    @Override
    public void createRoomOrders(String transactionId, String customerId, String hotelId, Date start, Date end, List<RoomBookingBean> roomBookingBeans) {
        throw new NotImplementedException();
    }

    @Override
    public List<RoomOrder> getRoomOrderEntitiesByTransactionIdAndDateRange(String transactionId, Date startDate, Date endDate) {
        throw new NotImplementedException();
    }

    @Override
    public List<RoomOrder> getAllRoomOrderEntitiesByHotelId(String hotelId) {
        throw new NotImplementedException();
    }

    @Override
    public List<RoomOrder> getRoomOrdersByHotelIdAndDateRange(String hotelId, Date startDate, Date endDate, Integer statusCode) {
        if (statusCode != null) {
            IRoomOrderDao roomDao = BeanManager.getLazyBeanByClass(RoomOrderDao.class);
            return roomDao.findRoomOrdersByHotelIdAndDateRange(hotelId, startDate, endDate, statusCode);
        } else {
            throw new NotImplementedException();
        }
    }
}