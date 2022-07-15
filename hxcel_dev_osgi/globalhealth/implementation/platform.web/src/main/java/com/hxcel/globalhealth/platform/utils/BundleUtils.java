package com.hxcel.globalhealth.platform.utils;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Bundle;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

/**
 * User: Bjorn Harvold
 * Date: May 11, 2009
 * Time: 6:54:45 PM
 * Responsibility: Utility methods for OSGi bundles
 */
public class BundleUtils {

    /**
     * Sort bundles by modified date
     *
     * @param list
     * @param size
     * @return
     */
    public List<BundleWrapper> getLastModifiedBundles(Bundle[] list, int size) {
        List<BundleWrapper> result = null;

        // quick sort on last modified date
        BundleQuickSort.quicksort(list);
        Bundle[] subList = null;

        // then make a new array based on the specified size
        if (list.length > size) {
            subList = new Bundle[size];
        } else {
            subList = new Bundle[list.length];
        }

        for (int i = 0; i < subList.length; i++) {
            subList[i] = list[i];
        }

        // then we wrap the bundles
        return handleBundles(subList, null, false, false);
    }

    /**
     * This method wraps every bundle into a BundleWrapper object and filters out bundles that shouldn't
     * be displayed. It can filter on name, system/fragment bundles
     *
     * @param list
     * @param system
     * @param fragment
     * @return
     */
    public List<BundleWrapper> handleBundles(Bundle[] list, String name, Boolean system, Boolean fragment) {
        List<BundleWrapper> result = new ArrayList<BundleWrapper>();

        if (list != null) {
            for (Bundle bundle : list) {
                BundleWrapper bw = new BundleWrapper(bundle);

                // if we only want to display system or fragment bundles, else we just add our deployed bundles
                if ((system && bw.getIsSystem()) || (fragment && bw.getIsFragment())) {
                    // if we are filtering on name as well we have to check for a name match
                    if (StringUtils.isNotBlank(name) && (StringUtils.indexOf(bw.getSymbolicName().toLowerCase(), name.toLowerCase()) > -1 || StringUtils.indexOf(bw.getBundleName().toLowerCase(), name.toLowerCase()) > -1)) {
                        result.add(bw);
                    } else if (StringUtils.isBlank(name)) {
                        result.add(bw);
                    }
                } else {
                    // if we are filtering on name as well we have to check for a name match
                    if (StringUtils.isNotBlank(name) && (StringUtils.indexOf(bw.getSymbolicName().toLowerCase(), name.toLowerCase()) > -1 || StringUtils.indexOf(bw.getBundleName().toLowerCase(), name.toLowerCase()) > -1)) {
                        result.add(bw);
                    } else if (StringUtils.isBlank(name)) {
                        result.add(bw);
                    }
                }
            }
        }

        // then we sort the list according to the Comparable interface on the wrapper
        Collections.sort(result);

        return result;
    }

    /**
     * grabs the sublist of bundles to be displayed
     * @param index
     * @param maxResults
     * @return
     */
    public List<BundleWrapper> paginate(List<BundleWrapper> list, Integer index, Integer maxResults) {
        List<BundleWrapper> result = null;

        // finally we paginate
        if (list.size() > (index * maxResults + maxResults)) {
            result = list.subList(index * maxResults, index * maxResults + maxResults);
        } else {
            result = list.subList(index * maxResults, list.size());
        }

        return result;
    }
}
