package io.swen90007sm2.app.filter.security;

import io.swen90007sm2.alpheccaboot.annotation.filter.Filter;
import io.swen90007sm2.alpheccaboot.core.web.filter.IFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 996Worker
 * @description authentication filter for hotelier
 * @create 2022-08-03 20:20
 */
@Filter(name = "hotelier-auth")
public class HotelierAuthFilter implements IFilter {

    @Override
    public boolean doFilter(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return true;
    }
}