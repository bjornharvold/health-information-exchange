package com.hxcel.globalhealth.platform.tiles;

/**
 * User: Bjorn Harvold
 * Date: Apr 4, 2009
 * Time: 10:21:22 PM
 * Responsibility:
 */

import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.el.ArrayELResolver;
import javax.el.BeanELResolver;
import javax.el.CompositeELResolver;
import javax.el.ELResolver;
import javax.el.ListELResolver;
import javax.el.MapELResolver;
import javax.el.ResourceBundleELResolver;
import javax.servlet.ServletContext;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.awareness.TilesRequestContextFactoryAware;
import org.apache.tiles.context.ChainedTilesRequestContextFactory;
import org.apache.tiles.context.TilesRequestContextFactory;
import org.apache.tiles.definition.DefinitionsFactoryException;
import org.apache.tiles.evaluator.AttributeEvaluator;
import org.apache.tiles.evaluator.el.ELAttributeEvaluator;
import org.apache.tiles.evaluator.el.TilesContextBeanELResolver;
import org.apache.tiles.evaluator.el.TilesContextELResolver;
import org.apache.tiles.factory.BasicTilesContainerFactory;
import org.apache.tiles.factory.TilesContainerFactoryException;
import org.apache.tiles.impl.BasicTilesContainer;
import org.apache.tiles.impl.mgmt.CachingTilesContainer;
import org.apache.tiles.locale.LocaleResolver;
import org.apache.tiles.renderer.impl.BasicRendererFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.ServletContextResourcePatternResolver;


/**
 * Custom Tiles container factory to customize Tiles behaviour.
 */
public class CustomTilesContainerFactory extends BasicTilesContainerFactory {
    private final static Logger log = LoggerFactory.getLogger(CustomTilesContainerFactory.class);

    public CustomTilesContainerFactory() {
    }

    public CustomTilesContainerFactory(List<String> definitions) {
        this.definitions = definitions;
    }

    private List<String> definitions = null;

    /**
     * {@inheritDoc}
     */
    @Override
    protected BasicTilesContainer instantiateContainer(TilesApplicationContext applicationContext) {
        return new CachingTilesContainer();
    }

    @Override
    protected void registerRequestContextFactory(String className,
                                                 List<TilesRequestContextFactory> factories,
                                                 TilesRequestContextFactory parent) {
        TilesRequestContextFactory retValue = null;
        try {
            Class<? extends TilesRequestContextFactory> clazz = Class
                    .forName(className).asSubclass(
                            TilesRequestContextFactory.class);
            retValue = clazz.newInstance();
            if (parent != null
                    && retValue instanceof TilesRequestContextFactoryAware) {
                ((TilesRequestContextFactoryAware) retValue)
                        .setRequestContextFactory(parent);
            }
        } catch (ClassNotFoundException e) {
            if (log.isDebugEnabled()) {
                log.debug("Cannot find JspTilesContextFactory, ignoring problem", e);
            }
        } catch (InstantiationException e) {
            throw new TilesContainerFactoryException(
                    "Cannot instantiate JspTilesContextFactory", e);
        } catch (IllegalAccessException e) {
            throw new TilesContainerFactoryException(
                    "Cannot access default constructor JspTilesContextFactory",
                    e);
        }
        if (retValue != null) {
            factories.add(retValue);
        }
    }

    /**
     * Register elements of a chained request context factory.
     *
     * @param contextFactory The request context factory to use.
     * @since 2.1.1
     */
    @Override
    protected void registerChainedRequestContextFactories(ChainedTilesRequestContextFactory contextFactory) {
        List<TilesRequestContextFactory> factories = new ArrayList<TilesRequestContextFactory>(3);
        registerRequestContextFactory(
                "org.apache.tiles.servlet.context.ServletTilesRequestContextFactory",
                factories, contextFactory);
//        registerRequestContextFactory(
//                "org.apache.tiles.portlet.context.PortletTilesRequestContextFactory",
//                factories, contextFactory);
        registerRequestContextFactory(
                "org.apache.tiles.jsp.context.JspTilesRequestContextFactory",
                factories, contextFactory);

//        registerRequestContextFactory(
//                FreeMarkerTilesRequestContextFactory.class.getName(),
//                factories, contextFactory);
//        registerRequestContextFactory(
//                VelocityTilesRequestContextFactory.class.getName(),
//                factories, contextFactory);

        contextFactory.setFactories(factories);
    }

    /**
     * {@inheritDoc}
     */
    /*
    @Override
    protected void registerAttributeRenderers(
            BasicRendererFactory rendererFactory, TilesApplicationContext applicationContext,
            TilesRequestContextFactory contextFactory,
            TilesContainer container, AttributeEvaluator evaluator) {
        super.registerAttributeRenderers(rendererFactory, applicationContext, contextFactory,
                container, evaluator);
    }
    */

    /**
     * {@inheritDoc}
     */
    @Override
    protected AttributeEvaluator createEvaluator(TilesApplicationContext applicationContext,
                                                 TilesRequestContextFactory contextFactory,
                                                 LocaleResolver resolver) {
        ELAttributeEvaluator evaluator = new ELAttributeEvaluator();
        evaluator.setApplicationContext(applicationContext);

        ELResolver elResolver = new CompositeELResolver() {
            {
                add(new TilesContextELResolver());
                add(new TilesContextBeanELResolver());
                add(new ArrayELResolver(false));
                add(new ListELResolver(false));
                add(new MapELResolver(false));
                add(new ResourceBundleELResolver());
                add(new BeanELResolver(false));
            }
        };
        evaluator.setResolver(elResolver);

        return evaluator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<URL> getSourceURLs(TilesApplicationContext applicationContext,
                                      TilesRequestContextFactory contextFactory) {
        List<URL> urls = new ArrayList<URL>();
        Resource[] resources= null;
        ResourcePatternResolver resolver = new ServletContextResourcePatternResolver((ServletContext) applicationContext.getContext());

        try {
            // if no defs were passed during instantiation. Look in default places
            if (definitions == null) {

                resources = resolver.getResources("/WEB-INF/**/tiles-defs*.xml");
                createResourceUrl(urls, resources);

                /* more default places to look
                urls.add(applicationContext.getResource(
                        "classpath:/tiles-defs.xml"));

                urls.add(applicationContext.getResource(
                        "classpath:/org/apache/tiles/freemarker-classpath-defs.xml"));
                urls.add(applicationContext.getResource(
                    "classpath:/org/apache/tiles/velocity-classpath-defs.xml"));
                */
            } else {
                for (String def : definitions) {
                    if (def.indexOf("*") > -1) {
                        // this is a wildcard definition
                        resources = resolver.getResources(def);
                        createResourceUrl(urls, resources);
                    } else {
                        urls.add(resolver.getResource(def).getURL());
                    }
                }
            }
        } catch (IOException e) {
            throw new DefinitionsFactoryException("Cannot load definition URLs", e);
        }

        if (!urls.isEmpty()) {
            log.info("Found these tiles definitions:");
            for (URL url : urls) {
                log.info(url.toString());
            }
        }

        return urls;
    }

    private void createResourceUrl(List<URL> urls, Resource[] resources) throws IOException {
        if (resources != null && resources.length > 0) {
            for (Resource resource : resources) {
                urls.add(resource.getURL());
            }
        }
    }
}
