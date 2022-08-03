package io.swen90007sm2.alpheccaboot.core.mvc.resolver;

import io.swen90007sm2.alpheccaboot.annotation.mvc.PathVariable;
import io.swen90007sm2.alpheccaboot.bean.RequestSessionBean;
import io.swen90007sm2.alpheccaboot.common.util.ObjectUtil;

import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * a helper for parsing PathVariable parameter
 *
 * @author xiaotian
 */
public class PathVariableParameterResolver implements IParameterResolver {
    @Override
    public Object resolve(RequestSessionBean requestSessionBean, Parameter parameter) {
        PathVariable pathVariableAnno = parameter.getDeclaredAnnotation(PathVariable.class);
        String targetParamName = pathVariableAnno.value();

        Map<String, String> urlParameterMap = requestSessionBean.getPathVariableParameterMap();
        String targetParamValue = urlParameterMap.get(targetParamName);

        return ObjectUtil.convertString2Object(targetParamValue, parameter.getType());
    }
}
