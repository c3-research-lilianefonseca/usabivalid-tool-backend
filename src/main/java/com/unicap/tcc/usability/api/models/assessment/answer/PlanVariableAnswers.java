package com.unicap.tcc.usability.api.models.assessment.answer;

import com.unicap.tcc.usability.api.models.assessment.answer.enums.AnswerEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanVariableAnswers {
    private AnswerEnum learnabilityAtt;
    private AnswerEnum efficiencyAtt;
    private AnswerEnum userRetentionOverTimeAtt;
    private AnswerEnum errorRateAtt;
    private AnswerEnum satisfactionAtt;
    private AnswerEnum learnabilityMeth;
    private AnswerEnum efficiencyMeth;
    private AnswerEnum userRetentionOverTimeMeth;
    private AnswerEnum errorRateMeth;
    private AnswerEnum satisfactionMeth;
    private AnswerEnum suggestedScales;
}
