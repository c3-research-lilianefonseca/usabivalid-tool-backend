package com.unicap.tcc.usability.api.models.assessment.answer;


import com.unicap.tcc.usability.api.models.assessment.answer.enums.AnswerEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanThreatsAnswers {
    private AnswerEnum whatThreats;
    private AnswerEnum threatsValidityControlled;
    private AnswerEnum assessmentLimitations;
    private AnswerEnum ethicalAspects;
    private AnswerEnum assessmentBiases;
}
