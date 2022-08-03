package io.swen90007sm2.app.filter;

import com.alibaba.fastjson.JSON;
import io.swen90007sm2.alpheccaboot.annotation.filter.Filter;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.core.web.filter.IFilter;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author xiaotian
 * @description simulated https://blog.csdn.net/HLH_2021/article/details/119491890
 * @create 2022-08-02 23:44
 */

@Filter(name = "authFilter")
public class AuthFilter implements IFilter {
    @Override
    public boolean doFilter(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String token= request.getHeader("auth");
        if (StringUtils.isBlank(token)) {
            responseWithFailure(response);
            return false;
        }
        return true;
    }

    private void responseWithFailure(HttpServletResponse response) throws IOException {
        R jsonObj = R.error(StatusCodeEnume.LOGIN_PASSWORD_EXCEPTION.getCode(), "auth failed");
        if (response != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String json = JSON.toJSON(jsonObj).toString();
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }
}