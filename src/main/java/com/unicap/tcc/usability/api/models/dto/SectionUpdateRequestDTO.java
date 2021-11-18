package com.unicap.tcc.usability.api.models.dto;

import com.unicap.tcc.usability.api.models.enums.SectionEnum;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionUpdateRequestDTO {
    private UUID assessmentUid;
    private UUID userUid;
    private SectionEnum sectionEnum;
}
