package com.unicap.tcc.usability.api.repository;

import com.unicap.tcc.usability.api.models.assessment.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE public.participants SET has_collected_information = :hasCollectedInformation WHERE id = :id")
    void updateHasCollectedInformation(@Param("hasCollectedInformation") Boolean hasCollectedInformation, @Param("id") Long id);
}
