package com.hxcel.globalhealth.domain.common;

import net.sf.dozer.util.mapping.MapperIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

/**
 * Created by IntelliJ IDEA.
 * User: Bjorn Harvold
 * Date: Sep 11, 2008
 * Time: 12:41:26 PM
 * Base class for Managers. Gives extending classes utility methods and beans to play with 
 */
public abstract class AbstractManager {

    // Spring IoC
    @Autowired
    protected MapperIF mapperIF;

    @Autowired
    protected MessageSource messageSource;
}
