package com.unicap.tcc.usability.api.models.dto;

import com.unicap.tcc.usability.api.models.enums.SectionEnum;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionControlRequestDTO {
    private UUID assessmentUid;
    private SectionEnum sectionEnum;
}
