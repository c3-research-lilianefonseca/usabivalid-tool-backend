package com.unicap.tcc.usability.api.service.planformgenerator.factory;

import com.unicap.tcc.usability.api.exception.ResourceNotFoundException;
import com.unicap.tcc.usability.api.models.enums.SectionEnum;
import com.unicap.tcc.usability.api.models.enums.MessageType;
import com.unicap.tcc.usability.api.service.planformgenerator.PlanFromGenerator;
import com.unicap.tcc.usability.api.service.planformgenerator.impl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PlanFormGeneratorFactory {

    private final ApplicationPlanFormGenerator applicationPlanFormGenerator;
    private final DataColPlanFormGenerator dataColPlanFormGenerator;
    private final GoalsPlanFormGenerator goalsPlanFormGenerator;
    private final ParticipantsPlanFormGenerator participantsPlanFormGenerator;
    private final ProcedurePlanFormGenerator procedurePlanFormGenerator;
    private final ThreatsPlanFormGenerator threatsPlanFormGenerator;
    private final TasksPlanFormGenerator tasksPlanFormGenerator;
    private final VariablesPlanFormGenerator variablesPlanFormGenerator;

    public PlanFromGenerator getPlanFormGenerator(SectionEnum category) {
        switch (category) {
            case AP:
                return applicationPlanFormGenerator;
            case DT:
                return dataColPlanFormGenerator;
            case GO:
                return goalsPlanFormGenerator;
            case PA:
                return participantsPlanFormGenerator;
            case PR:
                return procedurePlanFormGenerator;
            case TH:
                return threatsPlanFormGenerator;
            case TM:
                return tasksPlanFormGenerator;
            case VM:
                return variablesPlanFormGenerator;
            default:
                throw new ResourceNotFoundException(String.format(MessageType.PLAN_FORM_TYPE_NOT_FOUND_ERROR.getMessage(),
                        category.name()));
        }
    }
}
