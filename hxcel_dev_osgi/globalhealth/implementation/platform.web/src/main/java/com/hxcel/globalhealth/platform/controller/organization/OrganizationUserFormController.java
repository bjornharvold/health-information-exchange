package com.hxcel.globalhealth.platform.controller.organization;

import com.hxcel.globalhealth.platform.spec.dto.ICountryDto;
import com.hxcel.globalhealth.common.spec.model.enums.SalutationCd;
import com.hxcel.globalhealth.common.spec.model.enums.SexCd;
import com.hxcel.globalhealth.platform.controller.organization.validator.OrganizationUserValidator;
import com.hxcel.globalhealth.platform.spec.CountryService;
import com.hxcel.globalhealth.platform.spec.OrganizationService;
import com.hxcel.globalhealth.platform.spec.UserService;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationUserDto;
import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationTypeCd;
import com.hxcel.globalhealth.platform.spec.model.enums.UserStatusCd;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.beans.PropertyEditor;
import java.util.List;

/**
 * <p>Title: OrganizationUserFormController</p>
 * <p>Description: Handles creating a new user for an organization</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/user/edit.admin")
@SessionAttributes("organizationUserForm")
public class OrganizationUserFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationUserFormController.class);
    private String FORM = "organization.user.edit";

    /* cannot for OSGi
    @Autowired
    public OrganizationUserFormController(OrganizationService organizationService,
                                          UserService userService,
                                          CountryService countryService) {
        this.organizationService = organizationService;
        this.userService = userService;
        this.countryService = countryService;
    }
    */

    @ModelAttribute(value = "organization")
    protected IOrganizationDto populateOrganization(@RequestParam(value = "organizationId", required = true) String id) throws Exception {
        return organizationService.getOrganization(id);
    }

    @ModelAttribute(value = "countries")
    protected List<ICountryDto> populateCountries() throws Exception {
        return countryService.searchForCountries(null, null, null);
    }

    @ModelAttribute(value = "statuses")
    protected UserStatusCd[] populateUserStatuses() {
        return UserStatusCd.values();
    }

    @ModelAttribute(value = "salutations")
    protected SalutationCd[] populateSalutations() {
        return SalutationCd.values();
    }

    @ModelAttribute(value = "sexes")
    protected SexCd[] populateSexes() {
        return SexCd.values();
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false) String organizationUserId,
                               ModelMap model) throws Exception {
        IOrganizationUserDto form = null;

        if (StringUtils.isNotBlank(organizationUserId)) {
            form = organizationService.getOrganizationUser(organizationUserId);
        } else {
            form = organizationService.createOrganizationUser();
            form.setUser(userService.createUser());
        }

        model.addAttribute("organizationUserForm", form);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "organizationId", required = true) String organizationId,
                                   @ModelAttribute(value = "organizationUserForm") IOrganizationUserDto form,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        organizationUserValidator.validate(form, result);

        if (result.hasErrors()) {
            view = FORM;
        } else {

            if (StringUtils.isBlank(form.getId())) {
                // first register the user with the system
                form.setUser(userService.saveNewUser(form.getUser()));

                view = "redirect:/secure/organization/user/search.admin?view=organization.users.data&id=" + organizationId;
            } else {
                form.setUser(userService.updateUser(form.getUser()));

                view = "redirect:/secure/organization/user/view.admin?view=organization.user.data&id=" + form.getId() + "&organizationId=" + organizationId;
            }

            // do not run the next method if this is Health XCEL as we have already added the relation in the first call
            if (!form.getOrganization().getOrganizationType().equals(OrganizationTypeCd.HXCEL)) {
                form = organizationService.saveOrUpdateOrganizationUser(form);
            }

            status.setComplete();
        }

        return view;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(IOrganizationDto.class, organizationPropertyEditor);
        binder.registerCustomEditor(ICountryDto.class, countryPropertyEditor);
    }

    @Autowired
    private OrganizationUserValidator organizationUserValidator;

    // Spring IoC
    private OrganizationService organizationService = null;
    private CountryService countryService = null;
    private UserService userService = null;
    private PropertyEditor organizationPropertyEditor;
    private PropertyEditor countryPropertyEditor;

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setOrganizationPropertyEditor(PropertyEditor organizationPropertyEditor) {
        this.organizationPropertyEditor = organizationPropertyEditor;
    }

    public void setCountryPropertyEditor(PropertyEditor countryPropertyEditor) {
        this.countryPropertyEditor = countryPropertyEditor;
    }
}