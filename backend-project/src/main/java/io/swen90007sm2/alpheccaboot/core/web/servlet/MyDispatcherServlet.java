package io.swen90007sm2.alpheccaboot.core.web.servlet;

import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.bean.RequestSessionBean;
import io.swen90007sm2.alpheccaboot.core.mvc.HandlerManager;
import io.swen90007sm2.alpheccaboot.core.web.factory.RequestHandlerFactory;
import io.swen90007sm2.alpheccaboot.core.web.factory.ResponseFactory;
import io.swen90007sm2.alpheccaboot.core.web.handler.IRequestHandler;
import io.swen90007sm2.alpheccaboot.exception.InternalException;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.alpheccaboot.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * NIO servlet need to be run at the start of the application
 * This is a RESTful JSON servlet
 *
 * @author xiaotian
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class MyDispatcherServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyDispatcherServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String requestMethod = req.getMethod().toUpperCase();
        String requestPath = req.getServletPath();
        req.setCharacterEncoding("utf-8");

        LOGGER.info("Incoming request: [{}] {}",requestMethod, requestPath);

        try {
            // generate request-response session bean for this new serving session
            RequestSessionBean sessionBean = genRequestSessionBean(requestMethod, requestPath);
            sessionBean.setHttpServletRequest(req);
            sessionBean.setHttpServletResponse(resp);

            IRequestHandler requestHandler = RequestHandlerFactory.get(requestMethod);
            if (sessionBean.getWorkerNeeded() != null) {
                // handle the request
                requestHandler.handle(req, resp, sessionBean);
            } else {
                LOGGER.warn("handler mismatched with request: [" + requestMethod + "] " + requestPath);
                throw new ResourceNotFoundException("handler mismatched with request: [" + requestMethod + "] " + requestPath);
            }

            LOGGER.info("Finished handling request: [{}] {}", requestMethod, requestPath);

            // returns exception json
        } catch (ResourceNotFoundException e) {
            R responseBean = ResponseFactory.getResourceNotFoundResponseBean(e.toString());
            IRequestHandler.respondRequestWithJson(responseBean, resp);
        } catch (ConstraintViolationException e) {
            R responseBean = ResponseFactory.getValidationErrResponseBean(e.getConstraintViolations());
            IRequestHandler.respondRequestWithJson(responseBean, resp);
        } catch (IllegalArgumentException | RequestException e) {
            R responseBean = ResponseFactory.getRequestErrorResponseBean(e.toString());
            IRequestHandler.respondRequestWithJson(responseBean, resp);
        } catch (InternalException e) {
            R responseBean = ResponseFactory.getServerInternalErrorResponseBean(e.toString());
            IRequestHandler.respondRequestWithJson(responseBean, resp);
        } catch (Exception e) {
            LOGGER.error("Servlet caught an Internal exception: ", e);
            R responseBean = ResponseFactory.getServerInternalErrorResponseBean("Internal error, please contact admin.");
            IRequestHandler.respondRequestWithJson(responseBean, resp);
        }
    }

    /**
     * generate the request session bean, holds some information including method, and incoming request's params.
     */
    public static RequestSessionBean genRequestSessionBean(String requestMethodText, String requestPath) {
        RequestSessionBean requestSessionBean = new RequestSessionBean();

        HandlerManager.getRequestMap().forEach(
                (requestBean, workerBean) -> {
                    Pattern pattern = Pattern.compile(requestBean.getRequestPathPattern());
                    String urlDefinedInHandler = requestBean.getRequestPath();

                    boolean isUrlMatched = pattern.matcher(requestPath).find();
                    boolean isRequestMethodMatched = requestBean.getRequestMethod().equals(requestMethodText);

                    if (isRequestMethodMatched && isUrlMatched) {
                        requestSessionBean.setPathVariableParameterMap(
                                parseIncomingPath2pathVariableMap(requestPath, urlDefinedInHandler)
                        );

                        requestSessionBean.setWorkerNeeded(workerBean);
                        LOGGER.info("Handler Mapping: Handler [{}], method [{}]",
                                workerBean.getHandlerClazz().getName(),
                                workerBean.getHandlerMethod().getName());
                    }
                });

        return requestSessionBean;
    }

    /**
     * parse the path variable from incoming request path based on url defined in handler @HandlesRequest
     * @param requestPath incoming request
     * @param urlInHandler url defined in handler @HandlesRequest
     * @return Map : {variableName: value}
     */
    private static Map<String, String> parseIncomingPath2pathVariableMap(String requestPath, String urlInHandler) {
        String[] requestParams = requestPath.split("/");
        String[] urlParams = urlInHandler.split("/");
        Map<String, String> res = new HashMap<>();
        for (int i = 1; i < urlParams.length; i++) {
            res.put(urlParams[i].replace("{", "").replace("}", ""), requestParams[i]);
        }
        return res;
    }
}
