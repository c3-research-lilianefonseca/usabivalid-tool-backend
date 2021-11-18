package com.unicap.tcc.usability.api.models.dto.review;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeginReviewDTO {
    private UUID reviewUid;
    private UUID userUid;
}
