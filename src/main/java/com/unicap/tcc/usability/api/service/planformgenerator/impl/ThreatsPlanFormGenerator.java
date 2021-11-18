package com.unicap.tcc.usability.api.service.planformgenerator.impl;

import com.unicap.tcc.usability.api.service.planformgenerator.PlanFromGenerator;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Service
public class ThreatsPlanFormGenerator implements PlanFromGenerator {
    @Override
    public InputStream findQuestions() throws FileNotFoundException {
        File file = new File("src/main/resources/json-resources/threats_questions.json");
        return new FileInputStream(file);
    }
}
