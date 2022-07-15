package com.hxcel.globalhealth.jetty;

import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.jetty.webapp.Configuration;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

/**
 * User: bjorn
 * Date: Dec 16, 2007
 * Time: 10:24:57 AM
 */
public class JettyServer {
    private final static Logger log = LoggerFactory.getLogger(JettyServer.class);
    private static Server server = null;
    private final static String dir = "/globalhealth-project/java/integration-tests";

    public void start() throws Exception {
        if (server == null) {
            // server will get stopped and started on every unit test
            // we only want to set the user directory once
            String workingDirectory = System.getProperty("user.dir");

            // set it if hasn't already been set
            if (workingDirectory.indexOf(dir) == -1) {
                System.setProperty("user.dir", System.getProperty("user.dir") + dir);
                log.info("Working directory: " + System.getProperty("user.dir"));
            }

            log.info("Starting Jetty Server");
            server = new Server();

            SelectChannelConnector connector = new SelectChannelConnector();
            connector.setPort(8811);
            server.setConnectors(new Connector[]{connector});

            WebAppContext webappcontext = new WebAppContext("test-ws-webapp", "/");

            webappcontext.setConfigurationClasses(new String[]{
                    "org.mortbay.jetty.webapp.WebInfConfiguration",
                    "org.mortbay.jetty.webapp.WebXmlConfiguration"});

            HandlerCollection handlers = new HandlerCollection();
            handlers.setHandlers(new Handler[]{webappcontext, new DefaultHandler()});

            server.setHandler(handlers);
            server.setStopAtShutdown(true);

        }

        if (!server.isRunning()) {
            server.start();
        } else {
            log.info("Jetty server has already started. No need to start it again.");
        }

        log.info("Server ready...");
    }

    public void stop() throws Exception {
        //log.info("Stopping Jetty Server");

        /*
        if (server != null) {
            server.stop();
        }
        */
    }

    public static void main(String[] args) throws Exception {
        JettyServer jetty = new JettyServer();
        jetty.start();
    }
}
