package com.hxcel.globalhealth.admin.controller.application.validator;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import com.hxcel.globalhealth.domain.platform.model.License;
import com.hxcel.globalhealth.domain.platform.model.enums.LicenseTypeCd;

/**
 * User: Bjorn Harvold
 * Date: Sep 17, 2008
 * Time: 4:43:00 PM
 * Description:
 */
public class LicenseValidator implements Validator {
    public boolean supports(Class clazz) {
        return License.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        License license = (License) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "licenseType", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expirationDate", "error.form.field.required", null, "Required field");

        if ((license.getPrice() == null || license.getPrice().compareTo( (float)0) <= 0 ) && !license.getLicenseType().equals(LicenseTypeCd.FREE)) {
            errors.rejectValue("price", "error.field.value.less.than.zero", null, null);
        }
    }
    
}