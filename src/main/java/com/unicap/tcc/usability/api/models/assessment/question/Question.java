package com.unicap.tcc.usability.api.models.assessment.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private String objectKey;
    private String title;
    private String subTitle;
    private String placeHolder;
    private String hint;
}
