package com.unicap.tcc.usability.api.service;


import com.unicap.tcc.usability.api.models.Scale;
import com.unicap.tcc.usability.api.models.enums.ScalesEnum;
import com.unicap.tcc.usability.api.repository.ScaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ScaleService {

    private final ScaleRepository scaleRepository;

    public Scale getScaleByUid(UUID uuid) {
        var scale = scaleRepository.findByUid(uuid);
        return scale.orElseGet(Scale::new);
    }

    public Scale getScaleByScaleEnum(ScalesEnum optionalEnumScale) {
        var scale = scaleRepository.findByAcronym(optionalEnumScale);
        return scale.orElseGet(Scale::new);
    }
}
