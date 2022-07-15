/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.datacreation;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: bjorn
 * Date: Nov 4, 2007
 * Time: 11:17:25 AM
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface DataCreator {

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void create() throws DataCreatorException;
    
    String getName();

    Boolean getEnabled();
}
