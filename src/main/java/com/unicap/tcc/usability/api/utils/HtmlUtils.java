package com.unicap.tcc.usability.api.utils;

import com.google.common.io.Files;
import com.unicap.tcc.usability.api.models.User;
import com.unicap.tcc.usability.api.models.assessment.Assessment;
import com.unicap.tcc.usability.api.models.enums.SectionEnum;
import com.unicap.tcc.usability.api.models.review.Comment;
import com.unicap.tcc.usability.api.models.review.Review;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class HtmlUtils {

    public static String setHtmlMailCollaborator(Assessment assessment, String assessmentUid, User collaborator) {
        File input = new File("src/main/resources/templates/CollaboratorMail.html");
        try {
            var mailTemplate = Files.asCharSource(input, StandardCharsets.UTF_8).read();
            return mailTemplate.replace(":collaboratorName", collaborator.getName())
                    .replace(":authorName", assessment.getSystemUser().getName())
                    .replace(":assessmentUid", assessmentUid);
        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return "";
    }

    public static String setHtmlMailNewCollaborator(Assessment assessment, String assessmentUid) {
        File input = new File("src/main/resources/templates/NewCollaboratorMail.html");
        try {
            var mailTemplate = Files.asCharSource(input, StandardCharsets.UTF_8).read();
            return mailTemplate.replace(":authorName", assessment.getSystemUser().getName())
                    .replace(":assessmentUid", assessmentUid);
        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return "";
    }

    public static String setHtmlMailNewReview(Assessment assessment, Review review) {
        File input = new File("src/main/resources/templates/NewReview.html");
        try {
            var mailTemplate = Files.asCharSource(input, StandardCharsets.UTF_8).read();
            return mailTemplate.replace(":date", review.getLimitReviewDate().toString())
                    .replace(":projectName", assessment.getProjectName());
        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return "";
    }

    public static String setHtmlMailFinishedReview(Review review) {
        File input = new File("src/main/resources/templates/FinishedReview.html");
        try {
            var mailTemplate = Files.asCharSource(input, StandardCharsets.UTF_8).read();
            for (Comment comment : review.getComments()) {
                if (comment.getSection().equals(SectionEnum.AP))
                    mailTemplate = mailTemplate.replace(":applicationComent", StringUtils.isEmpty(comment.getComment()) ?
                            "N/A" : comment.getComment());
                if (comment.getSection().equals(SectionEnum.GO))
                    mailTemplate = mailTemplate.replace(":goalsComent", StringUtils.isEmpty(comment.getComment()) ?
                            "N/A" : comment.getComment());
                if (comment.getSection().equals(SectionEnum.VM))
                    mailTemplate = mailTemplate.replace(":variableComent", StringUtils.isEmpty(comment.getComment()) ?
                            "N/A" : comment.getComment());
                if (comment.getSection().equals(SectionEnum.PA))
                    mailTemplate = mailTemplate.replace(":participantComent", StringUtils.isEmpty(comment.getComment()) ?
                            "N/A" : comment.getComment());
                if (comment.getSection().equals(SectionEnum.TM))
                    mailTemplate = mailTemplate.replace(":taskComent", StringUtils.isEmpty(comment.getComment()) ?
                            "N/A" : comment.getComment());
                if (comment.getSection().equals(SectionEnum.PR))
                    mailTemplate = mailTemplate.replace(":procedureComent", StringUtils.isEmpty(comment.getComment()) ?
                            "N/A" : comment.getComment());
                if (comment.getSection().equals(SectionEnum.DT))
                    mailTemplate = mailTemplate.replace(":dataComent", StringUtils.isEmpty(comment.getComment()) ?
                            "N/A" : comment.getComment());
                if (comment.getSection().equals(SectionEnum.TH))
                    mailTemplate = mailTemplate.replace(":threatComent", StringUtils.isEmpty(comment.getComment()) ?
                            "N/A" : comment.getComment());
            }
            return mailTemplate.replace(":projectName", review.getAssessment().getProjectName());
        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return "";
    }

    public static String setHtmlSendPlan(Assessment assessment) {
        File input = new File("src/main/resources/templates/SendPlansToEmail.html");
        try {
            var mailTemplate = Files.asCharSource(input, StandardCharsets.UTF_8).read();
            return mailTemplate.replace(":plan", assessment.getProjectName());
        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return "";
    }
}