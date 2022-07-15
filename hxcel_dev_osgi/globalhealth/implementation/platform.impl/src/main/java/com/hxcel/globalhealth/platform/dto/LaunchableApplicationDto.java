package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.platform.spec.dto.*;
import com.hxcel.globalhealth.platform.spec.IKeyValuePair;
import com.hxcel.globalhealth.platform.utils.KeyValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.ArrayList;

/**
 * User: Bjorn Harvold
 * Date: Oct 30, 2008
 * Time: 11:45:46 AM
 * Description: We use it to wrap an application or organizationlicense entity and expose their values
 */
public class LaunchableApplicationDto implements ILaunchableApplicationDto {
    private final static Logger log = LoggerFactory.getLogger(LaunchableApplicationDto.class);

    private IApplicationDto application;
    private IOrganizationLicenseDto organizationLicense;
    private List<IApplicationConfigurationDto> configurations;
    private List<IApplicationConfigurationOverrideDto> overrides;

    /**
     * Use this for when the application is owned by the organization
     *
     * @param app
     * @param configurations
     */
    public LaunchableApplicationDto(IApplicationDto app, List<IApplicationConfigurationDto> configurations) {
        this.application = app;
        this.configurations = configurations;
    }

    /**
     * Use this when the application is licensed to an organization
     *
     * @param license
     * @param configurations
     * @param overrides
     */
    public LaunchableApplicationDto(IOrganizationLicenseDto license, List<IApplicationConfigurationDto> configurations, List<IApplicationConfigurationOverrideDto> overrides) {
        this.organizationLicense = license;
        this.configurations = configurations;
        this.overrides = overrides;
    }

    public IApplicationDto getApplication() {
        if (application != null) {
            return application;
        } else if (organizationLicense != null) {
            return organizationLicense.getLicense().getApplication();
        }

        log.error("No application entity is present! Cannot continue.");

        return null;
    }

    public List<IKeyValuePair> getConfigurations() {
        List<IKeyValuePair> result = null;

        if (application != null && configurations != null) {
            result = new ArrayList<IKeyValuePair>();

            for (IApplicationConfigurationDto configuration : configurations) {
                KeyValuePair kv = new KeyValuePair(configuration.getKey(), configuration.getValue());

                result.add(kv);
            }
        } else if (organizationLicense != null && (configurations != null || overrides != null)) {
            result = new ArrayList<IKeyValuePair>();

            for (IApplicationConfigurationDto configuration : configurations) {
                KeyValuePair kv = new KeyValuePair(configuration.getKey(), configuration.getValue());

                // here we override default values
                for (IApplicationConfigurationOverrideDto override : overrides) {
                    if (StringUtils.equals(configuration.getId(), override.getOriginal().getId()) && configuration.getExportable()) {
                        kv.setValue(override.getValue());
                    }
                }
                result.add(kv);
            }
        }

        return result;
    }

    public String getConfigurationForJSON() {
        List<IKeyValuePair> list = getConfigurations();
        StringBuilder sb = new StringBuilder();

        for (IKeyValuePair pair : list) {
            sb.append(pair.getKey());
            sb.append(": \"");
            sb.append(pair.getValue());
            sb.append("\", ");
        }

        if (sb.length() > 2) {
            sb.delete(sb.length() - 2, sb.length() - 1);
        }

        return sb.toString();
    }
}
