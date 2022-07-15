package com.hxcel.globalhealth.admin.controller.organization.validator;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import com.hxcel.globalhealth.domain.platform.model.ResourceBundleEntry;
import com.hxcel.globalhealth.domain.platform.model.ResourceBundleEntryOverride;

/**
 * User: bjorn
 * Date: Sep 23, 2008
 * Time: 5:56:18 PM
 */
public class ResourceBundleEntryOverrideValidator implements Validator {
    public boolean supports(Class clazz) {
        return ResourceBundleEntryOverride.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "original", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "error.form.field.required", null, "Required field");
    }
}