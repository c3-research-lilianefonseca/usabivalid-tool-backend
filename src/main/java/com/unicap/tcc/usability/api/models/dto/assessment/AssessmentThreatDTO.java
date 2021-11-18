package com.unicap.tcc.usability.api.models.dto.assessment;

import com.unicap.tcc.usability.api.models.assessment.AssessmentThreat;
import com.unicap.tcc.usability.api.models.assessment.answer.PlanThreatsAnswers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentThreatDTO {

    private UUID assessmentUid;
    private List<String> threats;
    private String controlMeasure;
    private List<String> limitations;
    private Boolean ethicalAspectsDefined;
    private String ethicalAspectsDescription;
    private String biasDescription;
    private PlanThreatsAnswers planThreatsAnswers;

    public AssessmentThreat updateThreats(AssessmentThreat assessmentThreat) {
        if (Objects.isNull(assessmentThreat)){
            return AssessmentThreat.builder()
                    .biasDescription(this.biasDescription)
                    .controlMeasure(this.controlMeasure)
                    .ethicalAspectsDefined(this.ethicalAspectsDefined)
                    .ethicalAspectsDescription(this.ethicalAspectsDescription)
                    .limitations(this.limitations)
                    .threats(this.threats)
                    .build();
        }
        assessmentThreat.setBiasDescription(this.biasDescription);
        assessmentThreat.setControlMeasure(this.controlMeasure);
        assessmentThreat.setEthicalAspectsDefined(this.ethicalAspectsDefined);
        assessmentThreat.setEthicalAspectsDescription(this.ethicalAspectsDescription);
        assessmentThreat.setLimitations(this.limitations);
        assessmentThreat.setThreats(this.threats);
        return assessmentThreat;
    }
}
