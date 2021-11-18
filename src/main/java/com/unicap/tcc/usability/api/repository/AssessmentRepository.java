package com.unicap.tcc.usability.api.repository;

import com.unicap.tcc.usability.api.models.User;
import com.unicap.tcc.usability.api.models.assessment.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    List<Assessment> findAllBySystemUserAndRemovedDateIsNull(User user);

    Optional<Assessment> findByUid(UUID uuid);

    Optional<Assessment> findByUidAndRemovedDateIsNull(UUID uuid);

    List<Assessment> findBySystemUserUidAndRemovedDateIsNullAndSystemUserRemovedDateIsNull(UUID uid);

    @Query(nativeQuery = true, value = "SELECT a.*\n" +
            "FROM assessment a\n" +
            "         INNER JOIN assessment_user_group aug on a.id = aug.assessment_id\n" +
            "         INNER JOIN sys_user su on su.id = aug.system_user_id\n" +
            "WHERE su.uid = :uid \n" +
            "  AND a.removed_at IS NULL\n" +
            "  AND su.removed_at IS NULL;")
    List<Assessment> findByCollaboratorUid(UUID uid);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE assessment\n" +
            "SET project_name          = :projectName,\n" +
            "    project_description   = :projectDescription,\n" +
            "    smart_city_percentage = :percentage\n" +
            "WHERE uid = :uid")
    void updateProjectAndAnswers(@Param("projectName") String projectName,
                                        @Param("projectDescription") String projectDescription,
                                        @Param("percentage") Double percentage,
                                        @Param("uid") UUID uid);
}
