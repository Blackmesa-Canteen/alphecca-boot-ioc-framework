package io.swen90007sm2.app.controller.customer.api;

import io.swen90007sm2.alpheccaboot.annotation.filter.AppliesFilter;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.RequestJsonBody;
import io.swen90007sm2.alpheccaboot.annotation.validation.Validated;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.blo.ITransactionBlo;
import io.swen90007sm2.app.model.param.CreateTransactionParam;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import io.swen90007sm2.app.security.helper.TokenHelper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller(path = "/api/customer/transaction")
@Validated
public class TransactionController {

    @AutoInjected
    ITransactionBlo transactionBlo;

    @HandlesRequest(path = "/", method = RequestMethod.POST)
    @AppliesFilter(filterNames = {SecurityConstant.CUSTOMER_ROLE_NAME})
    public R bookAHotel(HttpServletRequest request, @Valid @RequestJsonBody CreateTransactionParam param) {
        String token = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken authToken = TokenHelper.parseAuthTokenString(token);
        String userId = authToken.getUserId();
        transactionBlo.doMakeBooking(
                userId,
                param.getHotelId(),
                param.getStartDate(),
                param.getEndDate(),
                param.getRoomIdNumberMap()
        );

        return R.ok();
    }
}
