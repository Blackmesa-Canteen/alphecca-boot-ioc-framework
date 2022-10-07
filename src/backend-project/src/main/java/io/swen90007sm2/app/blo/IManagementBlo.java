package io.swen90007sm2.app.blo;

import io.swen90007sm2.app.db.bean.PageBean;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.param.*;
import io.swen90007sm2.app.model.vo.HotelVo;

import java.util.List;

public interface IManagementBlo {


    PageBean<Customer> getCustomerByPage(int pageNo, int pageSize);

    void groupHotelierWithExistingHotel(AdminGroupHotelierParam param);

    List<HotelVo> getHotelByPage(int pageNo, int pageSize);

    void changeHotelStatus(String hotelId);

    void registerNewHotelier(UserRegisterParam param);

    List<Hotelier> getHoteliersInOneGroupByHotelId(String hotelId);

    void removeHotelierFromHotelGroup(AdminRemoveHotelierParam param);

    PageBean<Hotelier> getHoteliersByPage(int pageNo, int pageSize);

    void updateCustomerInfo(String userId, AdminUpdateUserParam param);

    void updateHotelierInfo(String userId, AdminUpdateUserParam param);
}
