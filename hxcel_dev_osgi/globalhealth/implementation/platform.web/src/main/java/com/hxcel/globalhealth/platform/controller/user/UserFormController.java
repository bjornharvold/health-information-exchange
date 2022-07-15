package com.hxcel.globalhealth.platform.controller.user;

import com.hxcel.globalhealth.platform.spec.dto.ICountryDto;
import com.hxcel.globalhealth.common.spec.model.enums.SalutationCd;
import com.hxcel.globalhealth.common.spec.model.enums.SexCd;
import com.hxcel.globalhealth.platform.controller.user.validator.UserValidator;
import com.hxcel.globalhealth.platform.spec.CountryService;
import com.hxcel.globalhealth.platform.spec.UserService;
import com.hxcel.globalhealth.platform.spec.dto.IUserDto;
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
 * <p>Title: UserFormController</p>
 * <p>Description: Handles working with a single user</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/user/edit.admin")
@SessionAttributes("userForm")
public class UserFormController {
    private static final Logger log = LoggerFactory.getLogger(UserFormController.class);
    private String FORM = "user.edit";

    /* cannot for OSGi
    @Autowired
    public UserFormController(UserService userService,
                              CountryService countryService) {
        this.userService = userService;
        this.countryService = countryService;
    }
    */

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
    protected String setupForm(@RequestParam(value = "id", required = false) String id, ModelMap model) throws Exception {
        IUserDto user = null;

        if (StringUtils.isBlank(id)) {
            user = userService.createUser();
        } else {
            user = userService.getUser(id);
        }

        model.addAttribute("userForm", user);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@ModelAttribute(value = "userForm") IUserDto user,
                                   BindingResult result, SessionStatus status) throws Exception {

        userValidator.validate(user, result);
        String view = null;

        if (result.hasErrors()) {
            view = FORM;
        } else {
            if (StringUtils.isBlank(user.getId())) {
                user = userService.saveNewUser(user);
                view = "redirect:/secure/user/search.admin?view=users.data";
            } else {
                view = "redirect:/secure/user/view.admin?view=user.data&id=" + user.getId();
                user = userService.updateUser(user);
            }

            status.setComplete();
        }

        return view;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(ICountryDto.class, countryPropertyEditor);
    }

    // Spring IoC
    @Autowired
    private UserValidator userValidator;

    // Spring IoC
    private UserService userService = null;
    private CountryService countryService = null;
    private PropertyEditor countryPropertyEditor;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    public void setCountryPropertyEditor(PropertyEditor countryPropertyEditor) {
        this.countryPropertyEditor = countryPropertyEditor;
    }
}