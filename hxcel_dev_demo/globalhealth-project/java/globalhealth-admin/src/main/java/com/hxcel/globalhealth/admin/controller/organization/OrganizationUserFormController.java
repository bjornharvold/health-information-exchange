package com.hxcel.globalhealth.admin.controller.organization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.apache.commons.lang.StringUtils;
import org.springmodules.web.propertyeditors.ReflectivePropertyEditor;

import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.domain.platform.model.OrganizationUser;
import com.hxcel.globalhealth.domain.platform.model.Organization;
import com.hxcel.globalhealth.domain.platform.model.enums.OrganizationTypeCd;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.common.model.enums.UserStatusCd;
import com.hxcel.globalhealth.domain.common.model.enums.SalutationCd;
import com.hxcel.globalhealth.domain.common.model.enums.SexCd;
import com.hxcel.globalhealth.domain.common.UserManager;
import com.hxcel.globalhealth.admin.controller.organization.validator.OrganizationUserValidator;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Title: OrganizationUserFormController</p>
 * <p>Description: Handles creating a new user for an organization</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/organization/user/edit.admin")
@SessionAttributes(types = OrganizationUser.class)
public class OrganizationUserFormController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationUserFormController.class);
    private final PlatformManager platformManager;
    private final UserManager userManager;
    private String FORM = "organization.user.edit";

    @Autowired
    public OrganizationUserFormController(PlatformManager platformManager, UserManager userManager) {
        this.platformManager = platformManager;
        this.userManager = userManager;
    }

    @ModelAttribute(value = "organization")
    protected Organization populateOrganization(@RequestParam(value = "organizationId", required = true) String id) throws Exception {
        return platformManager.getOrganization(id);
    }

    @ModelAttribute(value = "countries")
    protected List<Country> populateCountries() {
        List<Country> result = null;

        try {
            return platformManager.searchForCountries(null, null, null);
        } catch (DomainException e) {
            log.error("Could not load country reference data");
        }

        return result;
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
        OrganizationUser form = null;

        if (StringUtils.isNotBlank(organizationUserId)) {
            form = platformManager.getOrganizationUser(organizationUserId);
        } else {
            form = new OrganizationUser();
            form.setUser(new User());
        }

        model.addAttribute("ou", form);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "organizationId", required = true) String organizationId,
                                   @ModelAttribute(value = "ou") OrganizationUser form,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        organizationUserValidator.validate(form, result);

        if (result.hasErrors()) {
            view = FORM;
        } else {

            if (StringUtils.isBlank(form.getId())) {
                // first register the user with the system
                form.setUser(userManager.saveNewUser(form.getUser()));

                view = "redirect:/secure/organization/user/search.admin?view=organization.users.data&id=" + organizationId;
            } else {
                form.setUser(userManager.updateUser(form.getUser()));

                view = "redirect:/secure/organization/user/view.admin?view=organization.user.data&id=" + form.getId() + "&organizationId=" + organizationId;
            }

            // do not run the next method if this is Health XCEL as we have already added the relation in the first call
            if (!form.getOrganization().getOrganizationType().equals(OrganizationTypeCd.HXCEL)) {
                form = platformManager.saveOrUpdateOrganizationUser(form);
            }

            status.setComplete();
        }

        return view;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Organization.class, organizationPropertyEditor);
        binder.registerCustomEditor(Country.class, countryPropertyEditor);
    }

    @Resource(name = "organizationPropertyEditor")
    private ReflectivePropertyEditor organizationPropertyEditor;

    @Resource(name = "countryPropertyEditor")
    private ReflectivePropertyEditor countryPropertyEditor;

    @Autowired
    private OrganizationUserValidator organizationUserValidator;
}