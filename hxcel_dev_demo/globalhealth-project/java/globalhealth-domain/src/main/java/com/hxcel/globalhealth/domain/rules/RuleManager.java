/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.rules;

import java.util.Map;

/**
 * User: Bjorn Harvold
 * Date: Nov 25, 2005
 * Time: 10:07:39 AM

 *
 * Description: Manages all business rules that are used by the rules servlet filter
 */
public interface RuleManager {
    Forward executeSiteRules(Map<String, Object> map) throws RuleException;
    Forward executeActionRules(Map<String, Object> map) throws RuleException;
}
