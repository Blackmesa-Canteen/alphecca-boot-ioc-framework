package io.swen90007sm2.alpheccaboot.bean;

import java.lang.reflect.Method;

/**
 * Used to seal up one handler method with relative params.
 *
 * @author Xiaotian
 */
public class Worker {

    /**
     * Controller class object from reflection, maps the method and it's handler class
     */
    private Class<?> controllerClazz;

    /**
     * the handler method that handles some request
     */
    private Method controllerMethod;

    public Worker() {
    }

    public Worker(Class<?> controllerClazz, Method controllerMethod) {
        this.controllerClazz = controllerClazz;
        this.controllerMethod = controllerMethod;
    }

    public Class<?> getControllerClazz() {
        return controllerClazz;
    }

    public void setControllerClazz(Class<?> controllerClazz) {
        this.controllerClazz = controllerClazz;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }

    public void setControllerMethod(Method controllerMethod) {
        this.controllerMethod = controllerMethod;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "handlerClazz=" + controllerClazz +
                ", handlerMethod=" + controllerMethod +
                '}';
    }
}
