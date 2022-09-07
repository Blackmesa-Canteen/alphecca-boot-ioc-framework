package io.swen90007sm2.app.controller.customer.api;

import io.swen90007sm2.alpheccaboot.annotation.filter.AppliesFilter;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.QueryParam;
import io.swen90007sm2.alpheccaboot.annotation.mvc.RequestJsonBody;
import io.swen90007sm2.alpheccaboot.annotation.validation.Validated;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.blo.ITransactionBlo;
import io.swen90007sm2.app.model.param.CreateTransactionParam;
import io.swen90007sm2.app.model.param.CustomerUpdateRoomOrderParam;
import io.swen90007sm2.app.model.vo.TransactionVo;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import io.swen90007sm2.app.security.helper.TokenHelper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller(path = "/api/customer/transaction")
@Validated
public class TransactionController {

    @AutoInjected
    ITransactionBlo transactionBlo;

    @HandlesRequest(path = "/", method = RequestMethod.POST)
    @AppliesFilter(filterNames = {SecurityConstant.CUSTOMER_ROLE_NAME})
    public R bookHotel(HttpServletRequest request, @Valid @RequestJsonBody CreateTransactionParam param) {
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

    @HandlesRequest(path = "/cancel", method = RequestMethod.GET)
    @AppliesFilter(filterNames = {SecurityConstant.CUSTOMER_ROLE_NAME})
    public R cancelTransaction(@QueryParam(value = "transactionId") String transactionId) {
        transactionBlo.doCancelBooking(transactionId);
        return R.ok();
    }

    // update a roomOrder
    @HandlesRequest(path = "/update", method = RequestMethod.POST)
    @AppliesFilter(filterNames = {SecurityConstant.CUSTOMER_ROLE_NAME})
    public R updateTransaction(@Valid @RequestJsonBody CustomerUpdateRoomOrderParam param) {
        String transactionId = param.getTransactionId();
        String roomOrderId = param.getRoomOrderId();
        int newQuantity = param.getNewQuantity();
        transactionBlo.doUpdateBooking(
                transactionId,
                roomOrderId,
                newQuantity
        );

        return R.ok();
    }

    // get all transactions of a customer
    @HandlesRequest(path = "/all", method = RequestMethod.GET)
    @AppliesFilter(filterNames = {SecurityConstant.CUSTOMER_ROLE_NAME})
    public R getAllTransactions( @QueryParam(value = "customerId") String customerId, @QueryParam(value = "currencyName") String currencyName) {
        List<TransactionVo> transactions = transactionBlo.getAllTransactionsForCustomerId(customerId, currencyName);
        return R.ok().setData(transactions);
    }

    // get all transactions of a customer by status code
    @HandlesRequest(path = "/all/with_status", method = RequestMethod.GET)
    @AppliesFilter(filterNames = {SecurityConstant.CUSTOMER_ROLE_NAME})
    public R getAllTransactionsWithStatusCode( @QueryParam(value = "customerId") String customerId, @QueryParam(value = "statusCode") int statusCode, @QueryParam(value = "currencyName") String currencyName) {
        List<TransactionVo> transactions = transactionBlo.getAllTransactionsForCustomerIdWithStatusCode(customerId, statusCode, currencyName);
        return R.ok().setData(transactions);
    }

}
