/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.utils.cxf;

import org.apache.ws.security.WSSecurityEngineResult;
import org.apache.ws.security.WSUsernameTokenPrincipal;
import org.apache.ws.security.handler.WSHandlerResult;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;

import java.util.Vector;

/**
 * User: bjorn
 * Date: Dec 30, 2007
 * Time: 2:02:14 PM
 */

public class ValidateUserTokenInterceptor extends AbstractPhaseInterceptor {

    public ValidateUserTokenInterceptor(String s) {
        super(s);
    }

    public void handleMessage(Message message) throws Fault {
        boolean userTokenValidated = false;
        Vector result = (Vector) message.getContextualProperty(WSHandlerConstants.RECV_RESULTS);

        for (Object res1 : result) {
            WSHandlerResult res = (WSHandlerResult) res1;
            for (Object res2 : res.getResults()) {
                WSSecurityEngineResult secRes = (WSSecurityEngineResult) res2;
                WSUsernameTokenPrincipal principal = (WSUsernameTokenPrincipal) secRes.get(WSSecurityEngineResult.TAG_PRINCIPAL);

                if (principal != null && (!principal.isPasswordDigest() ||
                        principal.getNonce() == null ||
                        principal.getPassword() == null ||
                        principal.getCreatedTime() == null)) {
                    throw new RuntimeException("Invalid Security Header");
                } else {
                    userTokenValidated = true;
                }
            }
        }

        if (!userTokenValidated) {
            throw new RuntimeException("Security processing failed");
        }
    }
}

