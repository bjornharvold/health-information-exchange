package com.hxcel.globalhealth.platform.controller.organization.validator;

import com.hxcel.globalhealth.platform.spec.dto.IResourceBundleEntryOverrideDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * User: bjorn
 * Date: Sep 23, 2008
 * Time: 5:56:18 PM
 */
public class ResourceBundleEntryOverrideValidator implements Validator {
    public boolean supports(Class clazz) {
        return IResourceBundleEntryOverrideDto.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "original", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "error.form.field.required", null, "Required field");
    }
}