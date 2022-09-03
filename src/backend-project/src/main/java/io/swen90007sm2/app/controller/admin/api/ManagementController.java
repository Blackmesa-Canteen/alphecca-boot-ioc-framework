package io.swen90007sm2.app.controller.admin.api;

import io.swen90007sm2.alpheccaboot.annotation.filter.AppliesFilter;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.QueryParam;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.blo.impl.CustomerBlo;
import io.swen90007sm2.app.db.bean.PageBean;
import io.swen90007sm2.app.model.entity.Customer;
import io.swen90007sm2.app.security.constant.SecurityConstant;

import java.util.List;

/**
 * @author 996Worker
 * @description
 * @create 2022-08-23 10:11
 */
@Controller(path = "/api/admin/management")
public class ManagementController {

    @AutoInjected
    CustomerBlo customerBlo;

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
        PageBean<Customer> customerByPage = customerBlo.getCustomerByPage(pageNo, pageSize);

        // get data beans from page bean
        List<Customer> beans = customerByPage.getBeans();

        return R.ok().setData(beans);
    }
}