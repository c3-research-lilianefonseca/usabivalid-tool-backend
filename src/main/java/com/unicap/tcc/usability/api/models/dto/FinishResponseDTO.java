package com.unicap.tcc.usability.api.models.dto;


import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinishResponseDTO {
    private UUID uid;
    private String projectName;
}
