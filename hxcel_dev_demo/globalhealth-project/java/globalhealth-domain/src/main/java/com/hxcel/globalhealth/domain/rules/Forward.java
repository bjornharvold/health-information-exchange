/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.rules;

/**
 * User: Bjorn Harvold
 * Date: Nov 25, 2005
 * Time: 10:13:52 AM

 * <p/>
 * Description: Just like Struts, handles the forwarding logic to a url.
 */
public class Forward {
    private boolean _redirect = false;
    private String _name = null;
    private String _path = null;

    public boolean isRedirect() {
        return _redirect;
    }

    public void setRedirect(boolean redirect) {
        this._redirect = redirect;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getPath() {
        return _path;
    }

    public void setPath(String value) {
        this._path = value;
    }

}
