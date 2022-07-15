package com.hxcel.globalhealth.platform.controller.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.osgi.context.BundleContextAware;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

import com.hxcel.globalhealth.platform.utils.BundleQuickSort;
import com.hxcel.globalhealth.platform.utils.BundleWrapper;
import com.hxcel.globalhealth.platform.utils.BundleUtils;
import com.hxcel.globalhealth.platform.utils.LoggerBundleListener;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * <p>Title: ServiceController</p>
 * <p>Description: This controller handles all service pages</p>
 *
 * @author Bjorn Harvold
 */
@Controller
public class ServiceController implements BundleContextAware {
    private static final Logger log = LoggerFactory.getLogger(ServiceController.class);
    private final static Integer DEFAULT_INDEX = 0;
    private final static Integer DEFAULT_MAX_RESULTS = 10;
    private final static Integer DEFAULT_CHILD_DATA_MAX_RESULTS = 20;
    private final BundleUtils bundleUtils = new BundleUtils();
    private final static String MESSAGE = "bundleMessage";

    @RequestMapping(value = "/secure/service/search.admin")
    protected String showServices(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "view", required = false) String view,
                                  @RequestParam(value = "system", required = false) Boolean system,
                                  @RequestParam(value = "fragment", required = false) Boolean fragment,
                                  @RequestParam(value = "index", required = false) Integer index,
                                  @RequestParam(value = "maxResults", required = false) Integer maxResults,
                                  ModelMap map) throws Exception {

        if (system == null) {
            system = false;
        }
        if (fragment == null) {
            fragment = false;
        }
        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_MAX_RESULTS;
        }

        // OsgiBundleUtils u = OsgiBundleUtils.;
        // OsgiServiceReferenceUtils u2 = OsgiServiceReferenceUtils.;
        // this is where we do our bundle magic
        Bundle[] bundles = bundleContext.getBundles();
        int count = 0;

        if (bundles != null) {

            // use only the bundles we want
            List<BundleWrapper> list = bundleUtils.handleBundles(bundles, name, system, fragment);

            // grab the total list count
            count = list.size();

            // paginate list
            list = bundleUtils.paginate(list, index, maxResults);

            map.addAttribute("services", list);
        }

        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);

        String result = StringUtils.isBlank(view) ? "services.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching service tiles view: '" + result + "'");
        }

        return result;
    }

    /**
     * Starts up a bundle that can be started
     *
     * @param id
     * @return
     */
    @RequestMapping("/secure/service/start.admin")
    protected String startBundle(@RequestParam(value = "id", required = true) Long id,
                                 @RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "view", required = false) String view,
                                 @RequestParam(value = "system", required = false) Boolean system,
                                 @RequestParam(value = "fragment", required = false) Boolean fragment,
                                 @RequestParam(value = "index", required = false) Integer index,
                                 @RequestParam(value = "maxResults", required = false) Integer maxResults,
                                 ModelMap map) throws Exception {
        Bundle bundle = bundleContext.getBundle(id);
        String msg = null;

        try {
            if (bundle != null) {
                switch (bundle.getState()) {
                    case Bundle.ACTIVE:
                        msg = "Bundle " + bundle.getSymbolicName() + " has already been started.";

                        if (log.isInfoEnabled()) {
                            log.info(msg);
                        }

                        map.addAttribute(MESSAGE, msg);

                        break;
                    case Bundle.STARTING:
                        msg = "Bundle " + bundle.getSymbolicName() + " already received the start command and is starting...";

                        if (log.isInfoEnabled()) {
                            log.info(msg);
                        }

                        map.addAttribute(MESSAGE, msg);

                        break;
                    case Bundle.STOPPING:
                        msg = "Bundle " + bundle.getSymbolicName() + " already received the stop command and is stopping...";

                        if (log.isInfoEnabled()) {
                            log.info(msg);
                        }

                        map.addAttribute(MESSAGE, msg);

                        break;
                    case Bundle.UNINSTALLED:
                        msg = "Bundle " + bundle.getSymbolicName() + " has been uninstalled and cannot be started again...";

                        if (log.isInfoEnabled()) {
                            log.info(msg);
                        }

                        map.addAttribute(MESSAGE, msg);

                        break;
                    case Bundle.INSTALLED:
                    case Bundle.RESOLVED:
                        msg = "Bundle " + bundle.getSymbolicName() + " is starting... This might take some time. Please be patient.";

                        if (log.isInfoEnabled()) {
                            log.info(msg);
                        }

                        bundle.start();

                        map.addAttribute(MESSAGE, msg);
                }
            } else {
                log.error("Cannot find bundle with id: " + id);
            }
        } catch (BundleException ex) {
            log.error("There was an error while starting bundle: " + bundle.getSymbolicName(), ex);
            map.addAttribute(MESSAGE, ex.getMessage());
        }

        // if view is not defined, use default
        view = StringUtils.isBlank(view) ? "services.data" : view;

        return showServices(null, "services.data", system, fragment, index, maxResults, map);
    }

    /**
     * Stops a bundle
     *
     * @param id
     * @return
     */
    @RequestMapping("/secure/service/stop.admin")
    protected String stopBundle(@RequestParam(value = "id", required = true) Long id,
                                 @RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "view", required = false) String view,
                                 @RequestParam(value = "system", required = false) Boolean system,
                                 @RequestParam(value = "fragment", required = false) Boolean fragment,
                                 @RequestParam(value = "index", required = false) Integer index,
                                 @RequestParam(value = "maxResults", required = false) Integer maxResults,
                                 ModelMap map) throws Exception {
        Bundle bundle = bundleContext.getBundle(id);
        String msg = null;

        try {
            if (bundle != null) {
                switch (bundle.getState()) {
                    case Bundle.STARTING:
                        msg = "Bundle " + bundle.getSymbolicName() + " already received the start command and is starting...";

                        if (log.isInfoEnabled()) {
                            log.info(msg);
                        }

                        map.addAttribute(MESSAGE, msg);

                        break;
                    case Bundle.STOPPING:
                        msg = "Bundle " + bundle.getSymbolicName() + " already received the stop command and is stopping...";

                        if (log.isInfoEnabled()) {
                            log.info(msg);
                        }

                        map.addAttribute(MESSAGE, msg);

                        break;
                    case Bundle.UNINSTALLED:
                        msg = "Bundle " + bundle.getSymbolicName() + " has been uninstalled and cannot be stopped.";

                        if (log.isInfoEnabled()) {
                            log.info(msg);
                        }

                        map.addAttribute(MESSAGE, msg);

                        break;
                    case Bundle.INSTALLED:
                        msg = "Bundle " + bundle.getSymbolicName() + " is installed but not yet active and therefore cannot be stopped yet.";

                        if (log.isInfoEnabled()) {
                            log.info(msg);
                        }

                        map.addAttribute(MESSAGE, msg);

                        break;
                    case Bundle.RESOLVED:
                        msg = "Bundle " + bundle.getSymbolicName() + " is resolved but not yet active and therefore cannot be stopped yet.";

                        if (log.isInfoEnabled()) {
                            log.info(msg);
                        }

                        map.addAttribute(MESSAGE, msg);
                        break;
                    case Bundle.ACTIVE:
                        msg = "Bundle " + bundle.getSymbolicName() + " is stopping... This might take some time. Please be patient.";

                        if (log.isInfoEnabled()) {
                            log.info(msg);
                        }

                        bundle.stop();

                        map.addAttribute(MESSAGE, msg);

                        break;
                }
            } else {
                log.error("Cannot find bundle with id: " + id);
            }
        } catch (BundleException ex) {
            log.error("There was an error while stopping bundle: " + bundle.getSymbolicName(), ex);
            map.addAttribute(MESSAGE, ex.getMessage());
        }

        // if view is not defined, use default
        view = StringUtils.isBlank(view) ? "services.data" : view;

        return showServices(null, "services.data", system, fragment, index, maxResults, map);
    }

    /**
     * Refresh up a bundle
     *
     * @param id
     * @return
     */
    @RequestMapping("/secure/service/reload.admin")
    protected String refreshBundle(@RequestParam(value = "id", required = true) Long id,
                                 @RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "view", required = false) String view,
                                 @RequestParam(value = "system", required = false) Boolean system,
                                 @RequestParam(value = "fragment", required = false) Boolean fragment,
                                 @RequestParam(value = "index", required = false) Integer index,
                                 @RequestParam(value = "maxResults", required = false) Integer maxResults,
                                 ModelMap map) throws Exception {
        Bundle bundle = bundleContext.getBundle(id);
        String msg = null;

        try {
            if (bundle != null) {
                switch (bundle.getState()) {
                    case Bundle.UNINSTALLED:
                        msg = "Bundle " + bundle.getSymbolicName() + " has already been uninstalled.";

                        if (log.isInfoEnabled()) {
                            log.info(msg);
                        }

                        map.addAttribute(MESSAGE, msg);

                        break;
                    case Bundle.STARTING:
                    case Bundle.STOPPING:
                    case Bundle.INSTALLED:
                    case Bundle.RESOLVED:
                    case Bundle.ACTIVE:
                        msg = "Bundle " + bundle.getSymbolicName() + " has been sent the reload command";

                        if (log.isInfoEnabled()) {
                            log.info(msg);
                        }

                        bundle.update();

                        map.addAttribute(MESSAGE, msg);

                        break;
                }
            } else {
                log.error("Cannot find bundle with id: " + id);
            }
        } catch (BundleException ex) {
            log.error("There was an error while reloading bundle: " + bundle.getSymbolicName(), ex);
            map.addAttribute(MESSAGE, ex.getMessage());
        }

        // if view is not defined, use default
        view = StringUtils.isBlank(view) ? "services.data" : view;

        return showServices(null, "services.data", system, fragment, index, maxResults, map);
    }

    /**
     * Uninstall a bundle
     *
     * @param id
     * @return
     */
    @RequestMapping("/secure/service/uninstall.admin")
    protected String uninstallBundle(@RequestParam(value = "id", required = true) Long id,
                                 @RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "view", required = false) String view,
                                 @RequestParam(value = "system", required = false) Boolean system,
                                 @RequestParam(value = "fragment", required = false) Boolean fragment,
                                 @RequestParam(value = "index", required = false) Integer index,
                                 @RequestParam(value = "maxResults", required = false) Integer maxResults,
                                 ModelMap map) throws Exception {
        Bundle bundle = bundleContext.getBundle(id);
        String msg = null;

        try {
            if (bundle != null) {
                switch (bundle.getState()) {
                    case Bundle.UNINSTALLED:
                        msg = "Bundle " + bundle.getSymbolicName() + " has already been uninstalled.";

                        if (log.isInfoEnabled()) {
                            log.info(msg);
                        }

                        map.addAttribute(MESSAGE, msg);

                        break;
                    case Bundle.STARTING:
                    case Bundle.STOPPING:
                    case Bundle.INSTALLED:
                    case Bundle.RESOLVED:
                    case Bundle.ACTIVE:
                        msg = "Bundle " + bundle.getSymbolicName() + " has been sent the uninstall command... This might take some time. Please be patient.";

                        if (log.isInfoEnabled()) {
                            log.info(msg);
                        }

                        bundle.uninstall();

                        map.addAttribute(MESSAGE, msg);

                        break;
                }
            } else {
                log.error("Cannot find bundle with id: " + id);
            }
        } catch (BundleException ex) {
            log.error("There was an error while uninstalling bundle: " + bundle.getSymbolicName(), ex);
            map.addAttribute(MESSAGE, ex.getMessage());
        }

        // if view is not defined, use default
        view = StringUtils.isBlank(view) ? "services.data" : view;

        return showServices(null, "services.data", system, fragment, index, maxResults, map);
    }


    // Spring IoC
    private BundleContext bundleContext;

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
        this.bundleContext.addBundleListener(new LoggerBundleListener());
    }
}