package com.unicap.tcc.usability.api.utils;

import java.util.UUID;

public class UUIDUtils {

    public static final String UUID_REGEXP = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}";

    public static boolean isValid(String uuid) {
        return uuid.matches(UUID_REGEXP);
    }

    public static boolean isValid(UUID uuid) {
        return isValid(uuid.toString());
    }

    public static UUID fromString(String uuid) {
        if (uuid == null) {
            return null;
        }
        if(!isValid(uuid)) {
            throw new IllegalArgumentException("Invalid UUID");
        }
        return UUID.fromString(uuid);
    }

    public static String toString(UUID uuid) {
        return (uuid != null) ? uuid.toString() : null;
    }
}
