package io.swen90007sm2.alpheccaboot.core.mvc.factory;

import io.swen90007sm2.alpheccaboot.annotation.mvc.PathVariable;
import io.swen90007sm2.alpheccaboot.annotation.mvc.RequestJsonBody;
import io.swen90007sm2.alpheccaboot.annotation.mvc.QueryParam;
import io.swen90007sm2.alpheccaboot.core.mvc.resolver.*;

import java.lang.reflect.Parameter;

/**
 * Factory returns correct param resolver based on method parameter's type
 *
 * @author xiaotian
 */
public class ParameterResolverFactory {
    public static IParameterResolver getResolverForParameter(Parameter parameter) {
        if (parameter.isAnnotationPresent(QueryParam.class)) {
            return new QueryParamParameterResolver();
        }
        if (parameter.isAnnotationPresent(PathVariable.class)) {
            return new PathVariableParameterResolver();
        }
        if (parameter.isAnnotationPresent(RequestJsonBody.class)) {
            return new RequestJsonParamResolver();
        }

        return new NoneAnnotatedParameterResolver();
    }
}
