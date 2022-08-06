package io.swen90007sm2.alpheccaboot.core.mvc.resolver;

import io.swen90007sm2.alpheccaboot.bean.RequestSessionBean;
import io.swen90007sm2.alpheccaboot.exception.RequestException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

/**
 * handles @FileParamParameter annotated param, can be used in file upload with POST method.
 * e.g.
 * public R upload(@FileParamParameter(value="img")UploadedFile img)
 *
 * @author xiaotian
 */

@Deprecated
public class FileParamParameterResolver implements IParameterResolver {

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    @Override
    public Object resolve(RequestSessionBean requestSessionBean, Parameter parameter) throws Exception {
        HttpServletRequest req = requestSessionBean.getHttpServletRequest();
        HttpServletResponse resp = requestSessionBean.getHttpServletResponse();

        if (!req.getContentType().equals(MULTIPART_FORM_DATA)) {
            throw new RequestException("@FileParamParameter can only be used in post and multipart/form-data request.");
        }

        return null;
    }
}
