package io.swen90007sm2.app.security.filter;

import io.swen90007sm2.alpheccaboot.annotation.filter.Filter;
import io.swen90007sm2.alpheccaboot.core.web.filter.IFilter;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.common.util.Assert;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import io.swen90007sm2.app.security.helper.TokenHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * abstract auth filter
 *
 * @author xiaotian
 */
public abstract class AbstractAuthFilter implements IFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAuthFilter.class);


    abstract boolean performRoleAuthorization(AuthToken tokenBean) throws RequestException;

    @Override
    public boolean doFilter(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String tokenStr = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        if (StringUtils.isEmpty(tokenStr)) {

            // if not providing token, 403
            throw new RequestException(
                    StatusCodeEnume.REQUEST_FORBIDDEN_EXCEPTION.getMessage(),
                    StatusCodeEnume.REQUEST_FORBIDDEN_EXCEPTION.getCode());
        }

        // parse token here, can also check expiration
        AuthToken authToken = TokenHelper.parseAuthTokenString(tokenStr);
        return performRoleAuthorization(authToken);
    }
}
