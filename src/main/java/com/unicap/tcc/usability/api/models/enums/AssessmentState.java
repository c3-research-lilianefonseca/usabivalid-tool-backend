package com.unicap.tcc.usability.api.models.enums;

import lombok.Getter;

@Getter
public enum AssessmentState {
    CREATED("CREATED"),
    COLLECTING_DATA("COLLECTING DATA"),
    DATA_COLLECTED("DATA COLLECTED"),
    WAITING_REVIEW("WAITING REVIEW"),
    IN_REVIEW("IN REVIEW"),
    REVIEWED("REVIEWED"),
    COMPLETED("COMPLETED"),
    CANCELED("CANCELED");

    private String value;

    private AssessmentState(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
