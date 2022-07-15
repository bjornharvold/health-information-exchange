package com.hxcel.globalhealth.admin.controller.application;

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

import com.hxcel.globalhealth.domain.platform.model.ResourceBundleEntry;
import com.hxcel.globalhealth.domain.platform.model.Application;
import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.admin.controller.application.validator.ResourceBundleEntryValidator;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Title: ApplicationResourceBundleFormController</p>
 * <p>Description: Handles working with a single resource bundle entry</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/application/resourcebundle/edit.admin")
@SessionAttributes(types = ResourceBundleEntry.class)
public class ApplicationResourceBundleFormController {
    private static final Logger log = LoggerFactory.getLogger(ApplicationResourceBundleFormController.class);
    private final static String FORM = "application.resourcebundle.edit";
    private final PlatformManager platformManager;

    @Autowired
    public ApplicationResourceBundleFormController(PlatformManager platformManager) {
        this.platformManager = platformManager;
    }

    @ModelAttribute(value = "countries")
    protected List<Country> populateCountries() throws Exception {
        return platformManager.searchForCountries(null, null, null);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false) String id, ModelMap model) throws Exception {
        ResourceBundleEntry rbe = null;

        if (StringUtils.isBlank(id)) {
            rbe = new ResourceBundleEntry();
        } else {
            rbe = platformManager.getResourceBundleEntry(id);
        }

        model.addAttribute("entry", rbe);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "applicationId", required = true) String applicationId,
                                   @ModelAttribute(value = "entry") ResourceBundleEntry rbe,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        try {
            new ResourceBundleEntryValidator().validate(rbe, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {
                rbe = platformManager.saveOrUpdateResourceBundle(rbe);
                status.setComplete();
                view = "redirect:/secure/application/resourcebundle/search.admin?view=application.resourcebundle.data&id=" + applicationId;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Country.class, countryPropertyEditor);
        binder.registerCustomEditor(Application.class, applicationPropertyEditor);
    }

    @Resource(name = "countryPropertyEditor")
    private ReflectivePropertyEditor countryPropertyEditor;

    @Resource(name = "applicationPropertyEditor")
    private ReflectivePropertyEditor applicationPropertyEditor;
}