package io.swen90007sm2.app.common.helper;

import com.alibaba.fastjson.JSON;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.core.web.handler.IRequestHandler;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 996Worker
 * @description A helper to respond R to a specific HttpServlet Response
 * @create 2022-08-03 20:29
 */
public class ServletResponseHelper {

    /**
     * response with failure json
     * @param response HttpServletResponse
     * @param code code
     * @param message failure message
     */
    private void responseWithFailure(HttpServletResponse response, int code, String message) throws IOException {
        R jsonObj = R.error(code, message);
        if (response != null) {
            IRequestHandler.respondRequestWithJson(jsonObj, response);
        }
    }
}