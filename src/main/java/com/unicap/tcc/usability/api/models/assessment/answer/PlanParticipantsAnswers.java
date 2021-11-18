package com.unicap.tcc.usability.api.models.assessment.answer;

import com.unicap.tcc.usability.api.models.assessment.answer.enums.AnswerEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanParticipantsAnswers {
    private AnswerEnum howManyParticipants;
    private AnswerEnum participationType;
    private AnswerEnum formCompensation;
    private AnswerEnum eligibilityCriteria;
    private AnswerEnum demographicQuestionnaire;
    private AnswerEnum participantsInstruction;
    private AnswerEnum askedQuestions;
}
