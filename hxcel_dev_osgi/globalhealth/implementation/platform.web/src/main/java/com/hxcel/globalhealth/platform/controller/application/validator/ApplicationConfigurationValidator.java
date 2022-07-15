package com.hxcel.globalhealth.platform.controller.application.validator;

import com.hxcel.globalhealth.platform.spec.dto.IApplicationConfigurationDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * User: bjorn
 * Date: Sep 23, 2008
 * Time: 5:56:18 PM
 */
public class ApplicationConfigurationValidator implements Validator {
    public boolean supports(Class clazz) {
        return IApplicationConfigurationDto.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "key", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "exportable", "error.form.field.required", null, "Required field");
    }
}
