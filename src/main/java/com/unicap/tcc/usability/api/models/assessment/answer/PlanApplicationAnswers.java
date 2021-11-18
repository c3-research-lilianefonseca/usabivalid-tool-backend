package com.unicap.tcc.usability.api.models.assessment.answer;

import com.unicap.tcc.usability.api.models.assessment.answer.enums.AnswerEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanApplicationAnswers {
    private AnswerEnum projectName;
    private AnswerEnum projectDescription;
    private AnswerEnum smartCityPercentage;
    private AnswerEnum dataManagement;
    private AnswerEnum applicationExecution;
    private AnswerEnum sensorNetwork;
    private AnswerEnum dataProcessing;
    private AnswerEnum dataAccess;
    private AnswerEnum serviceManagement;
    private AnswerEnum tools;
    private AnswerEnum definingCityModel;
}
