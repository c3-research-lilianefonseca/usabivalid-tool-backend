package com.unicap.tcc.usability.api.models.enums;

import lombok.Getter;

@Getter
public enum EScore {
    Yes(1, "Yes"),
    No(0, "No"),
    Partial(0.5, "Partial"),
    NA(-1, "Not Applicable");

    public double scoreValue;
    public String description;

    EScore(double scoreValue, String description) {
        this.scoreValue = scoreValue;
        this.description = description;
    }
}
