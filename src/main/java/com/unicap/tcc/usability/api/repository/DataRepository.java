package com.unicap.tcc.usability.api.repository;

import com.unicap.tcc.usability.api.models.assessment.AssessmentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface DataRepository extends JpaRepository<AssessmentData, Long> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE public.assessment_data SET statistical_methods = :statisticalMethod WHERE id = :id")
    void updateStatisticalMethodFlag(@Param("statisticalMethod") Boolean statisticalMethod, @Param("id") Long id);
}
