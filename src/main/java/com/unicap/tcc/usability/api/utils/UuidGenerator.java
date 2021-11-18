package com.unicap.tcc.usability.api.utils;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

public class UuidGenerator {

    public static UUID generateUuid(String key) {
        return Generators.nameBasedGenerator().generate(key);
    }
}
