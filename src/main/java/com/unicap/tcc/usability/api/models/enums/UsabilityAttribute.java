package com.unicap.tcc.usability.api.models.enums;

import lombok.Getter;

@Getter
public enum UsabilityAttribute {
    LRN("Learnability"),
    EFF("Efficiency"),
    USR("UserRetentionOT"),
    ERR("ErrorRate"),
    STF("Satisfaction");

    private final String description;

    UsabilityAttribute(String description){
        this.description = description;
    }
}
