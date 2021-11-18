package com.unicap.tcc.usability.api.models.dto.assessment;

import com.unicap.tcc.usability.api.models.enums.UsabilityAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
public @Data
class AttributeDTO {

    private UsabilityAttribute usabilityAttribute;
    private String variables;
    private String obtainedBy;
}
