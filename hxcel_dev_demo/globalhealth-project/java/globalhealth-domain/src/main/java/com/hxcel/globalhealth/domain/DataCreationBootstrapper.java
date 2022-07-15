/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import com.hxcel.globalhealth.domain.datacreation.DataCreator;
import com.hxcel.globalhealth.domain.datacreation.DataCreationManager;

/**
 * User: bjorn
 * Date: Nov 4, 2007
 * Time: 11:17:07 AM
 */
public class DataCreationBootstrapper {
    private final static Logger log = LoggerFactory.getLogger(DataCreationBootstrapper.class);

    public static void main(String[] args) {
        DataCreationBootstrapper dcm = new DataCreationBootstrapper();
        dcm.doIt();
    }

    private void doIt() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"spring-domain-beans-for-main-app.xml", "spring-domain-property-configurer-bean.xml"});

        if (ctx != null) {
            DataCreationManager dcm = (DataCreationManager) ctx.getBean("dataCreationManager");

            if (dcm != null) {
                dcm.init();
            }
        }
    }

    // Spring IoC
    private List<DataCreator> dataCreators = null;

    @Required
    public void setDataCreators(List<DataCreator> dataCreators) {
        this.dataCreators = dataCreators;
    }
}