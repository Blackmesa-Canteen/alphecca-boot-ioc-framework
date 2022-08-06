package io.swen90007sm2.alpheccaboot.core.mvc.resolver;

import io.swen90007sm2.alpheccaboot.annotation.mvc.QueryParam;
import io.swen90007sm2.alpheccaboot.bean.RequestSessionBean;
import io.swen90007sm2.alpheccaboot.common.util.ObjectUtil;

import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * convert incoming query string to annotation's declared type
 *
 * @author xiaotian
 * @author shuang.kou:https://github.com/Snailclimb/jsoncat
 */
public class QueryParamParameterResolver implements IParameterResolver {
    @Override
    public Object resolve(RequestSessionBean requestSessionBean, Parameter parameter) throws Exception {
        QueryParam queryParamAnno = parameter.getDeclaredAnnotation(QueryParam.class);
        String targetParamName = queryParamAnno.value();

        // this map has been put query param key-values from the incoming request.
        Map<String, String> queryParameterMap = requestSessionBean.getQueryParameterMap();
        String targetParamValue = queryParameterMap.get(targetParamName);

        if (targetParamValue == null) {
            if (queryParamAnno.require() && queryParamAnno.defaultValue().isEmpty()) {
                throw new IllegalArgumentException("The specified parameter " + targetParamName + " can not be null!");
            } else {
                targetParamValue = queryParamAnno.defaultValue();
            }
        }

        return ObjectUtil.convertString2Object(targetParamValue, parameter.getType());
    }
}
