package com.unicap.tcc.usability.api.models.assessment;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SectionsControlGroup {
    private Set<SectionsControl> sectionsControls;

}
