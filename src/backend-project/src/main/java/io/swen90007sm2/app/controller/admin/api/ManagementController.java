package io.swen90007sm2.app.controller.admin.api;

import io.swen90007sm2.alpheccaboot.annotation.filter.AppliesFilter;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.QueryParam;
import io.swen90007sm2.alpheccaboot.annotation.mvc.RequestJsonBody;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.blo.IManagementBlo;
import io.swen90007sm2.app.db.bean.PageBean;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.model.entity.Hotelier;
import io.swen90007sm2.app.model.param.*;
import io.swen90007sm2.app.model.vo.HotelVo;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import io.swen90007sm2.app.security.helper.TokenHelper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author 996Worker
 * @description
 * @create 2022-08-23 10:11
 */
@Controller(path = "/api/admin/management")
public class ManagementController {

    @AutoInjected
    IManagementBlo managementBlo;
//    @AutoInjected
//    CustomerBlo customerBlo;

    /**
     * get all existing customers
     * @param pageNo target page no
     * @param pageSize page size
     * @return response
     *
     */
    @HandlesRequest(path = "/customer", method = RequestMethod.GET)
    @AppliesFilter(filterNames = {SecurityConstant.ADMIN_ROLE_NAME})
    public R getCustomers(@QueryParam(value = "pageNo") int pageNo, @QueryParam(value = "pageSize") int pageSize) {
        PageBean<Customer> customerByPage = managementBlo.getCustomerByPage(pageNo, pageSize);

        // get data beans from page bean
        List<Customer> beans = customerByPage.getBeans();

        return R.ok().setData(beans);
    }

    @HandlesRequest(path = "/hotelier/new", method = RequestMethod.POST)
    @AppliesFilter(filterNames = {SecurityConstant.ADMIN_ROLE_NAME})
    public R registerNewHotelier(HttpServletRequest request, @RequestJsonBody @Valid UserRegisterParam param) {
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String adminUserId = authToken.getUserId();
        managementBlo.registerNewHotelier(param);
        return R.ok();
    }

    @HandlesRequest(path = "/hotelier/existing", method = RequestMethod.POST)
    @AppliesFilter(filterNames = {SecurityConstant.ADMIN_ROLE_NAME})
    public R groupHotelierWithExistingHotel(HttpServletRequest request, @RequestJsonBody @Valid AdminGroupHotelierParam param) {
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String adminUserId = authToken.getUserId();
        managementBlo.groupHotelierWithExistingHotel(param);
        return R.ok();
    }

    // get all hotels
    @HandlesRequest(path = "/hotel", method = RequestMethod.GET)
    @AppliesFilter(filterNames = {SecurityConstant.ADMIN_ROLE_NAME})
    public R getHotels(@QueryParam(value = "pageNo") int pageNo, @QueryParam(value = "pageSize") int pageSize) {
        List<HotelVo> hotelByPage = managementBlo.getHotelByPage(pageNo, pageSize);

        return R.ok().setData(hotelByPage);
    }

    // change hotel status
    @HandlesRequest(path = "/hotel", method = RequestMethod.POST)
    @AppliesFilter(filterNames = {SecurityConstant.ADMIN_ROLE_NAME})
    public R changeHotelStatus(HttpServletRequest request, @RequestJsonBody @Valid AdminChangeHotelStatusParam param) {
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String hotelId = param.getHotelId();
        managementBlo.changeHotelStatus(hotelId);
        return R.ok();
    }

    // find all hoteliers has one hotelId
    @HandlesRequest(path = "/hotelier", method = RequestMethod.GET)
    @AppliesFilter(filterNames = {SecurityConstant.ADMIN_ROLE_NAME})
    public R getHoteliersHavingSameHotel(@QueryParam(value = "hotelId") String hotelId) {
        List<Hotelier> hoteliersByPage = managementBlo.getHoteliersInOneGroupByHotelId(hotelId);

        return R.ok().setData(hoteliersByPage);
    }

    // remove a hotelier from a hotel group
    @HandlesRequest(path = "/hotelier/remove", method = RequestMethod.POST)
    @AppliesFilter(filterNames = {SecurityConstant.ADMIN_ROLE_NAME})
    public R removeHotelierFromHotelGroup(HttpServletRequest request, @RequestJsonBody @Valid AdminRemoveHotelierParam param) {
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String adminUserId = authToken.getUserId();
        managementBlo.removeHotelierFromHotelGroup(param);
        return R.ok();
    }

    // get hoteliers by page
    @HandlesRequest(path = "/hotelier/all", method = RequestMethod.GET)
    @AppliesFilter(filterNames = {SecurityConstant.ADMIN_ROLE_NAME})
    public R getHoteliers(@QueryParam(value = "pageNo") int pageNo, @QueryParam(value = "pageSize") int pageSize) {
        PageBean<Hotelier> hoteliersByPage = managementBlo.getHoteliersByPage(pageNo, pageSize);

        return R.ok().setData(hoteliersByPage);
    }

    @HandlesRequest(path = "/customer/update", method = RequestMethod.PUT)
    @AppliesFilter(filterNames = {SecurityConstant.ADMIN_ROLE_NAME})
    public R updateCustomerInfo(HttpServletRequest request, @RequestJsonBody @Valid UserUpdateParam userUpdateParam) {
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String userId = authToken.getUserId();
        managementBlo.updateCustomerInfo(userId, userUpdateParam);
        return R.ok();
    }

    @HandlesRequest(path = "/hotelier/update", method = RequestMethod.PUT)
    @AppliesFilter(filterNames = {SecurityConstant.ADMIN_ROLE_NAME})
    public R updateHotelierInfo(HttpServletRequest request, @RequestJsonBody @Valid UserUpdateParam userUpdateParam) {
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String userId = authToken.getUserId();
        managementBlo.updateHotelierInfo(userId, userUpdateParam);
        return R.ok();
    }



}