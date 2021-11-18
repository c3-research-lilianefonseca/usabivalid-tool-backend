package com.unicap.tcc.usability.api.models.assessment.answer;

import com.unicap.tcc.usability.api.models.assessment.answer.enums.AnswerEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanProcedureAnswers {
    private AnswerEnum whenOccur;
    private AnswerEnum whereOccur;
    private AnswerEnum howOccur;
    private AnswerEnum howMuchTime;
    private AnswerEnum assessmentProcedureSteps;
    private AnswerEnum isPilotAssessment;
    private AnswerEnum questionsAllowed;
}
