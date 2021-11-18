package com.unicap.tcc.usability.api.repository;

import com.unicap.tcc.usability.api.models.review.Review;
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
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByUid(UUID uid);

    Optional<Review> findByUidAndRemovedDateIsNull(UUID uid);

    List<Review> findAllByReviewerUidAndRemovedDateIsNull(UUID uid);


    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "SELECT r.*\n" +
            "FROM review r\n" +
            "         INNER JOIN assessment a on a.id = r.assessment_id\n" +
            "         INNER JOIN assessment_user_group aug on a.id = aug.assessment_id\n" +
            "WHERE r.state = 'AVAILABLE'\n" +
            "  and aug.system_user_id <> :userId\n" +
            "  and r.removed_at is null\n" +
            "  and a.removed_at is null\n" +
            "  and aug.removed_at is null")
    List<Review> findAllWhereUserEvaluatorIsNotReviewer(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "SELECT r.*\n" +
            "FROM review r\n" +
            "         INNER JOIN assessment a on a.id = r.assessment_id\n" +
            "         INNER JOIN assessment_user_group aug on a.id = aug.assessment_id\n" +
            "         INNER JOIN sys_user su on su.id = aug.system_user_id\n" +
            "WHERE su.uid = :userUid\n" +
            "  AND r.state = 'COMPLETED'\n" +
            "  AND r.removed_at IS NULL\n" +
            "  AND a.removed_at IS NULL\n" +
            "  AND aug.removed_at IS NULL\n" +
            "  AND su.removed_at IS NULL")
    List<Review> findAllWUserReviews(@Param("userUid") UUID userUid);
}
