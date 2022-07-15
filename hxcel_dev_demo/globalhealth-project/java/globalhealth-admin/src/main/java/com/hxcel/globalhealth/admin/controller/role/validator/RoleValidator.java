package com.hxcel.globalhealth.admin.controller.role.validator;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.apache.commons.lang.StringUtils;
import com.hxcel.globalhealth.domain.platform.model.ApplicationConfigurationOverride;
import com.hxcel.globalhealth.domain.platform.model.Organization;
import com.hxcel.globalhealth.domain.common.model.Role;

/**
 * User: bjorn
 * Date: Sep 25, 2008
 * Time: 1:32:16 PM
 */
public class RoleValidator implements Validator {
    private final static String ROLE_PREFIX = "ROLE_";
    public boolean supports(Class clazz) {
        return Role.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        Role role = (Role) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "error.form.field.required", null, "Required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "statusCode", "error.form.field.required", null, "Required field");

        if (StringUtils.isNotBlank(role.getStatusCode()) && !StringUtils.startsWith(role.getStatusCode(), "ROLE_")) {
            errors.rejectValue("statusCode", "error.form.field.syntax", new String[]{"must begin with ROLE_"}, "Syntax is wrong: must beging with ROLE_");
        }
    }
}