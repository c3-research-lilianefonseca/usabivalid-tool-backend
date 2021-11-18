package com.unicap.tcc.usability.api.repository;

import com.unicap.tcc.usability.api.models.User;
import com.unicap.tcc.usability.api.models.assessment.Assessment;
import com.unicap.tcc.usability.api.models.assessment.AssessmentUserGroup;
import com.unicap.tcc.usability.api.models.enums.UserProfileEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssessmentUserGroupRepository extends JpaRepository<AssessmentUserGroup, Long> {

    @Query(nativeQuery = true, value = "SELECT text(uid) as uid FROM assessment WHERE id = :id")
    Optional<String> findUidById(Long id);

    Optional<AssessmentUserGroup> findBySystemUserAndAssessmentAndRemovedDateIsNull(User user, Assessment assessment);

    List<AssessmentUserGroup> findAllBySystemUserUidAndAssessmentRemovedDateIsNullAndRemovedDateIsNull(UUID uid);

    List<AssessmentUserGroup> findAllByAssessmentAndProfile(Assessment assessment, UserProfileEnum profile);

    Optional<AssessmentUserGroup> findByAssessmentUidAndRemovedDateIsNull(UUID uid);

    Optional<AssessmentUserGroup> findByAssessmentUidAndAssessmentRemovedDateIsNullAndRemovedDateIsNull(UUID uid);

    Optional<AssessmentUserGroup> findBySystemUserUidAndRemovedDateIsNull(UUID uid);

    List<AssessmentUserGroup> findAllByAssessmentAndAssessmentRemovedDateIsNull(Assessment assessment);

}
