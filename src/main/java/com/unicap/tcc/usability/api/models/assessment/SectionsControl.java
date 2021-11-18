package com.unicap.tcc.usability.api.models.assessment;

import com.unicap.tcc.usability.api.models.enums.SectionControlEnum;
import com.unicap.tcc.usability.api.models.enums.SectionEnum;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SectionsControl {
    private UUID beingEditedBy;
    private SectionEnum section;
    private SectionControlEnum sectionControlEnum;
}
