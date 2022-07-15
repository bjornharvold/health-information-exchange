/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.test;

import java.util.Properties;
import java.util.Enumeration;
import java.util.Map;

/**
 * User: bjorn
 * Date: May 2, 2008
 * Time: 11:30:52 AM
 */
public class EnvironmentVariablesTest {
    public static void main(String[] args) {
        Map<String, String> m = System.getenv();

        for (String s : m.keySet()) {
            System.out.println(s + " = " + m.get(s));
        }

        System.setProperty("APP_ENCRYPTION_PASSWORD", "suchNunh0ly!...c0nf3d3r3at10n%$");
        Properties props = System.getProperties();

        for (Object s : props.keySet()) {
            String ss = (String) s;
            System.out.println(ss + " = " + System.getProperty(ss));
        }

        String env = System.getenv("APP_ENCRYPTION_PASSWORD");
        System.out.println("env = " + env);
    }
}
