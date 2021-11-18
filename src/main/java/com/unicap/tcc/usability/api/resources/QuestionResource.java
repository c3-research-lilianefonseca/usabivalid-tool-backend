package com.unicap.tcc.usability.api.resources;

import com.unicap.tcc.usability.api.models.assessment.question.PlanForm;
import com.unicap.tcc.usability.api.models.enums.SectionEnum;
import com.unicap.tcc.usability.api.service.QuestionService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/question")
@Api("Question API")
@RequiredArgsConstructor
public class QuestionResource {

    private final QuestionService questionService;

    @GetMapping(path = "/by-category/{category}")
    public ResponseEntity<PlanForm> getScaleByUid(@PathVariable("category") String category) {
        var planForm = questionService.findQuestionsFormByCategory(SectionEnum.convert(category));
        if (ObjectUtils.isEmpty(planForm)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok()
                .body(planForm);
    }
}
