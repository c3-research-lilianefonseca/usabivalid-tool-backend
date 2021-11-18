package com.unicap.tcc.usability.api.models.assessment.question;

import com.unicap.tcc.usability.api.models.enums.ScalesEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanScale {
    private ScalesEnum acronym;
    private String name;
    private String description;
    private List<String> measures;
    private List<PlanScaleQuestion> scaleQuestions;
}
