package com.unicap.tcc.usability.api.models.enums;

import lombok.Getter;

@Getter
public enum ParticipationType {
    V("Volunteer"),
    C("Compensated");

    private final String description;

    ParticipationType(String description){
        this.description = description;
    }
}
