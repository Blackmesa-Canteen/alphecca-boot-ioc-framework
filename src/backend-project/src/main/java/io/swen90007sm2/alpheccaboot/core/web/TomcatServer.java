package io.swen90007sm2.alpheccaboot.core.web;

import io.swen90007sm2.alpheccaboot.core.config.ConfigFileManager;
import io.swen90007sm2.alpheccaboot.core.web.servlet.MyDispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * embedded Tomcat server
 *
 * @author xiaotian
 */
public class TomcatServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TomcatServer.class);
    private String hostName = "localhost";
    private Tomcat tomcat;
    private int portNumber = 8088;

    public TomcatServer() {
        String serverHostName = ConfigFileManager.getServerHostName();
        if (!StringUtils.isEmpty(serverHostName)) {
            hostName = serverHostName;
        }

        String serverPortNumberString = ConfigFileManager.getServerPortNumber();
        if (!StringUtils.isEmpty(serverPortNumberString)) {
            try {
                int thePortNumber = Integer.parseInt(serverPortNumberString);
                if (thePortNumber >= 0 && thePortNumber <= 65535) {
                    portNumber = thePortNumber;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                LOGGER.error("port number in property file is mistaken, value: {}", serverPortNumberString);
            }
        }
    }

    public TomcatServer(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    /**
     * startup the framework
     * @throws LifecycleException tomcat embed exception
     */
    public void run() throws LifecycleException {

        // init tomcat
        tomcat = new Tomcat();
        tomcat.setPort(portNumber);
        tomcat.setHostname(hostName);
        tomcat.start();
        LOGGER.info("Web API App is running at {}:{}. Good luck no bugs mate :)", hostName, portNumber);

        Context context = new StandardContext();
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());
        MyDispatcherServlet myDispatcherServlet = new MyDispatcherServlet();
        Tomcat.addServlet(context,"myDispatcherServlet", myDispatcherServlet).setAsyncSupported(true);

        // add path interceptor
        context.addServletMappingDecoded("/","myDispatcherServlet");
        tomcat.getHost().addChild(context);

        // setup thread for running tomcat server
        Thread awaitThread = new Thread("tomcat_await_thread."){
            @Override
            public void run() {
                TomcatServer.this.tomcat.getServer().await();
            }
        };
        awaitThread.setDaemon(false);
        awaitThread.start();
    }
}
