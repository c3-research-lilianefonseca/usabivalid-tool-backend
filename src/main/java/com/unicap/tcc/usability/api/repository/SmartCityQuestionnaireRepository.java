package com.unicap.tcc.usability.api.repository;

import com.unicap.tcc.usability.api.models.SmartCityQuestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface SmartCityQuestionnaireRepository extends JpaRepository<SmartCityQuestionnaire, Long> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE sc_questionnaire\n" +
            "SET define_city_model      = :defineCity,\n" +
            "    has_app_execution      = :hasAppExection,\n" +
            "    has_data_access        = :hasDataAccess,\n" +
            "    has_data_management    = :hasDataManagement,\n" +
            "    has_data_processing    = :hasDataProcess,\n" +
            "    has_sensor_network     = :hasSensonNetwork,\n" +
            "    has_service_management = :hasServiceManegement,\n" +
            "    has_software_tools     = :hasSOftwareTools\n" +
            "WHERE assessment_id = :id")
    void updateSmartCityQuestionnaire(
            @Param("defineCity") Boolean defineCity,
            @Param("hasAppExection") Boolean hasAppExection,
            @Param("hasDataAccess") Boolean hasDataAccess,
            @Param("hasDataManagement") Boolean hasDataManagement,
            @Param("hasDataProcess") Boolean hasDataProcess,
            @Param("hasSensonNetwork") Boolean hasSensonNetwork,
            @Param("hasServiceManegement") Boolean hasServiceManegement,
            @Param("hasSOftwareTools") Boolean hasSOftwareTools,
            @Param("id") Long id);
}
