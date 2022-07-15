package com.hxcel.globalhealth.domain.datacreation.creators;

import com.hxcel.globalhealth.domain.datacreation.DataCreator;
import com.hxcel.globalhealth.domain.datacreation.DataCreatorException;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;

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
