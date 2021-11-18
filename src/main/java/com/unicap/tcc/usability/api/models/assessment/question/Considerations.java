package com.unicap.tcc.usability.api.models.assessment.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Considerations {
    private String state;
    private List<String> items;
}
