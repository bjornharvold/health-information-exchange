package com.hxcel.globalhealth.admin.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.apache.commons.lang.StringUtils;
import org.springmodules.web.propertyeditors.ReflectivePropertyEditor;

import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.common.model.enums.CountryCd;
import com.hxcel.globalhealth.domain.common.model.enums.UserStatusCd;
import com.hxcel.globalhealth.domain.common.model.enums.SexCd;
import com.hxcel.globalhealth.domain.common.model.enums.SalutationCd;
import com.hxcel.globalhealth.domain.common.UserManager;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.domain.platform.model.Organization;
import com.hxcel.globalhealth.admin.controller.user.validator.UserValidator;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>Title: UserFormController</p>
 * <p>Description: Handles working with a single user</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/user/edit.admin")
@SessionAttributes(types = User.class)
public class UserFormController {
    private static final Logger log = LoggerFactory.getLogger(UserFormController.class);
    private final UserManager userManager;
    private final PlatformManager platformManager;
    private String FORM = "user.edit";

    @Autowired
    public UserFormController(UserManager userManager, PlatformManager platformManager) {
        this.userManager = userManager;
        this.platformManager = platformManager;
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
    protected String setupForm(@RequestParam(value="id", required = false) String id, ModelMap model) throws Exception {
        User user = null;

        if (StringUtils.isBlank(id)) {
            user = new User();
        } else {
            user = userManager.getUser(id);
        }

        model.addAttribute("user", user);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@ModelAttribute(value="user") User user,
                            BindingResult result, SessionStatus status) throws Exception {

        userValidator.validate(user, result);
        String view = null;

        if (result.hasErrors()) {
            view = FORM;
        } else {
            if (StringUtils.isBlank(user.getId())) {
                user = userManager.saveNewUser(user);
                view = "redirect:/secure/user/search.admin?view=users.data";
            } else {
                view = "redirect:/secure/user/view.admin?view=user.data&id="+ user.getId();
                user = userManager.updateUser(user);
            }

            status.setComplete();
        }

        return view;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Country.class, countryPropertyEditor);
    }

    @Resource(name = "countryPropertyEditor")
    private ReflectivePropertyEditor countryPropertyEditor;

    // Spring IoC
    @Autowired
    private UserValidator userValidator;
}