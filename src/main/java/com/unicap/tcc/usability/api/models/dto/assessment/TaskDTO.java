package com.unicap.tcc.usability.api.models.dto.assessment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO{

    private String description;
    private String taskExecutionTime;
    private String acceptanceCriteria;
}
