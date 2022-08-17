package io.swen90007sm2.alpheccaboot;

import io.swen90007sm2.alpheccaboot.common.util.ArgumentsParser;
import io.swen90007sm2.alpheccaboot.common.util.BannerUtil;
import io.swen90007sm2.alpheccaboot.common.util.LogUtil;
import io.swen90007sm2.alpheccaboot.core.AppContextLoader;
import io.swen90007sm2.alpheccaboot.core.web.TomcatServer;
import org.apache.catalina.LifecycleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * It is the handwritten IoC and embed tomcat server application entrance.
 *
 * @author xiaotian
 */
public class AlpheccaBootApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlpheccaBootApplication.class);

    /**
     * Entrance to startup the IoC web application
     */
    public static void run(String[] args) {

        // print banner
        BannerUtil.printBanner();

        LOGGER.info("----------  SWEN90007 AlpheccaBoot Ioc web Api Supporter  ----------");
        LOGGER.info("Start to run the IoC Web Api Application.");

        // mute useless message caused by CgLib and Tomcat
        LogUtil.disableIllegalReflectiveWarning();
        LogUtil.disableTomcatInitWarning();

        // load core modules
        AppContextLoader.initAppContext();

        LOGGER.info("Startup the web server...");
        // start server
        TomcatServer server = new TomcatServer();
        // if running argument have port info, override server properties
        ArgumentsParser argumentsParser = new ArgumentsParser(args);
        if (argumentsParser.hasKey("port")) {
            server.setPortNumber(argumentsParser.getInt("port", 8088));
        }
        if (argumentsParser.hasKey("host")) {
            server.setHostName(argumentsParser.getString("host", "localhost"));
        }

        try {
            server.run();
        } catch (LifecycleException e) {
            LOGGER.error("tomcat server exception: ", e);
            throw new RuntimeException(e);
        }
    }
}
