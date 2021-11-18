package com.unicap.tcc.usability.api.repository;

import com.unicap.tcc.usability.api.models.User;
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
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginAndRemovedDateIsNull(String login);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndRemovedDateIsNull(String email);

    Optional<User> findByUid(UUID uuid);

    Optional<User> findByUidAndRemovedDateIsNull(UUID uuid);

    User findByEmailIgnoreCase(String email);

    List<User> findAllByIsReviewerTrueAndRemovedDateIsNullAndUidNotIn(List<UUID> uid);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "SELECT su.*\n" +
            "FROM review r\n" +
            "         INNER JOIN assessment a on a.id = r.assessment_id\n" +
            "         INNER JOIN assessment_user_group aug on a.id = aug.assessment_id\n" +
            "         INNER JOIN sys_user su on su.id = aug.system_user_id\n" +
            "WHERE r.id = :reviewId")
    List<User> findReviewUsers(@Param("reviewId") Long reviewId);
}
