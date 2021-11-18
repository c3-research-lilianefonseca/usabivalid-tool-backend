package com.unicap.tcc.usability.api.models.dto.review;

import com.unicap.tcc.usability.api.models.review.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinishReviewDTO {
    private UUID reviewUid;
    private Set<Comment> comments;
}
