package io.swen90007sm2.app.controller.hotelier.api;

import io.swen90007sm2.alpheccaboot.annotation.filter.AppliesFilter;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.QueryParam;
import io.swen90007sm2.alpheccaboot.annotation.validation.Validated;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.blo.ITransactionBlo;
import io.swen90007sm2.app.model.vo.TransactionVo;
import io.swen90007sm2.app.security.constant.SecurityConstant;

import java.util.List;

/**
 * @author 996Worker
 * @description
 * @create 2022-09-03 19:38
 */

@Controller(path = "/api/hotelier/transactions")
@Validated
public class TransactionController {
    @AutoInjected
    ITransactionBlo transactionBlo;

    // get all transactions of a hotelier
    @HandlesRequest(path = "/all", method = RequestMethod.GET)
    @AppliesFilter(filterNames = {SecurityConstant.HOTELIER_ROLE_NAME})
    public R getAllTransactions(@QueryParam(value = "hotelierId") String hotelierId, @QueryParam(value = "currencyName") String currencyName) {
        List<TransactionVo> transactions = transactionBlo.getAllTransactionsForHotelierId(hotelierId, currencyName);
        return R.ok().setData(transactions);
    }

    // get all transactions of a hotelier with status code
    @HandlesRequest(path = "/all/with_status", method = RequestMethod.GET)
    @AppliesFilter(filterNames = {SecurityConstant.HOTELIER_ROLE_NAME})
    public R getAllTransactionsWithTransactionId(@QueryParam(value = "hotelierId") String hotelierId, @QueryParam(value = "statusCode") int statusCode, @QueryParam(value = "currencyName") String currencyName) {
        List<TransactionVo> transactions = transactionBlo.getAllTransactionsForHotelierIdWithStatusCode(hotelierId, statusCode,  currencyName);
        return R.ok().setData(transactions);
    }
}