package com.unicap.tcc.usability.api.models.assessment.answer;

import com.unicap.tcc.usability.api.models.assessment.answer.enums.AnswerEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanGoalsAnswers {
    private AnswerEnum learnability;
    private AnswerEnum efficiency;
    private AnswerEnum userRetentionOverTime;
    private AnswerEnum errorRate;
    private AnswerEnum satisfaction;
}
