/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.datacreator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;

import java.util.List;

/**
 * User: bjorn
 * Date: Nov 4, 2007
 * Time: 11:17:07 AM
 */
public class DataCreationManager {
    private final static Logger log = LoggerFactory.getLogger(DataCreationManager.class);

    public void init() {
        if (dataCreators != null && dataCreators.size() > 0) {
            secureChannel();
            for (DataCreator dc : dataCreators) {
                try {
                    log.info("Creating data with: " + dc.getName());
                    dc.create();
                    log.info("Success: " + dc.getName());
                } catch (DataCreatorException e) {
                    log.error("Error creating data! " + e.getMessage(), e);
                }
            }
            unsecureChannel();
        }
    }

    /**
     * Have to do this in order to save roles as PlatformManager is restrictive
     */
    private void secureChannel() {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("Test", "Test",
                new GrantedAuthority[]{new GrantedAuthorityImpl("ROLE_ADMINISTRATOR")});
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    /**
     * When everything is done we can go ahead and remove that channel
     */
    private void unsecureChannel() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    // Spring IoC
    private List<DataCreator> dataCreators = null;

    @Required
    public void setDataCreators(List<DataCreator> dataCreators) {
        this.dataCreators = dataCreators;
    }
}
