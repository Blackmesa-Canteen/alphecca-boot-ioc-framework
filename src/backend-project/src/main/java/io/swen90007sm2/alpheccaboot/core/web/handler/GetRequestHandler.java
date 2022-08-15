package io.swen90007sm2.alpheccaboot.core.web.handler;

import io.swen90007sm2.alpheccaboot.annotation.filter.AppliesFilter;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.bean.RequestSessionBean;
import io.swen90007sm2.alpheccaboot.bean.Worker;
import io.swen90007sm2.alpheccaboot.common.util.ReflectionUtil;
import io.swen90007sm2.alpheccaboot.common.util.UrlUtil;
import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.alpheccaboot.core.mvc.factory.ParameterResolverFactory;
import io.swen90007sm2.alpheccaboot.core.mvc.resolver.IParameterResolver;
import io.swen90007sm2.alpheccaboot.core.web.filter.IFilter;
import io.swen90007sm2.alpheccaboot.core.web.filter.FilterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * handling Get request, returns json
 *
 * @author xiatian
 */
public class GetRequestHandler implements IRequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetRequestHandler.class);

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp, RequestSessionBean requestSessionBean) throws Exception {

        // get query parameters
        Enumeration<String> parameterNames = req.getParameterNames();
        Map<String, String> queryParameterMap = new HashMap<>();
        while (parameterNames.hasMoreElements()) {
            String queryParamName = parameterNames.nextElement();
            queryParameterMap.put(queryParamName, req.getParameter(queryParamName));
        }
        // init a query parameter map to hold incoming qurey parameter strings
        requestSessionBean.setQueryParameterMap(queryParameterMap);

        Worker worker = requestSessionBean.getWorkerNeeded();

        if (worker != null) {
            Method targetMethod = worker.getControllerMethod();

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

            // traverse params in controller method, then generate correct param object for method calling
            for (Parameter param : targetMethodParameters) {
                // get correct resolver type for the param
                IParameterResolver parameterResolver = ParameterResolverFactory.getResolverForParameter(param);
                if (parameterResolver != null) {
                    Object paramObj = parameterResolver.resolve(requestSessionBean, param);
                    paramObjList.add(paramObj);

                }
            }

            Object controllerBean = BeanManager.getBeanFromBeanMapByClass(worker.getControllerClazz());

            try {
                if (targetMethod.getReturnType().equals(void.class)) {
                    ReflectionUtil.invokeMethodWithoutResult(controllerBean, targetMethod, paramObjList.toArray());
                    // IRequestHandler.closeRequestConnection(resp);
                } else {
                    Object methodCallingResult = ReflectionUtil.invokeMethod(controllerBean, targetMethod, paramObjList.toArray());
                    IRequestHandler.respondRequestWithJson((R) methodCallingResult, resp);
                }
            } catch (IOException e) {
                LOGGER.info("handleRestfulResponse IO err: ", e);
                throw new RuntimeException(e);
            }

        } else {
            LOGGER.info("Worker miss matched in GetRequestHandler");
        }

    }

    /**
     * get the query parameters. e.g. /user?name=233, get Map {'name} -> '233'}
     */
    private Map<String, String> parseQueryParamFromUrlToMap(String url) {
        return UrlUtil.getQueryParamMap(url);
    }
}
