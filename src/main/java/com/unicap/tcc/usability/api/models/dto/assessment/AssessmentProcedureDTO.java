package com.unicap.tcc.usability.api.models.dto.assessment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.unicap.tcc.usability.api.models.assessment.AssessmentProcedure;
import com.unicap.tcc.usability.api.models.assessment.AssessmentProcedureStep;
import com.unicap.tcc.usability.api.models.assessment.answer.PlanProcedureAnswers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentProcedureDTO {

    private UUID assessmentUid;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate occurDate;
    private String occurLocal;
    private String occurDetail;
    private String occurTime;
    private List<AssessmentProcedureStepDTO> assessmentProcedureStepDTOS;
    private Boolean isPilotAssessment;
    private String pilotDescription;
    private Boolean questionsAllowed;
    private PlanProcedureAnswers planProcedureAnswers;

    public AssessmentProcedure updateProcedure(AssessmentProcedure assessmentProcedure) {
        if (Objects.isNull(assessmentProcedure)) {
            return AssessmentProcedure.builder()
                    .occurDate(this.occurDate)
                    .occurLocal(this.occurLocal)
                    .occurDetail(this.occurDetail)
                    .occurTime(this.occurTime)
                    .assessmentProcedureSteps(this.getProcedureStepList())
                    .isPilotAssessment(this.isPilotAssessment)
                    .pilotDescription(this.pilotDescription)
                    .questionsAllowed(this.questionsAllowed)
                    .build();
        }
        this.updateTasks(assessmentProcedure.getAssessmentProcedureSteps());
        assessmentProcedure.setOccurDate(this.occurDate);
        assessmentProcedure.setOccurLocal(this.occurLocal);
        assessmentProcedure.setOccurDetail(this.occurDetail);
        assessmentProcedure.setOccurTime(this.occurTime);
        assessmentProcedure.setIsPilotAssessment(this.isPilotAssessment);
        assessmentProcedure.setPilotDescription(this.pilotDescription);
        assessmentProcedure.setQuestionsAllowed(this.questionsAllowed);
        return assessmentProcedure;
    }

    private Set<AssessmentProcedureStep> getProcedureStepList() {
        return this.assessmentProcedureStepDTOS.stream().map(assessmentProcedureStepDTO ->
                AssessmentProcedureStep.builder()
                        .name(assessmentProcedureStepDTO.getName())
                        .description(assessmentProcedureStepDTO.getDescription())
                        .build())
                .collect(Collectors.toSet());
    }

    private void updateTasks(Set<AssessmentProcedureStep> assessmentProcedureSteps) {
        assessmentProcedureSteps.forEach(assessmentProcedureStep -> {
            if (!this.getProcedureStepList().contains(assessmentProcedureStep)) {
                assessmentProcedureStep.setRemovedDate(LocalDateTime.now());
            }
        });
        Set<AssessmentProcedureStep> newProcedureSteps = new HashSet<>();
        this.getProcedureStepList().forEach(task -> {
            if (!assessmentProcedureSteps.contains(task))
                newProcedureSteps.add(task);
        });
        assessmentProcedureSteps.addAll(newProcedureSteps);
    }
}
