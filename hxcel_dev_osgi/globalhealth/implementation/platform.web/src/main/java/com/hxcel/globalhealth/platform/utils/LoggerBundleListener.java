package com.hxcel.globalhealth.platform.utils;

import org.osgi.framework.BundleListener;
import org.osgi.framework.BundleEvent;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * User: Bjorn Harvold
 * Date: May 14, 2009
 * Time: 10:36:53 PM
 * Responsibility: Logs events for tracking OSGi bundles being added, updated, removed etc from the container
 */
public class LoggerBundleListener implements BundleListener {
    private final static Logger log = LoggerFactory.getLogger(LoggerBundleListener.class);

    @Override
    public void bundleChanged(BundleEvent event) {
        if (event != null) {
            switch (event.getType()) {
                case BundleEvent.INSTALLED:
                    log.info(event.getBundle().getSymbolicName() + " has been INSTALLED");
                    break;
                case BundleEvent.RESOLVED:
                    log.info(event.getBundle().getSymbolicName() + " has been RESOLVED");
                    break;
                case BundleEvent.STARTED:
                    log.info(event.getBundle().getSymbolicName() + " has been STARTED");
                    break;
                case BundleEvent.STARTING:
                    log.info(event.getBundle().getSymbolicName() + " is STARTING");
                    break;
                case BundleEvent.STOPPED:
                    log.info(event.getBundle().getSymbolicName() + " has been STOPPED");
                    break;
                case BundleEvent.STOPPING:
                    log.info(event.getBundle().getSymbolicName() + " is STOPPING");
                    break;
                case BundleEvent.UNINSTALLED:
                    log.info(event.getBundle().getSymbolicName() + " has been UNINSTALLED");
                    break;
                case BundleEvent.UNRESOLVED:
                    log.info(event.getBundle().getSymbolicName() + " has been UNRESOLVED");
                    break;
                case BundleEvent.UPDATED:
                    log.info(event.getBundle().getSymbolicName() + " has been UPDATED");
                    break;
                default:
                    log.error("Received unrecognized event: " + event.toString());
            }
        }
    }
}
