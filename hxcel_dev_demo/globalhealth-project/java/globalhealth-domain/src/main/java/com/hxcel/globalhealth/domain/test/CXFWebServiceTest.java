/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: bjorn
 * Date: May 2, 2008
 * Time: 8:55:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class CXFWebServiceTest {
    public static void main(String[] args) {
        CXFWebServiceTest test = new CXFWebServiceTest();
        test.doit();
    }

    public void doit() {
        try {
            String[] resources = {"com/hxcel/globalhealth/domain/test/spring-domain-test-beans.xml"};
            ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(resources);
        } catch (Exception e) {
            e.printStackTrace(); 
        }

    }
}
