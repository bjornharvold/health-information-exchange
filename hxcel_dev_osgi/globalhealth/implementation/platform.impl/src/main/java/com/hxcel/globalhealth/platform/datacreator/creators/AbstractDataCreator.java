package com.hxcel.globalhealth.platform.datacreator.creators;

import com.hxcel.globalhealth.platform.datacreator.DataCreator;
import com.hxcel.globalhealth.platform.datacreator.DataCreatorException;

/**
 * User: bjorn
 * Date: Aug 21, 2008
 * Time: 3:05:40 PM
 */
public abstract class AbstractDataCreator implements DataCreator {

    public String getName() {
        return name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public abstract void create() throws DataCreatorException;

    // Spring IoC
    private String name;
    private Boolean enabled;

    public void setName(String name) {
        this.name = name;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
