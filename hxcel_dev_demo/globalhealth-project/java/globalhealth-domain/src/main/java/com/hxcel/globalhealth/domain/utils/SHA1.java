/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.utils;

import org.bouncycastle.util.encoders.Base64;

import java.security.MessageDigest;

/**
 * User: bjorn
 * Date: Dec 31, 2007
 * Time: 2:47:30 PM
 */
public class SHA1 {
    public static String genSHA1(String clearText) {
        String sha1Hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] digest = md.digest(clearText.getBytes());
            sha1Hash = new String(Base64.encode(digest));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sha1Hash;
    }
}
