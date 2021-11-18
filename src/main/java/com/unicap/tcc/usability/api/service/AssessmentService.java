package com.unicap.tcc.usability.api.service;

import com.google.common.io.Files;
import com.unicap.tcc.usability.api.exception.ApiException;
import com.unicap.tcc.usability.api.models.Scale;
import com.unicap.tcc.usability.api.models.SmartCityQuestionnaire;
import com.unicap.tcc.usability.api.models.User;
import com.unicap.tcc.usability.api.models.assessment.Assessment;
import com.unicap.tcc.usability.api.models.assessment.AssessmentUserGroup;
import com.unicap.tcc.usability.api.models.assessment.SectionsControl;
import com.unicap.tcc.usability.api.models.assessment.SectionsControlGroup;
import com.unicap.tcc.usability.api.models.assessment.answer.PlanAnswers;
import com.unicap.tcc.usability.api.models.dto.*;
import com.unicap.tcc.usability.api.models.dto.assessment.*;
import com.unicap.tcc.usability.api.models.enums.AssessmentState;
import com.unicap.tcc.usability.api.models.enums.SectionControlEnum;
import com.unicap.tcc.usability.api.models.enums.SectionEnum;
import com.unicap.tcc.usability.api.models.enums.UserProfileEnum;
import com.unicap.tcc.usability.api.repository.*;
import com.unicap.tcc.usability.api.utils.PdfGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final UserRepository userRepository;
    private final AssessmentUserGroupRepository assessmentUserGroupRepository;
    private final SmartCityQuestionnaireRepository smartCityQuestionnaireRepository;
    private final ParticipantRepository participantRepository;
    private final ScaleRepository scaleRepository;
    private final ProcedureRepository procedureRepository;
    private final DataRepository dataRepository;
    private final ThreatsRepository threatsRepository;
    private final MailSender mailSender;

    public SmartCityResponse calculateSmartCityPercentage(SmartCityQuestionnaire smartCityQuestionnaire) {
        var resultList = smartCityQuestionnaire.getListOfResults();
        var resultsQuantity = Long.valueOf(resultList.size());
        var positiveResults = Long.valueOf(resultList.stream().filter(aBoolean -> aBoolean.equals(true)).count());
        return SmartCityResponse.builder()
                .smartCityPercentage(positiveResults.doubleValue() == 0 ?
                        0 : (positiveResults.doubleValue() * 100) / resultsQuantity.doubleValue())
                .build();
    }

    public Assessment getAssessmentByUuid(UUID uid) {
        var assessmentOptional = assessmentRepository.findByUid(uid);
        return assessmentOptional.orElse(null);
    }

    public Response getReportStreamingOutput(String id) {
        Assessment assessment = getAssessmentByUuid(UUID.fromString(id));
        if (Objects.isNull(assessment))
            throw new ApiException(Response.Status.NOT_FOUND,
                    "Assessment not found");

        var userGroups =
                assessmentUserGroupRepository.findAllByAssessmentAndProfile(assessment, UserProfileEnum.COLLABORATOR);
        var collaborators = userGroups.stream().map(AssessmentUserGroup::getSystemUser).collect(Collectors.toList());
        ByteArrayOutputStream byteArrayOutputStream = PdfGenerator.generatePlan(assessment, collaborators);

        StreamingOutput output = out -> {
            byteArrayOutputStream.writeTo(out);
            out.flush();
            byteArrayOutputStream.close();
        };
        return Response.ok(output, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=" + assessment.getProjectName() + ".pdf").build();
    }

    public Assessment createAssessment(AssessmentCreationDTO assessmentCreationDTO) {
        var user = userRepository.findByUid(assessmentCreationDTO.getUserUid());
        if (user.isEmpty()) {
            return null;
        }
        Assessment assessment = assessmentRepository.save(Assessment.builder()
                .systemUser(user.get())
                .projectName(assessmentCreationDTO.getProjectName())
                .projectDescription(assessmentCreationDTO.getProjectDescription())
                .state(AssessmentState.CREATED)
                .answers(PlanAnswers.newPlanAnswers())
                .uid(UUID.randomUUID())
                .sectionsControlGroup(SectionsControlGroup.builder()
                        .sectionsControls(initializeSections())
                        .build())
                .build());
        assessmentUserGroupRepository.save(AssessmentUserGroup.builder()
                .assessment(assessment)
                .profile(UserProfileEnum.AUTHOR)
                .systemUser(assessment.getSystemUser())
                .build());
        var optionalUid = assessmentUserGroupRepository.findUidById(assessment.getId());
        optionalUid.ifPresent(s ->
                mailSender.sendCollaboratorEmail(assessment, s, assessmentCreationDTO.getCollaboratorsEmail()));
        return assessment;
    }

    private Set<SectionsControl> initializeSections() {
        return SectionEnum.getSectionList()
                .stream()
                .map(sectionEnum ->
                        SectionsControl.builder()
                                .beingEditedBy(null)
                                .section(sectionEnum)
                                .sectionControlEnum(SectionControlEnum.AVAILABLE)
                                .build())
                .collect(Collectors.toSet());
    }

    public Assessment addCollaborator(CollaboratorDTO collaboratorDTO) {
        var assessment = assessmentRepository.findByUidAndRemovedDateIsNull(collaboratorDTO.getAssessmentUid());
        if (assessment.isEmpty()) {
            return null;
        }
        var optionalUid = assessmentUserGroupRepository.findUidById(assessment.get().getId());
        optionalUid.ifPresent(s ->
                mailSender.sendCollaboratorEmail(assessment.get(), s, collaboratorDTO.getCollaboratorsEmail()));
        return assessment.get();
    }

    public AssessmentUserGroup enterAsCollaborator(CollaboratorDTO collaboratorDTO) {
        var assessment = assessmentRepository.findByUidAndRemovedDateIsNull(collaboratorDTO.getAssessmentUid());
        if (assessment.isPresent()) {
            var optionalUser = userRepository.findByUidAndRemovedDateIsNull(collaboratorDTO.getUserUid());
            if (optionalUser.isPresent()) {
                var assessmentGroup = assessmentUserGroupRepository
                        .findBySystemUserAndAssessmentAndRemovedDateIsNull(optionalUser.get(), assessment.get());
                if (assessmentGroup.isPresent()) {
                    return null;
                }
                return assessmentUserGroupRepository.save(AssessmentUserGroup.builder()
                        .assessment(assessment.get())
                        .systemUser(optionalUser.get())
                        .profile(UserProfileEnum.COLLABORATOR)
                        .build());
            }
        }
        return null;
    }

    public List<AssessmentListDTO> findUserAssessmentList(UUID uid) {
        var userGroupList =
                assessmentUserGroupRepository.findAllBySystemUserUidAndAssessmentRemovedDateIsNullAndRemovedDateIsNull(uid);
        if (CollectionUtils.isNotEmpty(userGroupList)) {
            return userGroupList.stream().map(assessmentUserGroup ->
                    AssessmentListDTO.builder()
                            .assessmentUid(assessmentUserGroup.getAssessment().getUid().toString())
                            .authorName(assessmentUserGroup.getAssessment().getSystemUser().getName())
                            .projectName(assessmentUserGroup.getAssessment().getProjectName())
                            .state(assessmentUserGroup.getAssessment().getState().getValue())
                            .profile(assessmentUserGroup.getProfile())
                            .build()).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public Optional<Assessment> findAssessmentPlanByUid(UUID uid) {
        var userGroup =
                assessmentUserGroupRepository.findByAssessmentUidAndAssessmentRemovedDateIsNullAndRemovedDateIsNull(uid);
        if (userGroup.isPresent()) {
            userGroup.get().getAssessment().setUserProfile(userGroup.get().getProfile());
            return Optional.of(userGroup.get().getAssessment());
        }
        return Optional.empty();
    }

    public List<Scale> getScaleList() {
        return scaleRepository.findAll();
    }

    public Optional<Assessment> deleteAssessmentPlan(UUID uid) {
        var assessmentOptional = assessmentRepository.findByUid(uid);
        if (assessmentOptional.isPresent()) {
            assessmentOptional.get().setState(AssessmentState.CANCELED);
            assessmentOptional.get().setRemovedDate(LocalDateTime.now());
            return Optional.of(assessmentRepository.save(assessmentOptional.get()));
        }
        return Optional.empty();
    }

    public Assessment addSmartCityQuestionnaire(ApplicationSectionDTO applicationSection) {
        var optionalAssessment = assessmentRepository.findByUidAndRemovedDateIsNull(applicationSection.getAssessmentUid());
        if (optionalAssessment.isPresent()) {
            var assessment = optionalAssessment.get();
            if (Objects.isNull(assessment.getSmartCityQuestionnaire()))
                assessment.setSmartCityQuestionnaire(applicationSection.updateSmartCityQuestionnaire(assessment));
            else
                assessment.getSmartCityQuestionnaire().updateValues(applicationSection);
            assessment.getAnswers().setPlanApplicationAnswers(applicationSection.getPlanApplicationAnswers());
            assessment.setProjectName(applicationSection.getProjectName());
            assessment.setProjectDescription(applicationSection.getProjectDescription());
            var resultList = applicationSection.toSmartCityQuestionnaire().getListOfResults();
            var resultsQuantity = Long.valueOf(resultList.size());
            var positiveResults = Long.valueOf(resultList.stream().filter(aBoolean -> aBoolean.equals(true)).count());
            var percentage = positiveResults.doubleValue() == 0 ?
                    0 : (positiveResults.doubleValue() * 100) / resultsQuantity.doubleValue();
            assessment.setSmartCityPercentage(percentage);
            if (!assessment.getState().equals(AssessmentState.COLLECTING_DATA))
                assessment.setState(AssessmentState.COLLECTING_DATA);
            assessment = assessmentRepository.save(assessment);

            assessmentRepository.updateProjectAndAnswers(applicationSection.getProjectName(),
                    applicationSection.getProjectDescription(),
                    percentage,
                    assessment.getUid());
            smartCityQuestionnaireRepository.updateSmartCityQuestionnaire(
                    applicationSection.getDefineCityModel(),
                    applicationSection.getHasAppExecution(),
                    applicationSection.getHasDataAccess(),
                    applicationSection.getHasDataManagement(),
                    applicationSection.getHasDataProcessing(),
                    applicationSection.getHasSensorNetwork(),
                    applicationSection.getHasServiceManagement(),
                    applicationSection.getHasSoftwareTools(),
                    assessment.getId());
            return assessment;
        }
        return null;
    }

    public Assessment addUsabilityGoals(UsabilityGoalDTO usabilityGoalDTO) {
        var optionalAssessment = assessmentRepository.findByUidAndRemovedDateIsNull(usabilityGoalDTO.getAssessmentUid());
        if (optionalAssessment.isPresent()) {
            optionalAssessment.get().setUsabilityGoals(usabilityGoalDTO.toUsabilityGoals(
                    optionalAssessment.get().getUsabilityGoals()));
            optionalAssessment.get().getAnswers().setPlanGoalsAnswers(usabilityGoalDTO.getPlanGoalsAnswers());
            if (!optionalAssessment.get().getState().equals(AssessmentState.COLLECTING_DATA))
                optionalAssessment.get().setState(AssessmentState.COLLECTING_DATA);
            return assessmentRepository.save(optionalAssessment.get());
        }
        return null;
    }

    public Assessment addVariables(AssessmentVariablesDTO assessmentVariablesDTO) {
        var optionalAssessment = assessmentRepository.findByUidAndRemovedDateIsNull(assessmentVariablesDTO.getAssessmentUid());
        if (optionalAssessment.isPresent()) {
            optionalAssessment.get().setAttributes(assessmentVariablesDTO.updateVariableSet(optionalAssessment.get().getAttributes()));
            optionalAssessment.get().getAnswers().setPlanVariableAnswers(assessmentVariablesDTO.getPlanVariableAnswers());
            var scaleList = scaleRepository.findByAcronymIn(assessmentVariablesDTO.getScale());
            if (CollectionUtils.isNotEmpty(scaleList))
                optionalAssessment.get().setScale(scaleList);
            if (!optionalAssessment.get().getState().equals(AssessmentState.COLLECTING_DATA))
                optionalAssessment.get().setState(AssessmentState.COLLECTING_DATA);
            return assessmentRepository.save(optionalAssessment.get());
        }
        return null;
    }

    public Assessment addParticipant(ParticipantDTO participantDTO) {
        var optionalAssessment = assessmentRepository.findByUidAndRemovedDateIsNull(participantDTO.getAssessmentUid());
        if (optionalAssessment.isPresent()) {
            optionalAssessment.get().setParticipant(participantDTO.updateParticipant(optionalAssessment.get().getParticipant()));
            optionalAssessment.get().getAnswers().setPlanParticipantsAnswers(participantDTO.getPlanParticipantsAnswers());
            if (!optionalAssessment.get().getState().equals(AssessmentState.COLLECTING_DATA))
                optionalAssessment.get().setState(AssessmentState.COLLECTING_DATA);
            var assessment = assessmentRepository.save(optionalAssessment.get());
            participantRepository.updateHasCollectedInformation(participantDTO.getHasCollectedInformation(),
                    assessment.getParticipant().getId());
            return assessmentRepository.findByUid(assessment.getUid()).get();
        }
        return null;
    }

    public Assessment addAssessmentTools(AssessmentToolsDTO assessmentToolsDTO) {
        var optionalAssessment = assessmentRepository.findByUidAndRemovedDateIsNull(assessmentToolsDTO.getAssessmentUid());
        if (optionalAssessment.isPresent()) {
            optionalAssessment.get().setAssessmentTools(
                    assessmentToolsDTO.updateAssessmentTools(optionalAssessment.get().getAssessmentTools()));
            optionalAssessment.get().getAnswers().setPlanTasksAnswers(assessmentToolsDTO.getPlanTasksAnswers());
            if (!optionalAssessment.get().getState().equals(AssessmentState.COLLECTING_DATA))
                optionalAssessment.get().setState(AssessmentState.COLLECTING_DATA);
            return assessmentRepository.save(optionalAssessment.get());
        }
        return null;
    }

    public Assessment addAssessmentProcedure(AssessmentProcedureDTO assessmentProcedureDTO) {
        var optionalAssessment = assessmentRepository.findByUidAndRemovedDateIsNull(assessmentProcedureDTO.getAssessmentUid());
        if (optionalAssessment.isPresent()) {
            optionalAssessment.get().setAssessmentProcedure(
                    assessmentProcedureDTO.updateProcedure(optionalAssessment.get().getAssessmentProcedure()));
            optionalAssessment.get().getAnswers().setPlanProcedureAnswers(assessmentProcedureDTO.getPlanProcedureAnswers());
            if (!optionalAssessment.get().getState().equals(AssessmentState.COLLECTING_DATA))
                optionalAssessment.get().setState(AssessmentState.COLLECTING_DATA);
            var assessment = assessmentRepository.save(optionalAssessment.get());
            procedureRepository.updateIsPilotAndQuestionAllowed(assessmentProcedureDTO.getIsPilotAssessment(),
                    assessmentProcedureDTO.getQuestionsAllowed(),
                    assessment.getAssessmentProcedure().getId());
            return assessmentRepository.findByUid(assessment.getUid()).get();
        }
        return null;
    }

    public Assessment addAssessmentData(AssessmentDataDTO assessmentDataDTO) {
        var optionalAssessment = assessmentRepository.findByUidAndRemovedDateIsNull(assessmentDataDTO.getAssessmentUid());
        if (optionalAssessment.isPresent()) {
            optionalAssessment.get().setAssessmentData(
                    assessmentDataDTO.updateDataCollection(optionalAssessment.get().getAssessmentData()));
            optionalAssessment.get().getAnswers().setPlanDataAnswers(assessmentDataDTO.getPlanDataAnswers());
            if (!optionalAssessment.get().getState().equals(AssessmentState.COLLECTING_DATA))
                optionalAssessment.get().setState(AssessmentState.COLLECTING_DATA);
            var assessment = assessmentRepository.save(optionalAssessment.get());
            dataRepository.updateStatisticalMethodFlag(assessmentDataDTO.getStatisticalMethods(),
                    assessment.getAssessmentData().getId());
            return assessmentRepository.findByUid(assessment.getUid()).get();
        }
        return null;
    }

    public Assessment addAssessmentThreats(AssessmentThreatDTO assessmentThreatDTO) {
        var optionalAssessment = assessmentRepository.findByUidAndRemovedDateIsNull(assessmentThreatDTO.getAssessmentUid());
        if (optionalAssessment.isPresent()) {
            optionalAssessment.get().setAssessmentThreat(
                    assessmentThreatDTO.updateThreats(optionalAssessment.get().getAssessmentThreat()));
            optionalAssessment.get().getAnswers().setPlanThreatsAnswers(assessmentThreatDTO.getPlanThreatsAnswers());
            if (!optionalAssessment.get().getState().equals(AssessmentState.COLLECTING_DATA))
                optionalAssessment.get().setState(AssessmentState.COLLECTING_DATA);
            var assessment = assessmentRepository.save(optionalAssessment.get());
            threatsRepository.updateEthicalAspectsDefined(assessmentThreatDTO.getEthicalAspectsDefined(),
                    assessment.getAssessmentData().getId());
            return assessmentRepository.findByUid(assessment.getUid()).get();
        }
        return null;
    }

    public Optional<Assessment> finishPlanDataCollection(UUID uid) {
        var optionalAssessment = assessmentRepository.findByUidAndRemovedDateIsNull(uid);
        if (optionalAssessment.isPresent()) {
            optionalAssessment.get().setState(AssessmentState.COMPLETED);
            return Optional.of(assessmentRepository.save(optionalAssessment.get()));
        }
        return Optional.empty();
    }

    public Optional<ByteArrayOutputStream> downloadPlan(UUID uid) {
        var optionalAssessment = assessmentRepository.findByUid(uid);
        List<User> collaborators;
        if (optionalAssessment.isPresent()) {
            var userGroup =
                    assessmentUserGroupRepository.findAllByAssessmentAndProfile(optionalAssessment.get(), UserProfileEnum.COLLABORATOR);
            collaborators = userGroup.stream().map(AssessmentUserGroup::getSystemUser).collect(Collectors.toList());
            return Optional.of(PdfGenerator.generatePlan(optionalAssessment.get(), collaborators));
        }
        return Optional.empty();
    }

    public String findPlanProjectName(UUID uid) {
        var assessmentOptional = assessmentRepository.findByUidAndRemovedDateIsNull(uid);
        if (assessmentOptional.isPresent()) {
            return assessmentOptional.get().getProjectName();
        }
        return "assessmentUsabiity";
    }

    public ResponseEntity<Object> sendPlanToEmail(SendMailRequest emailList) {
        var optionalAssessment = assessmentRepository.findByUid(emailList.getAssessmentUid());
        if (optionalAssessment.isPresent()) {
            var userGroups =
                    assessmentUserGroupRepository.findAllByAssessmentAndProfile(optionalAssessment.get(), UserProfileEnum.COLLABORATOR);
            var collaborators = userGroups.stream().map(AssessmentUserGroup::getSystemUser).collect(Collectors.toList());
            var baos = PdfGenerator.generatePlan(optionalAssessment.get(), collaborators);
            mailSender.sendPlanExportEmail(optionalAssessment.get(), emailList.getEmails(), baos);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    public void releaseSection(UUID userUid) {
        var optionalUser = userRepository.findByUid(userUid);
        if (optionalUser.isPresent()) {
            var userAssessmentList =
                    assessmentRepository.findAllBySystemUserAndRemovedDateIsNull(optionalUser.get());
            if (CollectionUtils.isNotEmpty(userAssessmentList)) {
                userAssessmentList.forEach(assessment ->
                        assessment.getSectionsControlGroup().getSectionsControls()
                                .stream()
                                .filter(sectionsControl -> Objects.nonNull(sectionsControl.getBeingEditedBy()))
                                .forEach(sectionsControl -> {
                                    if (sectionsControl.getBeingEditedBy().equals(userUid)) {
                                        sectionsControl.setSectionControlEnum(SectionControlEnum.AVAILABLE);
                                        sectionsControl.setBeingEditedBy(null);
                                        assessmentRepository.save(assessment);
                                    }
                                }));
            }
        }
    }

    public SectionControlResponseDTO verifySection(SectionUpdateRequestDTO sectionUpdateRequestDTO) {
        var optionalAssessment = assessmentRepository.findByUid(sectionUpdateRequestDTO.getAssessmentUid());
        SectionControlResponseDTO response = SectionControlResponseDTO.builder()
                .userName(null)
                .sectionControlEnum(SectionControlEnum.AVAILABLE)
                .build();
        if (optionalAssessment.isPresent()) {
            optionalAssessment.get().getSectionsControlGroup().getSectionsControls()
                    .stream()
                    .filter(sectionsControl -> sectionsControl.getSection().equals(sectionUpdateRequestDTO.getSectionEnum()))
                    .forEach(sectionsControl -> {
                        if (sectionsControl.getSectionControlEnum().equals(SectionControlEnum.BUSY) &&
                                !sectionsControl.getBeingEditedBy().equals(sectionUpdateRequestDTO.getUserUid())) {
                            response.setSectionControlEnum(SectionControlEnum.BUSY);
                            if (Objects.nonNull(sectionsControl.getBeingEditedBy())) {
                                var optionalUser = userRepository.findByUid(sectionsControl.getBeingEditedBy());
                                optionalUser.ifPresent(user -> response.setUserName(user.getName()));
                            }
                        } else {
                            sectionsControl.setSectionControlEnum(SectionControlEnum.BUSY);
                            sectionsControl.setBeingEditedBy(sectionUpdateRequestDTO.getUserUid());
                        }
                    });
            assessmentRepository.save(optionalAssessment.get());
        }
        return response;
    }

    public void updateSectionState(SectionUpdateRequestDTO sectionUpdateRequestDTO) {
        var optionalAssessment = assessmentRepository.findByUid(sectionUpdateRequestDTO.getAssessmentUid());
        if (optionalAssessment.isPresent()) {
            optionalAssessment.get().getSectionsControlGroup().getSectionsControls()
                    .stream()
                    .filter(sectionsControl -> sectionsControl.getSection().equals(sectionUpdateRequestDTO.getSectionEnum()))
                    .forEach(sectionsControl -> {
                        sectionsControl.setBeingEditedBy(sectionUpdateRequestDTO.getUserUid());
                        sectionsControl.setSectionControlEnum(SectionControlEnum.BUSY);
                    });
            assessmentRepository.save(optionalAssessment.get());
        }
    }


    public Optional<byte[]> downloadExpressPlan() throws IOException {
        File file = new File("src/main/resources/dashboard-image/instrument-canvas.pdf");
        return Optional.of(Files.toByteArray(file));
    }
}
