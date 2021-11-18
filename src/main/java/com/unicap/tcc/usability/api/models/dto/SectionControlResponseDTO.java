package com.unicap.tcc.usability.api.models.dto;

import com.unicap.tcc.usability.api.models.enums.SectionControlEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionControlResponseDTO {
    private SectionControlEnum sectionControlEnum;
    private String userName;
}
