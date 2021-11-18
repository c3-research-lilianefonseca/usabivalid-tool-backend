package com.unicap.tcc.usability.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unicap.tcc.usability.api.models.assessment.Assessment;
import com.unicap.tcc.usability.api.models.dto.assessment.ApplicationSectionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sc_questionnaire")
public class SmartCityQuestionnaire extends BaseEntity {

    @JsonIgnore
    @Id
    @Column(name = "assessment_id")
    private Long id;
    @JsonBackReference
    @OneToOne
    @MapsId
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;
    @Column(name = "has_data_management")
    @Generated(GenerationTime.ALWAYS)
    private Boolean hasDataManagement;
    @Column(name = "has_app_execution")
    @Generated(GenerationTime.ALWAYS)
    private Boolean hasAppExecution;
    @Column(name = "has_sensor_network")
    @Generated(GenerationTime.ALWAYS)
    private Boolean hasSensorNetwork;
    @Column(name = "has_data_processing")
    @Generated(GenerationTime.ALWAYS)
    private Boolean hasDataProcessing;
    @Column(name = "has_data_access")
    @Generated(GenerationTime.ALWAYS)
    private Boolean hasDataAccess;
    @Column(name = "has_service_management")
    @Generated(GenerationTime.ALWAYS)
    private Boolean hasServiceManagement;
    @Column(name = "has_software_tools")
    @Generated(GenerationTime.ALWAYS)
    private Boolean hasSoftwareTools;
    @Column(name = "define_city_model")
    @Generated(GenerationTime.ALWAYS)
    private Boolean defineCityModel;

    @JsonIgnore
    public List<Boolean> getListOfResults() {
        return new ArrayList<>(Arrays.asList(hasDataManagement, hasAppExecution, hasSensorNetwork,
                hasDataProcessing, hasDataAccess, hasServiceManagement, hasSoftwareTools, defineCityModel));
    }

    public void updateValues(ApplicationSectionDTO questionnaireDTO) {
        this.hasAppExecution = questionnaireDTO.getHasAppExecution();
        this.hasSensorNetwork = questionnaireDTO.getHasSensorNetwork();
        this.hasDataProcessing = questionnaireDTO.getHasDataProcessing();
        this.hasDataAccess = questionnaireDTO.getHasDataAccess();
        this.hasServiceManagement = questionnaireDTO.getHasServiceManagement();
        this.hasDataManagement = questionnaireDTO.getHasDataManagement();
        this.hasSoftwareTools = questionnaireDTO.getHasSoftwareTools();
        this.defineCityModel = questionnaireDTO.getDefineCityModel();
    }
}


