/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.utils.cxf;

import org.apache.ws.security.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;

/**
 * User: bjorn
 * Date: Dec 30, 2007
 * Time: 2:01:05 PM
 */
 public class ClientPasswordCallback implements CallbackHandler {
     public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
         WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];

         pc.setPassword("W6ph5Mm5Pz8GgiULbPgzG37mj9g=");
     }
 }