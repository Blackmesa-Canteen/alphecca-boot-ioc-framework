package io.swen90007sm2.alpheccaboot.core.mvc;

import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.bean.Request;
import io.swen90007sm2.alpheccaboot.bean.Worker;
import io.swen90007sm2.alpheccaboot.core.ioc.ClassManager;
import io.swen90007sm2.alpheccaboot.common.util.ReflectionUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * helper for Controller level
 * @author xiaotian
 */
public class ControllerManager {

    /**
     * This map holds: [RequestParam(Method + URL)] to [Handler method].
     * Worker object is a map of Handler class and a method
     */
    private static final Map<Request, Worker> REQUEST_WORKER_MAP = new HashMap<>();

    public static Map<Request, Worker> getRequestMap () {
        return REQUEST_WORKER_MAP;
    }

    static {
        Set<Class<?>> controllerClassSet = ClassManager.getControllerClassSet();
        if(!controllerClassSet.isEmpty()) {
            for (Class<?> controllerClass :controllerClassSet) {
                mapRequestWithWorkerInTheController(controllerClass);
            }
        }
    }

    private static void mapRequestWithWorkerInTheController(Class<?> controllerClass) {
        // get all methods from this class using reflect
        Method[] controllerMethods = ReflectionUtil.getMethods(controllerClass);

        String requestRootPath = "";
        // get Controller's root request path
        Controller controllerAnnotationObj = controllerClass.getAnnotation(Controller.class);
        String basePathFromAnno = controllerAnnotationObj.path();
        if (StringUtils.isNotEmpty(basePathFromAnno)) {
            requestRootPath += basePathFromAnno;
        }

        if (ArrayUtils.isNotEmpty(controllerMethods)) {
            for (Method handlerMethod : controllerMethods) {
                // check RequestMapper annotation on this method
                if (handlerMethod.isAnnotationPresent(HandlesRequest.class)) {
                    HandlesRequest annotationObj = handlerMethod.getAnnotation(HandlesRequest.class);

                    // fetch from annotation metadata
                    String requestMethod = annotationObj.method().name();
                    String requestPath = requestRootPath+ annotationObj.path();

                    // seal up into request map
                    Request request = new Request(requestMethod, requestPath);
                    Worker worker = new Worker(controllerClass, handlerMethod);
                    REQUEST_WORKER_MAP.put(request, worker);
                }
            }
        }
    }
}
