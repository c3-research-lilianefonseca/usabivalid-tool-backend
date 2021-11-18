package com.unicap.tcc.usability.api.models.enums;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum UserProfileEnum {
    AUTHOR,
    COLLABORATOR;

    public static Optional<UserProfileEnum> convert(String name) {
        for (UserProfileEnum scale : UserProfileEnum.values()) {
            if (scale.name().equalsIgnoreCase(name)) {
                return Optional.of(scale);
            }
        }
        return Optional.empty();
    }
}
