package com.unicap.tcc.usability.api.models.dto.assessment;

import com.unicap.tcc.usability.api.models.assessment.Attribute;
import com.unicap.tcc.usability.api.models.assessment.answer.PlanVariableAnswers;
import com.unicap.tcc.usability.api.models.enums.ScalesEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@NoArgsConstructor
@AllArgsConstructor
@Builder
public @Data
class AssessmentVariablesDTO {

    private UUID assessmentUid;
    private List<AttributeDTO> variables;
    private List<ScalesEnum> scale;
    private PlanVariableAnswers planVariableAnswers;

    public Set<Attribute> updateVariableSet(Set<Attribute> attributes) {
        if (CollectionUtils.isEmpty(attributes)) {
            return this.variables.stream()
                    .filter(attributeDTO -> StringUtils.isNotEmpty(attributeDTO.getVariables())
                            || StringUtils.isNotEmpty(attributeDTO.getObtainedBy()))
                    .map(attributeDTO -> Attribute.builder()
                            .obtainedBy(attributeDTO.getObtainedBy())
                            .variables(attributeDTO.getVariables())
                            .usabilityAttribute(attributeDTO.getUsabilityAttribute())
                            .build())
                    .collect(Collectors.toSet());
        }
        return attributes.stream().peek(attribute ->
                this.variables.forEach(attributeDTO -> {
                    if (attributeDTO.getUsabilityAttribute() == attribute.getUsabilityAttribute()) {
                        attribute.setObtainedBy(attributeDTO.getObtainedBy());
                        attribute.setVariables(attributeDTO.getVariables());
                    }
                })).collect(Collectors.toSet());

    }
}
