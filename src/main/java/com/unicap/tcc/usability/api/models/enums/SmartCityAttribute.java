package com.unicap.tcc.usability.api.models.enums;

import lombok.Getter;

@Getter
public enum SmartCityAttribute {
    DMN("DataManagement", "Data Management"),
    AEE("ApplicationExecutionEnvironment", "Application Execution Environment"),
    SNM("SensorNetworkManagement", "Sensor Network Management"),
    DPR("DataProcessing", "Data Processing"),
    DTA("DataAccess", "Data Access"),
    SMN("ServiceManagement", "Service Management"),
    TSD("ToolsforSoftwareDevelopment", "Tools for Software Development"),
    DCM("DefiningACityModel", "Defining a City Model");

    private final String key;
    private final String description;

    SmartCityAttribute(String key, String description){
        this.key = key;
        this.description = description;
    }
}
