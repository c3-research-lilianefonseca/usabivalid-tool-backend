package com.unicap.tcc.usability.api.models.enums;

import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum SectionEnum {
    AP("Application"),
    GO("Goals"),
    VM("Variables And Measurement"),
    PA("Participants"),
    TM("Tasks And Materials"),
    PR("Procedure"),
    DT("Data Collection And Data Analysis"),
    TH("Threats to validity");

    private final String description;

    SectionEnum(String description) {
        this.description = description;
    }

    public static SectionEnum convert(String name) {
        for (SectionEnum value : SectionEnum.values()) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return null;
    }

    public static Set<SectionEnum> getSectionList() {
        return Stream.of(SectionEnum.values()).collect(Collectors.toSet());
    }
}
