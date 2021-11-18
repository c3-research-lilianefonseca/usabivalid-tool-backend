package com.unicap.tcc.usability.api.models.dto.assessment;

import com.unicap.tcc.usability.api.models.assessment.AssessmentData;
import com.unicap.tcc.usability.api.models.assessment.answer.PlanDataAnswers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Builder
public class AssessmentDataDTO {

    private UUID assessmentUid;
    private String dataCollectionProcedure;
    private String analysisDescription;
    private Boolean statisticalMethods;
    private String statisticalMethodsDescription;
    private PlanDataAnswers planDataAnswers;

    public AssessmentData updateDataCollection(AssessmentData assessmentData) {
        if (Objects.isNull(assessmentData)){
            return AssessmentData.builder()
                    .analysisDescription(this.analysisDescription)
                    .dataCollectionProcedure(this.dataCollectionProcedure)
                    .statisticalMethods(this.statisticalMethods)
                    .statisticalMethodsDescription(this.statisticalMethodsDescription)
                    .build();
        }
        assessmentData.setAnalysisDescription(this.analysisDescription);
        assessmentData.setDataCollectionProcedure(this.dataCollectionProcedure);
        assessmentData.setStatisticalMethods(this.statisticalMethods);
        assessmentData.setStatisticalMethodsDescription(this.statisticalMethodsDescription);
        return assessmentData;
    }
}
