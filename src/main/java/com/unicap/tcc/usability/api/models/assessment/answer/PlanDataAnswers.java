package com.unicap.tcc.usability.api.models.assessment.answer;

import com.unicap.tcc.usability.api.models.assessment.answer.enums.AnswerEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanDataAnswers {
    private AnswerEnum dataCollectionProcedure;
    private AnswerEnum dataCollectedAnalyzed;
    private AnswerEnum statisticalMethods;
}
