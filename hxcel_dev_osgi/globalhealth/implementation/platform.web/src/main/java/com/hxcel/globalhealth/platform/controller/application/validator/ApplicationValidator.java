package com.hxcel.globalhealth.platform.controller.application.validator;

import com.hxcel.globalhealth.platform.spec.dto.IApplicationDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * User: Bjorn Harvold
 * Date: Sep 17, 2008
 * Time: 4:43:00 PM
 * Description:
 */
public class ApplicationValidator implements Validator {

    public boolean supports(Class clazz) {
        return IApplicationDto.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "owner", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "appVersion", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "applicationType", "error.form.field.required", null, "Required field");

    }

}
