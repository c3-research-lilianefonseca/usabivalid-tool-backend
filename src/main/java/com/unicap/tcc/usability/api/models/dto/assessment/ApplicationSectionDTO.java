package com.unicap.tcc.usability.api.models.dto.assessment;

import com.unicap.tcc.usability.api.models.SmartCityQuestionnaire;
import com.unicap.tcc.usability.api.models.assessment.Assessment;
import com.unicap.tcc.usability.api.models.assessment.answer.PlanApplicationAnswers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationSectionDTO {

    private UUID assessmentUid;
    private String projectName;
    private String projectDescription;
    private Boolean hasDataManagement;
    private Boolean hasAppExecution;
    private Boolean hasSensorNetwork;
    private Boolean hasDataProcessing;
    private Boolean hasDataAccess;
    private Boolean hasServiceManagement;
    private Boolean hasSoftwareTools;
    private Boolean defineCityModel;
    private PlanApplicationAnswers planApplicationAnswers;

    public SmartCityQuestionnaire toSmartCityQuestionnaire() {
        return SmartCityQuestionnaire.builder()
                .hasDataManagement(this.hasDataManagement)
                .hasAppExecution(this.hasAppExecution)
                .hasSensorNetwork(this.hasSensorNetwork)
                .hasDataProcessing(this.hasDataProcessing)
                .hasDataAccess(this.hasDataAccess)
                .hasServiceManagement(this.hasServiceManagement)
                .hasSoftwareTools(this.hasSoftwareTools)
                .defineCityModel(this.defineCityModel)
                .build();
    }

    public SmartCityQuestionnaire updateSmartCityQuestionnaire(Assessment assessment) {
        return SmartCityQuestionnaire.builder()
                .hasDataManagement(this.hasDataManagement)
                .hasAppExecution(this.hasAppExecution)
                .hasSensorNetwork(this.hasSensorNetwork)
                .hasDataProcessing(this.hasDataProcessing)
                .hasDataAccess(this.hasDataAccess)
                .hasServiceManagement(this.hasServiceManagement)
                .hasSoftwareTools(this.hasSoftwareTools)
                .defineCityModel(this.defineCityModel)
                .assessment(assessment)
                .build();

    }

}


