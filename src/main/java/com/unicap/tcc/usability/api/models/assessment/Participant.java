package com.unicap.tcc.usability.api.models.assessment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unicap.tcc.usability.api.models.BaseEntity;
import com.unicap.tcc.usability.api.models.enums.ParticipationLocalType;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "participants")
@TypeDefs({
        @TypeDef(
                name = "list-array",
                typeClass = ListArrayType.class
        ),
})
public class Participant extends BaseEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Integer participantsQuantity;

    @Column(name = "participation_local_type", columnDefinition = "varchar(1)", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParticipationLocalType participationLocalType;

    @Column(name = "has_compensation", columnDefinition = "boolean default false")
    @Generated(GenerationTime.ALWAYS)
    private Boolean hasCompensation;

    @Column(columnDefinition = "text")
    private String compensationDescription;

    @Column(name = "eligibility_criteria", columnDefinition = "text")
    private String criteria;

    @Column(name = "has_collected_information", columnDefinition = "boolean default false")
    @Generated(GenerationTime.ALWAYS)
    private Boolean hasCollectedInformation;

    @Column(columnDefinition = "text")
    private String collectedInformationUse;

    @Column(columnDefinition = "text")
    private String instructions;

    @Type(
            type = "com.vladmihalcea.hibernate.type.array.ListArrayType",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = ListArrayType.SQL_ARRAY_TYPE,
                            value = "text"
                    )
            }
    )
    @Column(
            name = "questions",
            columnDefinition = "text[]"
    )
    private List<String> questions;

}
