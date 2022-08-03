package io.swen90007sm2.alpheccaboot.bean;

import java.lang.reflect.Method;

/**
 * Used to seal up one handler method with relative params.
 *
 * @author Xiaotian
 */
public class Worker {

    /**
     * Handler class object from reflection, maps the method and it's handler class
     */
    private Class<?> handlerClazz;

    /**
     * the handler method that handles some request
     */
    private Method handlerMethod;

    public Worker() {
    }

    public Worker(Class<?> handlerClazz, Method handlerMethod) {
        this.handlerClazz = handlerClazz;
        this.handlerMethod = handlerMethod;
    }

    public Class<?> getHandlerClazz() {
        return handlerClazz;
    }

    public void setHandlerClazz(Class<?> handlerClazz) {
        this.handlerClazz = handlerClazz;
    }

    public Method getHandlerMethod() {
        return handlerMethod;
    }

    public void setHandlerMethod(Method handlerMethod) {
        this.handlerMethod = handlerMethod;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "handlerClazz=" + handlerClazz +
                ", handlerMethod=" + handlerMethod +
                '}';
    }
}
