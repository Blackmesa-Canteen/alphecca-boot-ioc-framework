package io.swen90007sm2.alpheccaboot.core.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * a interface for request filter, can be used for login
 * @author xiaotian
 */
public interface IFilter {
    /**
     * filter the request
     * @param request incoming request
     * @param response response object
     * @return if pass this filter. if false, will stop at this filter.
     */
    boolean doFilter(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
