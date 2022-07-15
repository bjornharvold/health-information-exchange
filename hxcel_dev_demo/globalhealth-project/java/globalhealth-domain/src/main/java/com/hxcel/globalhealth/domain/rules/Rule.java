/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.rules;

import java.util.List;
import java.util.Map;

/**
 * User: Bjorn Harvold
 * Date: Nov 25, 2005
 * Time: 10:03:11 AM

 */
public interface Rule {
    /**
     * Is true if rule passed / false if it hasn't been run yet
     * @return boolean
     */
    boolean executedAndPassed();

    /**
     * sets whether it passed or not
     * @param passed
     */
    void setPassed(boolean passed);

    /**
     * Core of the rules engine. If there's an error, it returns a Forward object with the mapping
     * to the jsp / controller to go to. Otherwise it returns null. Throws exception if something goes
     * horribly wrong.
     * @return Forward
     * @throws RuleException
     */
    Forward validate(Map<String, Object> map) throws RuleException;

    /**
     * Returns the regular expression that should be tested to see if we should run this rule
     * @return String
     */
    List<String> getPatterns();

    /**
     * Returns list of dependency Rules this rule has. Dependencies are object references to other rules.
     * They might have been run already or not.
     * @return List
     */
    List<Rule> getDependencies();

    /**
     * Returns scope of the rule. Valid values are "site" and "controller". Site rules are executed on every request.
     * Action rules are executed when the mapping finds a match in the URL.
     * @return String
     */
    String getScope();

    /**
     * Returns the rule name. The name is the key to the Rule object in the _rules map.
     * @return String
     */
    String getName();

    /**
     * Returns short description of what the rule does.
     * @return String
     */
    String getDescription();

    /**
     * Returns map of forwards associated with this rule. Forwards are the places the user can be directed
     * to in the event of an error
     * @return Map
     */
    Map<String, Forward> getForwards();

    /**
     * Returns a Forward object based on the given name
     * @param name
     * @return Forward
     */
    Forward getForward(String name);

    /**
     * Sets rule scope
     * @param scope
     */
    void setScope(String scope);

    /**
     * Sets rule name
     * @param name
     */
    void setName(String name);

    /**
     * Sets rule reg ex mapping
     * @param mappings
     */
    void setPatterns(List<String> mappings);

    /**
     * Sets rule object references
     * @param dependencies
     */
    void setDependencies(List<Rule> dependencies);

    /**
     * Sets rule description
     * @param description
     */
    void setDescription(String description);

    /**
     * Sets available forwards for this rule
     * @param forwards
     */
    void setForwards(Map<String, Forward> forwards);
}
