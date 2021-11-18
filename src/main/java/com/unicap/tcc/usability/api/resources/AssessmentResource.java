package com.unicap.tcc.usability.api.resources;

import com.unicap.tcc.usability.api.exception.ApiException;
import com.unicap.tcc.usability.api.models.Scale;
import com.unicap.tcc.usability.api.models.SmartCityQuestionnaire;
import com.unicap.tcc.usability.api.models.assessment.Assessment;
import com.unicap.tcc.usability.api.models.assessment.AssessmentUserGroup;
import com.unicap.tcc.usability.api.models.constants.ApplicationConstants;
import com.unicap.tcc.usability.api.models.dto.*;
import com.unicap.tcc.usability.api.models.dto.assessment.*;
import com.unicap.tcc.usability.api.service.AssessmentService;
import com.unicap.tcc.usability.api.utils.UUIDUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/assessment")
@Api("Assessment API")
@RequiredArgsConstructor
public class AssessmentResource {

    private final AssessmentService assessmentService;

    @PostMapping("/sc-percentage")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Smart city percentage calculation.")
    @ApiResponse(code = 200, message = "Calculo realizado.")
    public ResponseEntity<SmartCityResponse> calculateSmartCityPercentage(@RequestBody @Valid SmartCityQuestionnaire questionnaire) {
        return ResponseEntity.ok()
                .body(assessmentService.calculateSmartCityPercentage(questionnaire));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Assessment plan creation.")
    @ApiResponse(code = 200, message = "Assessment plan created.")
    public ResponseEntity<Assessment> createAssessment(@RequestBody @Valid AssessmentCreationDTO assessmentCreationDTO) {
        Assessment assessment = assessmentService.createAssessment(assessmentCreationDTO);
        if (Objects.isNull(assessment)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(assessment);
    }

    @GetMapping("/list/by-user-uid/{uid}")
    @ApiOperation("Search list of assessment plans.")
    public ResponseEntity<List<AssessmentListDTO>> findPlanListByUid(@PathVariable(value = "uid") @ApiParam("User uid") String uid) {
        var assessmentList = assessmentService.findUserAssessmentList(UUID.fromString(uid));
        if (assessmentList.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(assessmentList);
    }

    @GetMapping("/by-uid/{uid}")
    @ApiOperation("Search list of assessment plans.")
    public ResponseEntity<Assessment> findAssessmentPlanByUid(@PathVariable(value = "uid") @ApiParam("Assessment plan uid") String uid) {
        Optional<Assessment> assessmentOptional = assessmentService.findAssessmentPlanByUid(UUID.fromString(uid));
        if (assessmentOptional.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(assessmentOptional.get());
    }


    @PostMapping("/add/smartcity-questionnaire")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Add smartcity questionnaire to assessment plan.")
    @ApiResponse(code = 200, message = "Smartcity questionnaire added.")
    public ResponseEntity<Assessment> addSmartCityQuestionnaire(@RequestBody @Valid ApplicationSectionDTO questionnaire) {
        Assessment assessment = assessmentService.addSmartCityQuestionnaire(questionnaire);
        if (Objects.isNull(assessment)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(assessment);
    }

    @PostMapping("/add/goals")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Add goals to assessment plan.")
    @ApiResponse(code = 200, message = "Goals added.")
    public ResponseEntity<Assessment> addUsabilityGoals(@RequestBody @Valid UsabilityGoalDTO usabilityGoalDTO) {
        Assessment assessment = assessmentService.addUsabilityGoals(usabilityGoalDTO);
        if (Objects.isNull(assessment)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(assessment);
    }

    @PostMapping("/add/variables")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Add variables to assessment plan.")
    @ApiResponse(code = 200, message = "Variables added.")
    public ResponseEntity<Assessment> addVariables(@RequestBody @Valid AssessmentVariablesDTO assessmentVariablesDTO) {
        Assessment assessment = assessmentService.addVariables(assessmentVariablesDTO);
        if (Objects.isNull(assessment)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(assessment);
    }

    @PostMapping("/add/participant")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Add participant to assessment plan.")
    @ApiResponse(code = 200, message = "Participant added.")
    public ResponseEntity<Assessment> addParticipant(@RequestBody @Valid ParticipantDTO participantDTO) {
        Assessment assessment = assessmentService.addParticipant(participantDTO);
        if (Objects.isNull(assessment)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(assessment);
    }

    @PostMapping("/add/tools")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Add tools and tasks to assessment plan.")
    @ApiResponse(code = 200, message = "Tasks and tools added.")
    public ResponseEntity<Assessment> addAssessmentTools(@RequestBody @Valid AssessmentToolsDTO assessmentToolsDTO) {
        Assessment assessment = assessmentService.addAssessmentTools(assessmentToolsDTO);
        if (Objects.isNull(assessment)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(assessment);
    }

    @PostMapping("/add/procedure")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Add procedure to assessment plan.")
    @ApiResponse(code = 200, message = "Procedure added.")
    public ResponseEntity<Assessment> addAssessmentProcedure(@RequestBody @Valid AssessmentProcedureDTO assessmentProcedureDTO) {
        Assessment assessment = assessmentService.addAssessmentProcedure(assessmentProcedureDTO);
        if (Objects.isNull(assessment)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(assessment);
    }

    @PostMapping("/add/data")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Add data collection to assessment plan.")
    @ApiResponse(code = 200, message = "Data collection added.")
    public ResponseEntity<Assessment> addAssessmentData(@RequestBody @Valid AssessmentDataDTO assessmentDataDTO) {
        Assessment assessment = assessmentService.addAssessmentData(assessmentDataDTO);
        if (Objects.isNull(assessment)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(assessment);
    }

    @PostMapping("/add/threats")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Add threats to assessment plan.")
    @ApiResponse(code = 200, message = "Threats added.")
    public ResponseEntity<Assessment> addAssessmentThreats(@RequestBody @Valid AssessmentThreatDTO assessmentThreatDTO) {
        Assessment assessment = assessmentService.addAssessmentThreats(assessmentThreatDTO);
        if (Objects.isNull(assessment)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(assessment);
    }


    @GetMapping("/download-report/{id}")
    @Produces({ApplicationConstants.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    public Response generateReport(@PathVariable @Pattern(regexp = UUIDUtils.UUID_REGEXP, message = "Invalid id") String id) throws ApiException {
        try {
            return assessmentService.getReportStreamingOutput(id);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(Response.Status.INTERNAL_SERVER_ERROR,
                    "Internal server error. It was not possible to download this assessment");
        }
    }

    @PutMapping("/finish/{uid}")
    @ApiOperation("Finish a plan data collection.")
    public ResponseEntity<FinishResponseDTO> finishPlanDataCollection(@Valid @PathVariable(value = "uid") @ApiParam("Assessment plan uid") String uid) {
        Optional<Assessment> assessment = assessmentService.finishPlanDataCollection(UUID.fromString(uid));
        if (assessment.isEmpty())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(FinishResponseDTO.builder()
                .uid(UUID.fromString(uid))
                .projectName(assessment.get().getProjectName())
                .build());
    }

    @GetMapping("/scales")
    @ApiOperation("Get list of scales.")
    public ResponseEntity< List<Scale>> getScaleList() {
        List<Scale> scaleList = assessmentService.getScaleList();
        if (scaleList.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(scaleList);
    }

    @PostMapping("/add/collaborator")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Add collaborator to assessment plan.")
    @ApiResponse(code = 200, message = "Collaborator added.")
    public ResponseEntity<Assessment> addCollaborators(@RequestBody @Valid CollaboratorDTO collaboratorDTO) {
        Assessment assessment = assessmentService.addCollaborator(collaboratorDTO);
        if (Objects.isNull(assessment)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(assessment);
    }

    @PostMapping("/enter/collaborator")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Enter as collaborator to assessment plan.")
    @ApiResponse(code = 200, message = "Collaborator entered.")
    public ResponseEntity<Object> enterAsCollaborator(@RequestBody @Valid CollaboratorDTO collaboratorDTO) {
        AssessmentUserGroup assessmentUserGroup = assessmentService.enterAsCollaborator(collaboratorDTO);
        if (Objects.isNull(assessmentUserGroup)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/delete/{uid}")
    @ApiOperation("Delete assessment plan.")
    public ResponseEntity<Object> deleteAssessmentPlan(@Valid @PathVariable(value = "uid") @ApiParam("Assessment plan uid") String uid) {
        Optional<Assessment> assessmentOptional = assessmentService.deleteAssessmentPlan(UUID.fromString(uid));
        if (assessmentOptional.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{uid}/file")
    @ApiOperation("Download plan.")
    public ResponseEntity<byte[]> download(@PathVariable @Pattern(regexp = UUIDUtils.UUID_REGEXP, message = "Invalid uid") String uid) {

        Optional<ByteArrayOutputStream> byteArrayOutputStream = assessmentService.downloadPlan(UUID.fromString(uid));

        if (byteArrayOutputStream.isEmpty())
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok()
                .header("Content-Disposition",
                        "attachment; filename=" + assessmentService.findPlanProjectName(UUID.fromString(uid)) + "Plan.pdf")
                .contentType(APPLICATION_OCTET_STREAM)
                .body(byteArrayOutputStream.get().toByteArray());
    }

    @PostMapping("/export/to-email")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Send plan to email.")
    @ApiResponse(code = 200, message = "Email sended.")
    public ResponseEntity<Object> sendPlanToEmail(@RequestBody @Valid SendMailRequest sendMailRequest) throws IOException {
        return assessmentService.sendPlanToEmail(sendMailRequest);
    }

    @GetMapping("/release/section/{uid}")
    @ApiOperation("Delete assessment plan.")
    public ResponseEntity<Object> releaseSection(@Valid @PathVariable(value = "uid") @ApiParam("user uid") String uid) {
        assessmentService.releaseSection(UUID.fromString(uid));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify/section")
    @ApiOperation("Check state of section plan.")
    public ResponseEntity<SectionControlResponseDTO> verifySection(
            @RequestBody @Valid SectionUpdateRequestDTO sectionUpdateRequestDTO) {
        SectionControlResponseDTO response = assessmentService.verifySection(sectionUpdateRequestDTO);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/update/section")
    @ApiOperation("Update section of plan.")
    public ResponseEntity<Object> updateSectionState(
            @RequestBody @Valid SectionUpdateRequestDTO sectionUpdateRequestDTO) {
        assessmentService.updateSectionState(sectionUpdateRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/express/file")
    @ApiOperation("Download express plan.")
    public ResponseEntity<byte[]> downloadExpressPlanning() throws IOException {

        Optional<byte[]> byteArrayOutputStream = assessmentService.downloadExpressPlan();

        if (byteArrayOutputStream.isEmpty())
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok()
                .contentType(APPLICATION_OCTET_STREAM)
                .body(byteArrayOutputStream.get());
    }
}
