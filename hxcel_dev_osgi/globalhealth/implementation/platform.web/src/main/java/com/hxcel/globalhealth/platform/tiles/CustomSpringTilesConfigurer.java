package com.hxcel.globalhealth.platform.tiles;

import org.springframework.web.context.ServletContextAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.util.StringUtils;
import org.apache.tiles.access.TilesAccess;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.TilesException;
import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.startup.BasicTilesInitializer;
import org.apache.tiles.definition.DefinitionsFactory;
import org.apache.tiles.factory.AbstractTilesContainerFactory;
import org.apache.tiles.context.AbstractTilesApplicationContextFactory;
import org.apache.tiles.servlet.context.ServletTilesApplicationContext;
import org.apache.tiles.servlet.context.ServletUtil;
import org.apache.tiles.servlet.context.wildcard.WildcardServletTilesApplicationContext;
import org.apache.tiles.web.util.ServletContextAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Apr 4, 2009
 * Time: 11:00:06 PM
 * Responsibility:
 */
public class CustomSpringTilesConfigurer implements ServletContextAware, InitializingBean, DisposableBean {
    private final static Logger log = LoggerFactory.getLogger(CustomSpringTilesConfigurer.class);

    private ServletContext servletContext;

    private TilesApplicationContext tilesContext;

    public List<String> definitions = null;

    protected TilesContainer createTilesContainer() throws TilesException {
        log.info("Trying to create new TilesContainer...");

        CustomTilesListenerInitializer initializer = new CustomTilesListenerInitializer();
        tilesContext = new WildcardServletTilesApplicationContext(servletContext);
        initializer.initialize(tilesContext);

        TilesContainer result = ServletUtil.getContainer(servletContext);

        if (result != null) {
            log.info("TilesContainer created successfully");
        } else {
            log.error("Could not instantiate TilesContainer. Null.");
        }

        return result;
    }

    public void setDefinitions(List<String> definitions) {
        if (definitions != null) {
            this.definitions = definitions;
        }
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        TilesContainer existingContainer = ServletUtil.getContainer(this.servletContext);

        // if the listener is already being used there is no need to instantiate it again
        if (existingContainer == null) {
            createTilesContainer();
        } else {
            log.info("TilesContainer has already been initialized with the Tiles listener. Check your deployment descriptor.");
        }
    }

    @Override
    public void destroy() throws Exception {
        TilesAccess.setContainer(this.tilesContext, null);
    }

    private class CustomTilesListenerInitializer extends BasicTilesInitializer {

        /** {@inheritDoc} */
        @Override
        protected AbstractTilesContainerFactory createContainerFactory(
                TilesApplicationContext context) {
            return new CustomTilesContainerFactory(definitions);
        }

        /** {@inheritDoc} */
        @Override
        protected TilesApplicationContext createTilesApplicationContext(
                TilesApplicationContext preliminaryContext) {
            return new WildcardServletTilesApplicationContext(
                    (ServletContext) preliminaryContext.getContext());
        }
    }
}
