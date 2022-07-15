package com.hxcel.globalhealth.admin.controller.regulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.apache.commons.lang.StringUtils;

import com.hxcel.globalhealth.domain.platform.model.Regulation;
import com.hxcel.globalhealth.domain.platform.model.enums.RegulationTypeCd;
import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.admin.controller.regulation.validator.RegulationValidator;

/**
 * <p>Title: RegulationFormController</p>
 * <p>Description: Handles working with a single regulation</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/regulation/edit.admin")
@SessionAttributes(types = Regulation.class)
public class RegulationFormController {
    private static final Logger log = LoggerFactory.getLogger(RegulationFormController.class);
    private final PlatformManager platformManager;
    private String FORM = "regulation.edit";


    @Autowired
    public RegulationFormController(PlatformManager platformManager) {
        this.platformManager = platformManager;
    }

    @ModelAttribute(value = "types")
    protected RegulationTypeCd[] populateRegulationType() {
        return RegulationTypeCd.values();
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false) String id, ModelMap model) throws Exception {
        Regulation regulation = null;

        if (StringUtils.isBlank(id)) {
            regulation = new Regulation();
        } else {
            regulation = platformManager.getRegulation(id);
        }

        model.addAttribute("regulation", regulation);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@ModelAttribute(value = "regulation") Regulation regulation,
                                   BindingResult result, SessionStatus status) throws Exception {
        new RegulationValidator().validate(regulation, result);
        String view = null;

        if (result.hasErrors()) {
            view = FORM;
        } else {

            view = "redirect:/secure/regulation/search.admin?view=regulations.data";

            platformManager.saveOrUpdateRegulation(regulation);
            status.setComplete();

        }

        return view;
    }
}