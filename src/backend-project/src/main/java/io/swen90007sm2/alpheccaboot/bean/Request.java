package io.swen90007sm2.alpheccaboot.bean;

import java.util.Objects;

/**
 * a bean used to seal up incoming request
 *
 * @author xiaotian
 */
public class Request {

    /*
        request method : GET, POST...
     */
    private String requestMethod;

    /*
        request path defined in controller: "like/{xxx}"
     */
    private String requestPath;

    /*
       stores path pattern, converted from requestPath
     */
    private String requestPathPattern;

    public Request() {
    }

    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
        this.requestPathPattern = getPathPattern(requestPath);
    }

    /**
     * format the path to pattern string: from "/user/{name}" to pattern string "^/user/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$"
     */
    private static String getPathPattern(String path) {
        // replace {xxx} placeholders with regular expressions.
        String originPattern = path.replaceAll("(\\{\\w+})", "[\\\\u4e00-\\\\u9fa5_a-zA-Z0-9]+");
        String pattern = "^" + originPattern + "/?$";
        return pattern.replaceAll("/+", "/");
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public String getRequestPathPattern() {
        return requestPathPattern;
    }

    /**
     * need to override the equals logic, to only compare Path pattern and method
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return requestMethod.equals(request.requestMethod) && requestPathPattern.equals(request.requestPathPattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestMethod, requestPathPattern);
    }
}
