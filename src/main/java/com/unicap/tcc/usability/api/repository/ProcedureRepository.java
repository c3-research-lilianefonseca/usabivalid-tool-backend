package com.unicap.tcc.usability.api.repository;

import com.unicap.tcc.usability.api.models.assessment.AssessmentProcedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ProcedureRepository extends JpaRepository<AssessmentProcedure, Long> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE public.assessment_procedure SET is_pilot_assessment = :isPilotAssessment, " +
            "questions_allowed = :questionAllowed WHERE id = :id")
    void updateIsPilotAndQuestionAllowed(@Param("isPilotAssessment") Boolean isPilotAssessment,
                                         @Param("questionAllowed") Boolean questionAllowed,
                                         @Param("id") Long id);
}
