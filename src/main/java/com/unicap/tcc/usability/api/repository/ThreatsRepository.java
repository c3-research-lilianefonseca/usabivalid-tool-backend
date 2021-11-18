package com.unicap.tcc.usability.api.repository;

import com.unicap.tcc.usability.api.models.assessment.AssessmentThreat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ThreatsRepository extends JpaRepository<AssessmentThreat, Long> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE public.assessment_threat SET ethical_aspects_defined = :ethicalAspectsDefined WHERE id = :id")
    void updateEthicalAspectsDefined(@Param("ethicalAspectsDefined") Boolean ethicalAspectsDefined, @Param("id") Long id);
}
