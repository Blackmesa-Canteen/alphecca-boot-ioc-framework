package io.swen90007sm2.app.controller.shared.api;

import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.common.constant.ResourceConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

import static io.swen90007sm2.alpheccaboot.common.util.ClassLoadUtil.getContextClassLoader;

/**
 * @author 996Worker
 * @description main controller
 * @create 2022-08-18 23:15
 */
@Controller(path = "/")
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);


    /**
     * show api document at backend index
     */
    @HandlesRequest(path = "/", method = RequestMethod.GET)
    public void welcome(HttpServletRequest request, HttpServletResponse response) {
//        return R.ok().setData("Hello, This is alphecca hotel booking backend api server. API Doc address: HOST_NAME/api");
        showApiHtmlDoc(request, response);
    }

    private static void showApiHtmlDoc(HttpServletRequest request, HttpServletResponse response) {
        try {
            // CORS
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Max-Age", "4200");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");

            ServletContext servletContext = request.getServletContext();
            response.setContentType("text/html");

            // init streams for file IO
            InputStream inputStream = getContextClassLoader().getResourceAsStream(
                    ResourceConstant.API_DOC_FILE_NAME
            );

            if (inputStream == null) {
                LOGGER.error("Can not get API Doc input Stream.");
                throw new RuntimeException("Can not get API Doc input Stream.");
            }
            ServletOutputStream outStream = response.getOutputStream();

            // file buffer
            int len=-1;
            byte[] b=new byte[2048];
            while((len=inputStream .read(b))!=-1){
                outStream.write(b,0,len);
            }

            //close stream
            inputStream.close();
            outStream.close();

        } catch (Exception e) {
            LOGGER.error("render API Doc error: ", e);
            throw new RuntimeException(e);
        }
    }
}