/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.platform.propertyeditor;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springmodules.web.propertyeditors.ReflectivePropertyEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.hxcel.globalhealth.domain.common.model.User;

/**
 * User: Bjorn Harvold
 * Date: Aug 3, 2007
 * Time: 5:16:16 PM
 */
public class CustomPropertyEditorRegistrar implements PropertyEditorRegistrar {
    private final static Logger log = LoggerFactory.getLogger(CustomPropertyEditorRegistrar.class);

    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(User.class, userPropertyEditor);
        registry.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true));
//        registry.registerCustomEditor(FieldType.class, new EnumPropertyEditor(FieldType.class));
    }

    // Spring IoC
    private ReflectivePropertyEditor userPropertyEditor;

    @Required
    public void setUserPropertyEditor(ReflectivePropertyEditor userPropertyEditor) {
        this.userPropertyEditor = userPropertyEditor;
    }

}
