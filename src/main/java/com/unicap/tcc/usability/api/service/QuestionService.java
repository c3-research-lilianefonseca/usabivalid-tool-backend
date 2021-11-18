package com.unicap.tcc.usability.api.service;


import com.google.gson.Gson;
import com.unicap.tcc.usability.api.models.assessment.question.PlanForm;
import com.unicap.tcc.usability.api.models.enums.SectionEnum;
import com.unicap.tcc.usability.api.service.planformgenerator.factory.PlanFormGeneratorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class QuestionService {

    private final PlanFormGeneratorFactory planFormGeneratorFactory;

    public PlanForm findQuestionsFormByCategory(SectionEnum category) {
        PlanForm planForm = new PlanForm();
        try {
            var planFormGenerator = planFormGeneratorFactory.getPlanFormGenerator(category);
            InputStream resourceAsStream1 = planFormGenerator.findQuestions();
            StringWriter stringWriter = new StringWriter();
            IOUtils.copy(resourceAsStream1, stringWriter, Charset.defaultCharset());
            Gson gson = new Gson();
            planForm = gson.fromJson(stringWriter.toString(), PlanForm.class);
        } catch (Exception e) {
            log.error("Unable to build plan form object", e);
        }

        return planForm;
    }
}
