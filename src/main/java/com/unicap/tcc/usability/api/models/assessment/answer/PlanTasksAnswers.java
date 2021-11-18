package com.unicap.tcc.usability.api.models.assessment.answer;


import com.unicap.tcc.usability.api.models.assessment.answer.enums.AnswerEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanTasksAnswers {
    private AnswerEnum usedTools;
    private AnswerEnum tasksToPerform;
    private AnswerEnum tasksTime;
    private AnswerEnum criteria;
}
