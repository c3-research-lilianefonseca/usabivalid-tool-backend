package com.unicap.tcc.usability.api.models.assessment.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanForm {
    private String section;
    private String key;
    private List<ParentQuestion> parentQuestions;
}
