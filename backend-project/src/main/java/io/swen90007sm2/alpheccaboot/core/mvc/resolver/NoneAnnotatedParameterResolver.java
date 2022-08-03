package io.swen90007sm2.alpheccaboot.core.mvc.resolver;

import io.swen90007sm2.alpheccaboot.bean.RequestSessionBean;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

/**
 * none annotated param
 *
 * only support HttpServletRequest and HttpServletResponse param
 *
 * @author xiaotain
 */
public class NoneAnnotatedParameterResolver implements IParameterResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoneAnnotatedParameterResolver.class);

    @Override
    public Object resolve(RequestSessionBean requestSessionBean, Parameter parameter) throws Exception {
        HttpServletRequest req = requestSessionBean.getHttpServletRequest();
        HttpServletResponse resp = requestSessionBean.getHttpServletResponse();

        if (parameter.getType().equals(HttpServletRequest.class)) {
            return req;
        } else if (parameter.getType().equals(HttpServletResponse.class)) {
            return resp;
        } else {
            throw new RequestException("handler missing Param Annotation, " +
                    "only HttpServletRequest and HttpServletResponse type params can have no param annotation.");
        }
    }
}
