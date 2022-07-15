/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.utils.cxf;

import org.apache.ws.security.WSPasswordCallback;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;

import com.hxcel.globalhealth.domain.common.dao.UserDAO;
import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;

/**
 * User: bjorn
 * Date: Dec 30, 2007
 * Time: 2:01:05 PM
 */
public class ServerPasswordCallback implements CallbackHandler {
    private final static Logger log = LoggerFactory.getLogger(ServerPasswordCallback.class);

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];

        try {
            if (StringUtils.isNotBlank(pc.getIdentifer())) {
                User user = userDAO.getUserByUsername(pc.getIdentifer());
                pc.setPassword(user.getPassword());
            }
        } catch (PersistenceException e) {
            String error = "Couldn't retrieve user by username: " + pc.getIdentifer() + ", " + e.getMessage();
            log.error(error, e);
            throw new IOException(error);
        }
    }

    // Spring IoC

    @Autowired
    private UserDAO userDAO;
}
