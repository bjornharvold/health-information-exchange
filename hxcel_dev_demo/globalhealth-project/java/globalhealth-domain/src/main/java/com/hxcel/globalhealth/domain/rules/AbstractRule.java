/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.rules;

import java.util.List;
import java.util.Map;

/**
 * User: Bjorn Harvold
 * Date: Nov 25, 2005
 * Time: 4:14:27 PM

 * <p/>
 * Description:
 */
public abstract class AbstractRule implements Rule {
    private List _mappings = null;
    private String _description = null;
    private String _scope = null;
    private String _name = null;
    private List<Rule> _dependencies = null;
    private Map<String, Forward> _forwards = null;
    
    private boolean _passed = false;

    public boolean executedAndPassed() {
        return _passed;
    }

    public void setPassed(boolean passed) {
        _passed = passed;
    }

    public List<String> getPatterns() {
        return _mappings;
    }

    public List<Rule> getDependencies() {
        return _dependencies;
    }

    public String getScope() {
        return _scope;
    }

    public String getName() {
        return _name;
    }

    public String getDescription() {
        return _description;
    }

    public void setScope(String scope) {
        _scope = scope;
    }

    public Map<String, Forward> getForwards() {
        return _forwards;
    }

    public Forward getForward(String name) {
        Forward result = null;

        if (_forwards != null && _forwards.containsKey(name)) {
            result = _forwards.get(name);
        }

        return result;
    }

    public void setForwards(Map<String, Forward> forwards) {
        _forwards = forwards;
    }

    public void setName(String name) {
        _name = name;
    }

    public void setPatterns(List<String> mappings) {
        _mappings = mappings;
    }

    public void setDependencies(List<Rule> dependencies) {
        _dependencies = dependencies;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public abstract Forward validate(Map<String, Object> map) throws RuleException;
}
