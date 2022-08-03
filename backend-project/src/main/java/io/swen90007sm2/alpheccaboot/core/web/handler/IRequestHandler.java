package io.swen90007sm2.alpheccaboot.core.web.handler;

import com.alibaba.fastjson.JSON;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.bean.RequestSessionBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public interface IRequestHandler {

    void handle(HttpServletRequest req, HttpServletResponse resp, RequestSessionBean requestSessionBean) throws Exception;

    static void respondRequestWithJson(R responseObj, HttpServletResponse response) throws IOException {
        if (responseObj != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String json = JSON.toJSON(responseObj).toString();
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

    /**
     * close the connection by close write stream.
     */
    static void closeRequestConnection (HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.flush();
        writer.close();
    }
}
