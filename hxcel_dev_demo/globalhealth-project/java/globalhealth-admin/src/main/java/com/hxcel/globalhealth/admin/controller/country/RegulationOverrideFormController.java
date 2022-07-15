package com.hxcel.globalhealth.admin.controller.country;

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

import com.hxcel.globalhealth.domain.platform.model.Regulation;
import com.hxcel.globalhealth.domain.platform.model.RegulationOverride;
import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.admin.controller.country.validator.RegulationOverrideValidator;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Title: CountryRegulationOverrideFormController</p>
 * <p>Description: Handles working with a a regulation override</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/country/regulation/override/edit.admin")
@SessionAttributes(types = RegulationOverride.class)
public class RegulationOverrideFormController {
    private static final Logger log = LoggerFactory.getLogger(RegulationOverrideFormController.class);
    private final static String FORM = "regulation.override.edit";
    private final PlatformManager platformManager;

    @Autowired
    public RegulationOverrideFormController(PlatformManager platformManager) {
        this.platformManager = platformManager;
    }

    @ModelAttribute(value = "regulations")
    protected List<Regulation> populateRegulations() {
        List<Regulation> result = null;

        try {
            result = platformManager.searchForRegulations(null, null, null);
        } catch (DomainException e) {
            log.error("Could not load regulations list", e);
        }

        return result;
    }

    @ModelAttribute(value = "country")
    protected Country populateCountry(@RequestParam(value = "countryId", required = true)String countryId) {
        Country result = null;

        try {
            result = platformManager.getCountry(countryId);
        } catch (DomainException e) {
            log.error("Could not load country with id: " + countryId, e);
        }

        return result;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false)String id, ModelMap model) throws Exception {
        RegulationOverride regulationOverride = null;

        if (StringUtils.isBlank(id)) {
            regulationOverride = new RegulationOverride();
        } else {
            regulationOverride = platformManager.getRegulationOverride(id);
        }

        model.addAttribute("override", regulationOverride);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "countryId", required = true) String countryId,
                                   @ModelAttribute(value = "override") RegulationOverride regulationOverride,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        try {
            new RegulationOverrideValidator().validate(regulationOverride, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {

                regulationOverride = platformManager.saveOrUpdateRegulationOverride(regulationOverride);
                status.setComplete();
                view = "redirect:/secure/country/regulation/search.admin?id=" + countryId + "&view=country.regulation.overrides.data";
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Regulation.class, regulationPropertyEditor);
        binder.registerCustomEditor(Country.class, countryPropertyEditor);
    }

    @Resource(name = "regulationPropertyEditor")
    private ReflectivePropertyEditor regulationPropertyEditor;

    @Resource(name = "countryPropertyEditor")
    private ReflectivePropertyEditor countryPropertyEditor;
}