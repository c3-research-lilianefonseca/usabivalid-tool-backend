package com.unicap.tcc.usability.api.models.dto.assessment;

import com.unicap.tcc.usability.api.models.enums.ScalesEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScaleDTO {

    private ScalesEnum acronym;
    private String name;
    private String description;
    private List<String> measures;
    private List<ScaleQuestionDTO> scaleQuestions;
}


