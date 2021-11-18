package com.unicap.tcc.usability.api.models.assessment.answer;

import com.unicap.tcc.usability.api.models.assessment.answer.enums.AnswerEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanAnswers {
    private PlanApplicationAnswers planApplicationAnswers;
    private PlanGoalsAnswers planGoalsAnswers;
    private PlanVariableAnswers planVariableAnswers;
    private PlanParticipantsAnswers planParticipantsAnswers;
    private PlanTasksAnswers planTasksAnswers;
    private PlanProcedureAnswers planProcedureAnswers;
    private PlanDataAnswers planDataAnswers;
    private PlanThreatsAnswers planThreatsAnswers;

    public static PlanAnswers newPlanAnswers() {
        return PlanAnswers.builder()
                .planApplicationAnswers(PlanApplicationAnswers.builder()
                        .projectName(AnswerEnum.PENDING)
                        .projectDescription(AnswerEnum.PENDING)
                        .smartCityPercentage(AnswerEnum.PENDING)
                        .dataManagement(AnswerEnum.PENDING)
                        .applicationExecution(AnswerEnum.PENDING)
                        .sensorNetwork(AnswerEnum.PENDING)
                        .dataProcessing(AnswerEnum.PENDING)
                        .dataAccess(AnswerEnum.PENDING)
                        .serviceManagement(AnswerEnum.PENDING)
                        .tools(AnswerEnum.PENDING)
                        .definingCityModel(AnswerEnum.PENDING)
                        .build())
                .planGoalsAnswers(PlanGoalsAnswers.builder()
                        .learnability(AnswerEnum.PENDING)
                        .efficiency(AnswerEnum.PENDING)
                        .userRetentionOverTime(AnswerEnum.PENDING)
                        .errorRate(AnswerEnum.PENDING)
                        .satisfaction(AnswerEnum.PENDING)
                        .build())
                .planVariableAnswers(PlanVariableAnswers.builder()
                        .learnabilityAtt(AnswerEnum.PENDING)
                        .efficiencyAtt(AnswerEnum.PENDING)
                        .userRetentionOverTimeAtt(AnswerEnum.PENDING)
                        .errorRateAtt(AnswerEnum.PENDING)
                        .satisfactionAtt(AnswerEnum.PENDING)
                        .learnabilityMeth(AnswerEnum.PENDING)
                        .efficiencyMeth(AnswerEnum.PENDING)
                        .userRetentionOverTimeMeth(AnswerEnum.PENDING)
                        .errorRateMeth(AnswerEnum.PENDING)
                        .satisfactionMeth(AnswerEnum.PENDING)
                        .suggestedScales(AnswerEnum.PENDING)
                        .build())
                .planParticipantsAnswers(PlanParticipantsAnswers.builder()
                        .howManyParticipants(AnswerEnum.PENDING)
                        .participationType(AnswerEnum.PENDING)
                        .formCompensation(AnswerEnum.PENDING)
                        .eligibilityCriteria(AnswerEnum.PENDING)
                        .demographicQuestionnaire(AnswerEnum.PENDING)
                        .participantsInstruction(AnswerEnum.PENDING)
                        .askedQuestions(AnswerEnum.PENDING)
                        .build())
                .planTasksAnswers(PlanTasksAnswers.builder()
                        .usedTools(AnswerEnum.PENDING)
                        .tasksToPerform(AnswerEnum.PENDING)
                        .tasksTime(AnswerEnum.PENDING)
                        .criteria(AnswerEnum.PENDING)
                        .build())
                .planProcedureAnswers(PlanProcedureAnswers.builder()
                        .whenOccur(AnswerEnum.PENDING)
                        .whereOccur(AnswerEnum.PENDING)
                        .howOccur(AnswerEnum.PENDING)
                        .howMuchTime(AnswerEnum.PENDING)
                        .assessmentProcedureSteps(AnswerEnum.PENDING)
                        .isPilotAssessment(AnswerEnum.PENDING)
                        .questionsAllowed(AnswerEnum.PENDING)
                        .build())
                .planDataAnswers(PlanDataAnswers.builder()
                        .dataCollectionProcedure(AnswerEnum.PENDING)
                        .dataCollectedAnalyzed(AnswerEnum.PENDING)
                        .statisticalMethods(AnswerEnum.PENDING)
                        .build())
                .planThreatsAnswers(PlanThreatsAnswers.builder()
                        .whatThreats(AnswerEnum.PENDING)
                        .threatsValidityControlled(AnswerEnum.PENDING)
                        .assessmentLimitations(AnswerEnum.PENDING)
                        .ethicalAspects(AnswerEnum.PENDING)
                        .assessmentBiases(AnswerEnum.PENDING)
                        .build())
                .build();
    }

}
