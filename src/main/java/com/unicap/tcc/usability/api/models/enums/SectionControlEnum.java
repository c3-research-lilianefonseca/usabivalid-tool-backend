package com.unicap.tcc.usability.api.models.enums;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum SectionControlEnum {
    AVAILABLE,
    BUSY;

    public static Optional<SectionControlEnum> convert(String name) {
        for (SectionControlEnum scale : SectionControlEnum.values()) {
            if (scale.name().equalsIgnoreCase(name)) {
                return Optional.of(scale);
            }
        }
        return Optional.empty();
    }
}
