package com.hxcel.globalhealth.platform.controller.regulation;

import com.hxcel.globalhealth.platform.spec.CountryService;
import com.hxcel.globalhealth.platform.spec.dto.IRegulationDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * User: bjorn
 * Date: Nov 7, 2008
 * Time: 11:23:32 AM
 */
@Controller
public class RegulationController {
    private static final Logger log = LoggerFactory.getLogger(RegulationController.class);
    private final static Integer DEFAULT_INDEX = 0;
    private final static Integer DEFAULT_MAX_RESULTS = 22;

    /* cannot for OSGi
    @Autowired
    public RegulationController(CountryService countryService) {
        this.countryService = countryService;
    }
    */

    /**
     * Display a list of regulations
     *
     * @param name
     * @param view
     * @param index
     * @param maxResults
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/secure/regulation/search.admin")
    protected String showRegulations(@RequestParam(value = "name", required = false)String name,
                                       @RequestParam(value = "view", required = false)String view,
                                       @RequestParam(value = "index", required = false)Integer index,
                                       @RequestParam(value = "maxResults", required = false)Integer maxResults,
                                       ModelMap map) throws Exception {
        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_MAX_RESULTS;
        }

        List<IRegulationDto> list = countryService.searchForRegulations(name, index, maxResults);
        Integer count = countryService.searchForRegulationsCount(name);
        map.addAttribute("regulations", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);

        String result = StringUtils.isBlank(view) ? "regulations.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching regulation tiles view: '" + result + "'");
        }

        return result;
    }

    /**
     * Display a single regulation
     *
     * @param regulationId
     * @param view
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/secure/regulation/view.admin")
    protected String showRegulation(@RequestParam(value = "id", required = true)String regulationId,
                                      @RequestParam(value = "view", required = false)String view,
                                      ModelMap map) throws Exception {
        IRegulationDto regulation = countryService.getRegulation(regulationId);

        map.addAttribute("regulation", regulation);

        String result = StringUtils.isBlank(view) ? "regulation.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching regulation tiles view: '" + result + "'");
        }

        return result;
    }

    /**
     * Delete a single regulation
     *
     * @param regulationId
     * @param view
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/secure/regulation/delete.admin")
    protected String deleteRegulation(@RequestParam(value = "id", required = true)String regulationId,
                                      @RequestParam(value = "view", required = false)String view,
                                      ModelMap map) throws Exception {
        countryService.deleteRegulation(regulationId);

        return showRegulations(null, view, null, null, map);
    }

    // Spring IoC
    private CountryService countryService = null;

    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }
}