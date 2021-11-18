package com.unicap.tcc.usability.api.models.assessment.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentQuestion {
    private String title;
    private List<Question> questions;
    private String instruction;
    private String hint;
    private String placeHolder;
    private List<PlanScale> scales;
}
