package io.swen90007sm2.app.controller.admin.api;

import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.QueryParam;
import io.swen90007sm2.alpheccaboot.annotation.validation.Validated;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.blo.impl.CustomerBlo;
import io.swen90007sm2.app.db.bean.PageBean;
import io.swen90007sm2.app.model.entity.Customer;

import java.util.List;

@Controller(path = "/api/admin")
@Validated
public class AdminController {

    @AutoInjected
    CustomerBlo customerBlo;

    /**
     * get all existing customers
     * @param pageNo target page no
     * @param pageSize page size
     * @return response
     *
     * TODO need to uncomment the filter
     */
    @HandlesRequest(path = "/customer", method = RequestMethod.GET)
//    @AppliesFilter(filterNames = {SecurityConstant.ADMIN_ROLE_NAME})
    public R getCustomers(@QueryParam(value = "pageNo") int pageNo, @QueryParam(value = "pageSize") int pageSize) {
        PageBean<Customer> customerByPage = customerBlo.getCustomerByPage(pageNo, pageSize);

        // get data beans from page bean
        List<Customer> beans = customerByPage.getBeans();

        return R.ok().setData(beans);
    }


}
