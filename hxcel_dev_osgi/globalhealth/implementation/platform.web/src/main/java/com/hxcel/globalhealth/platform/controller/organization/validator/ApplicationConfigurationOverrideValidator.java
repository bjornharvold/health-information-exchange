package com.hxcel.globalhealth.platform.controller.organization.validator;

import com.hxcel.globalhealth.platform.spec.dto.IApplicationConfigurationOverrideDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * User: bjorn
 * Date: Sep 25, 2008
 * Time: 1:32:16 PM
 */
public class ApplicationConfigurationOverrideValidator implements Validator {
    public boolean supports(Class clazz) {
        return IApplicationConfigurationOverrideDto.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "original", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "error.form.field.required", null, "Required field");
    }
}
