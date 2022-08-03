package io.swen90007sm2.alpheccaboot.core.web.factory;

import io.swen90007sm2.alpheccaboot.bean.ParamValidationErrorBean;
import io.swen90007sm2.alpheccaboot.bean.R;
import org.apache.http.HttpStatus;

import javax.validation.ConstraintViolation;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * factory for generate R object
 *
 * @author xiaotian
 */
public class ResponseFactory {

    /**
     * returns response with 200 status code
     */
    public static R getSuccessResponseBean(Object data) {
        return R.ok().setData(data);
    }

    public static R getValidationErrResponseBean (Set<ConstraintViolation<?>> result) {
        List<ParamValidationErrorBean> resList = new LinkedList<>();
        for (ConstraintViolation<?> violation : result) {
            resList.add(new ParamValidationErrorBean(
                    violation.getPropertyPath().toString(),
                    violation.getMessageTemplate()
            ));
        }

        return R.error(HttpStatus.SC_BAD_REQUEST, "Illegal request parameters.").setData(resList);
    }

    /**
     * returns response R with 400 status code
     */
    public static R getRequestErrorResponseBean(int code, String msg) {
        return R.error(code, msg);
    }

    /**
     * returns response R with 404 status code
     */
    public static R getResourceNotFoundResponseBean(String msg) {
        return R.error(HttpStatus.SC_NOT_FOUND, msg);
    }

    /**
     * returns response R with 500 status code
     */
    public static R getServerInternalErrorResponseBean(int code, String msg) {
        return R.error(code, msg);
    }

    public static R getErrorResponseBean(int code, String msg) {
        return R.error(code, msg);
    }
}
