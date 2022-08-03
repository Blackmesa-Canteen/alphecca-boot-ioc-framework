package io.swen90007sm2.alpheccaboot.core.web.handler;

import io.swen90007sm2.alpheccaboot.annotation.filter.AppliesFilter;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.bean.RequestSessionBean;
import io.swen90007sm2.alpheccaboot.bean.Worker;
import io.swen90007sm2.alpheccaboot.common.util.ReflectionUtil;
import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.alpheccaboot.core.mvc.factory.ParameterResolverFactory;
import io.swen90007sm2.alpheccaboot.core.mvc.resolver.IParameterResolver;
import io.swen90007sm2.alpheccaboot.core.web.filter.IFilter;
import io.swen90007sm2.alpheccaboot.core.web.filter.FilterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * handling POST, PUT, DELETE request, returns json
 *
 * @author xiatian
 */
public class PostRequestHandler implements IRequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostRequestHandler.class);
    public static final String APPLICATION_JSON = "application/json";

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp, RequestSessionBean requestSessionBean) throws Exception {

        if (req.getContentType() != null && req.getContentType().equals(APPLICATION_JSON)) {
            handleJsonRequest(req, resp, requestSessionBean);

        } else {
            // if request body is not JSON body, use basic handler
            // expose HttpServletRequest and HttpServletResponse to handler parameter
            handleBasicRequest(req, resp, requestSessionBean);
        }
    }

    /**
     * handler with HttpServletRequest, HttpServletResponse param
     */
    private static void handleBasicRequest(HttpServletRequest req, HttpServletResponse resp, RequestSessionBean requestSessionBean) throws Exception {
        Worker worker = requestSessionBean.getWorkerNeeded();
        if (worker != null) {
            Method targetMethod = worker.getHandlerMethod();

            // perform request filter logic
            if (targetMethod.isAnnotationPresent(AppliesFilter.class)) {
                AppliesFilter annotation = targetMethod.getAnnotation(AppliesFilter.class);
                String[] filterNames = annotation.filterNames();
                List<IFilter> filterList = new LinkedList<>();
                for (String filterName : filterNames) {
                    IFilter filterObj = FilterManager.getRequestFilterByName(filterName);
                    if (filterObj != null) {
                        filterList.add(filterObj);
                    } else {
                        LOGGER.error("Filter [{}] not found on method [{}]",
                                filterName, targetMethod.getName());
                    }

                }

                for(IFilter filter : filterList) {
                    boolean passed = filter.doFilter(req, resp);

                    if (!passed) {
                        LOGGER.info("Didn't pass request filter: [{}]", filter.getClass().getName());
                        IRequestHandler.closeRequestConnection(resp);
                        return;
                    }
                    LOGGER.info("passed request filter: [{}]", filter.getClass().getName());
                }
            }

            Parameter[] targetMethodParameters = targetMethod.getParameters();

            List<Object> paramObjList = new ArrayList<>();
            for (Parameter parameter : targetMethodParameters) {
                IParameterResolver parameterResolver = ParameterResolverFactory.getResolverForParameter(parameter);
                if (parameterResolver != null) {
                    Object param = parameterResolver.resolve(requestSessionBean, parameter);
                    paramObjList.add(param);
                }
            }

            Object handlerBean = BeanManager.getBeanFromBeanMapByClass(worker.getHandlerClazz());

            if (targetMethod.getReturnType().equals(void.class)) {
                ReflectionUtil.invokeMethodWithoutResult(handlerBean, targetMethod, paramObjList.toArray());
                IRequestHandler.closeRequestConnection(resp);
            } else {
                Object methodCallingResult = ReflectionUtil.invokeMethod(handlerBean, targetMethod, paramObjList.toArray());
                IRequestHandler.respondRequestWithJson((R) methodCallingResult, resp);
            }

        } else {
            LOGGER.info("Worker miss matched in PostRequestHandler");
        }
    }

    private static void handleJsonRequest(HttpServletRequest req, HttpServletResponse resp, RequestSessionBean requestSessionBean) throws Exception {
        Worker worker = requestSessionBean.getWorkerNeeded();
        if (worker != null) {
            String jsonStr = parseJsonString(req);
            requestSessionBean.setJsonBodyString(jsonStr);
            Method targetMethod = worker.getHandlerMethod();

            Parameter[] targetMethodParameters = targetMethod.getParameters();


            List<Object> paramObjList = new ArrayList<>();
            for (Parameter parameter : targetMethodParameters) {
                IParameterResolver parameterResolver = ParameterResolverFactory.getResolverForParameter(parameter);
                if (parameterResolver != null) {
                    Object param = parameterResolver.resolve(requestSessionBean, parameter);
                    paramObjList.add(param);
                }
            }

            Object handlerBean = BeanManager.getBeanFromBeanMapByClass(worker.getHandlerClazz());

            try {
                if (targetMethod.getReturnType().equals(void.class)) {
                    ReflectionUtil.invokeMethodWithoutResult(handlerBean, targetMethod, paramObjList.toArray());
                    IRequestHandler.closeRequestConnection(resp);
                } else {
                    Object methodCallingResult = ReflectionUtil.invokeMethod(handlerBean, targetMethod, paramObjList.toArray());
                    IRequestHandler.respondRequestWithJson((R) methodCallingResult, resp);
                }
            } catch (IOException e) {
                LOGGER.info("handleRestfulResponse IO err: ", e);
                throw new RuntimeException(e);
            }


        } else {
            LOGGER.info("Worker miss matched in PostRequestHandler");
        }
    }

    /**
     * doPost will read JSON from request body to get param
     */
    private static String parseJsonString(HttpServletRequest request) {

        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            BufferedReader reader = request.getReader();
            while((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("doPost exception: ", e);
            throw new RuntimeException(e);
        }

        return sb.toString();
    }
}
