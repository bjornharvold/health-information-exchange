package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.platform.spec.IKeyValuePair;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Jan 5, 2009
 * Time: 5:21:21 PM
 * Responsibility:
 */
public interface ILaunchableApplicationDto {
    IApplicationDto getApplication();

    List<IKeyValuePair> getConfigurations();

    String getConfigurationForJSON();
}
