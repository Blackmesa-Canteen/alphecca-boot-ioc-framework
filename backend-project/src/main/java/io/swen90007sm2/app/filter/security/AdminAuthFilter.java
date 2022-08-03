package io.swen90007sm2.app.filter.security;

import com.alibaba.fastjson.JSON;
import io.swen90007sm2.alpheccaboot.annotation.filter.Filter;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.core.web.filter.IFilter;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 996Worker
 * @description authentication filter for admin
 * @create 2022-08-03 20:21
 */
@Filter(name = "admin-auth")
public class AdminAuthFilter implements IFilter {
    @Override
    public boolean doFilter(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return true;
    }


}