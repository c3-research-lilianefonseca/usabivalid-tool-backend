package com.unicap.tcc.usability.api.models.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewedPlanDTO {
    private UUID reviewUid;
    private String projectName;
    private LocalDate limitReviewDate;
    private LocalDate reviewedDate;
    private String reviewer;
}
