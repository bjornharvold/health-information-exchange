/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.calendar;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Bjorn Harvold
 * Date: Nov 12, 2006
 * Time: 10:22:51 AM
 */
public abstract class AbstractCosmoManager {
    private final static Logger log = LoggerFactory.getLogger(AbstractCosmoManager.class);

    /**
     * Created an HttpClient for cosmo's root user. Root username and password will be used if username and/or password
     * are empty.
     * @return
     */
    protected HttpClient createHttpClient(String username, String password) {
        if (log.isTraceEnabled()) {
            log.trace("Entering createHttpClient");
        }

        HttpClient httpClient = new HttpClient();
        String u = null;
        String p = null;

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            u = cosmoRootUser;
            p = cosmoRootPassword;
        } else {
            u = username;
            p = password;
        }

        Credentials credentials = new UsernamePasswordCredentials(u, p);
        httpClient.getState().setCredentials(new AuthScope(cosmoHost, cosmoPort, AuthScope.ANY_REALM), credentials);
        httpClient.getParams().setAuthenticationPreemptive(true);


        httpClient.setHostConfiguration(createHostConfiguration());

        if (log.isTraceEnabled()) {
            log.trace("Exiting createHttpClient");
        }

        return httpClient;
    }

    protected HostConfiguration createHostConfiguration() {
        HostConfiguration hostConfig = new HostConfiguration();
        hostConfig.setHost(cosmoHost, cosmoPort);

        return hostConfig;
    }

    // Spring IoC
    protected String cosmoRootUser;
    protected String cosmoRootPassword;
    protected String cosmoHost;
    protected Integer cosmoPort;

    public void setCosmoRootUser(String cosmoRootUser) {
        this.cosmoRootUser = cosmoRootUser;
    }

    public void setCosmoRootPassword(String cosmoRootPassword) {
        this.cosmoRootPassword = cosmoRootPassword;
    }


    public void setCosmoHost(String cosmoHost) {
        this.cosmoHost = cosmoHost;
    }

    public void setCosmoPort(Integer cosmoPort) {
        this.cosmoPort = cosmoPort;
    }




}
