package com.unicap.tcc.usability.api.models.dto.assessment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollaboratorDTO {

    private UUID assessmentUid;
    private UUID userUid;
    private List<String> collaboratorsEmail;


}


