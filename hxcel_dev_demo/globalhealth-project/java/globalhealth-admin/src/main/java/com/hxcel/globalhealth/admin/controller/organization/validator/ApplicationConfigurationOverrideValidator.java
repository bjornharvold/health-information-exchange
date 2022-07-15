package com.hxcel.globalhealth.admin.controller.organization.validator;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import com.hxcel.globalhealth.domain.platform.model.ApplicationConfigurationOverride;

/**
 * User: bjorn
 * Date: Sep 25, 2008
 * Time: 1:32:16 PM
 */
public class ApplicationConfigurationOverrideValidator implements Validator {
    public boolean supports(Class clazz) {
        return ApplicationConfigurationOverride.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "original", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "error.form.field.required", null, "Required field");
    }
}
