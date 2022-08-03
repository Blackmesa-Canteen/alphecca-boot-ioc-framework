package io.swen90007sm2.alpheccaboot.core.mvc.resolver;

import io.swen90007sm2.alpheccaboot.bean.RequestSessionBean;

import java.lang.reflect.Parameter;

public interface IParameterResolver {

    Object resolve(RequestSessionBean requestSessionBean, Parameter parameter) throws Exception;
}
