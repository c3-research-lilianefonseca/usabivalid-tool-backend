package com.unicap.tcc.usability.api.models.dto.review;

import com.unicap.tcc.usability.api.models.enums.EReviewState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewListResponseDTO {
    private String projectName;
    private LocalDate limitReviewDate;
    private EReviewState reviewStatus;
    private UUID reviewUid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewListResponseDTO that = (ReviewListResponseDTO) o;
        return Objects.equals(projectName, that.projectName) &&
                Objects.equals(limitReviewDate, that.limitReviewDate) &&
                reviewStatus == that.reviewStatus &&
                Objects.equals(reviewUid, that.reviewUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectName, limitReviewDate, reviewStatus, reviewUid);
    }
}
