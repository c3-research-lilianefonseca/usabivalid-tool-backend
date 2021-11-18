package com.unicap.tcc.usability.api.models.assessment.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanScaleQuestion {
    private String key;
    private String question;
    private String lowerScoreLabel;
    private String higherScoreLabel;
}
