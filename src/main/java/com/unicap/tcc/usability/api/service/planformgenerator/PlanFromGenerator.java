package com.unicap.tcc.usability.api.service.planformgenerator;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface PlanFromGenerator {

    public InputStream findQuestions() throws FileNotFoundException;
}
