package io.swen90007sm2.alpheccaboot.core.web.filter;

import io.swen90007sm2.alpheccaboot.annotation.filter.Filter;
import io.swen90007sm2.alpheccaboot.common.util.ReflectionUtil;
import io.swen90007sm2.alpheccaboot.core.ioc.BeanManager;
import io.swen90007sm2.alpheccaboot.core.ioc.ClassManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author xiaotian
 * @description a manager holds all filter
 * @create 2022-08-02 22:46
 */
public class FilterManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanManager.class);

    private static Map<String, IFilter> FILTER_MAP;

    static {
        FILTER_MAP = new HashMap<>();
        Set<Class<?>> filterAnnotatedClassSet = ClassManager.getFilterAnnotatedClassSet();
        for (Class<?> filterClass : filterAnnotatedClassSet) {
            IFilter filterObj = (IFilter) ReflectionUtil.genNewInstanceByClass(filterClass);
            Filter annotation = filterClass.getAnnotation(Filter.class);
            String filterName = annotation.name();

            if (FILTER_MAP.containsKey(filterName)) {
                LOGGER.error("Duplicated request filter name!!! Ignored");
            } else {
                FILTER_MAP.put(filterName, filterObj);
            }
        }
    }

    public static IFilter getRequestFilterByName(String filterName) {
        return FILTER_MAP.get(filterName);
    }
}