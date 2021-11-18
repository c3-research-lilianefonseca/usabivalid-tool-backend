package com.unicap.tcc.usability.api.models.dto.assessment;

import com.unicap.tcc.usability.api.models.assessment.Participant;
import com.unicap.tcc.usability.api.models.assessment.answer.PlanParticipantsAnswers;
import com.unicap.tcc.usability.api.models.enums.ParticipationLocalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantDTO{

    private UUID assessmentUid;
    private Integer participantsQuantity;
    private ParticipationLocalType participationLocalType;
    private String compensationDescription;
    private String criteria;
    private Boolean hasCollectedInformation;
    private String collectedInformationUse;
    private String instructions;
    private List<String> questions;
    private PlanParticipantsAnswers planParticipantsAnswers;

    public Participant updateParticipant(Participant participant) {
        if (Objects.isNull(participant)) {
            return Participant.builder()
                    .participantsQuantity(this.participantsQuantity)
                    .participationLocalType(this.participationLocalType)
                    .compensationDescription(this.compensationDescription)
                    .collectedInformationUse(this.collectedInformationUse)
                    .criteria(this.criteria)
                    .hasCollectedInformation(this.hasCollectedInformation)
                    .instructions(this.instructions)
                    .questions(this.questions)
                    .build();
        }
        participant.setParticipantsQuantity(this.participantsQuantity);
        participant.setParticipationLocalType(this.participationLocalType);
        participant.setCompensationDescription(this.compensationDescription);
        participant.setCollectedInformationUse(this.collectedInformationUse);
        participant.setCriteria(this.criteria);
        participant.setHasCollectedInformation(this.hasCollectedInformation);
        participant.setInstructions(this.instructions);
        participant.setQuestions(this.questions);
        return participant;
    }
}
