package com.unicap.tcc.usability.api.models.assessment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unicap.tcc.usability.api.models.BaseEntity;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "assessment_procedure")
@TypeDefs({
        @TypeDef(
                name = "list-array",
                typeClass = ListArrayType.class
        ),
})
public class AssessmentProcedure extends BaseEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate occurDate;

    @Column(columnDefinition = "text")
    private String occurLocal;

    @Column(columnDefinition = "text")
    private String occurDetail;

    @Column(columnDefinition = "text")
    private String occurTime;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "assessment_procedure_id", referencedColumnName = "id", nullable = false)
    @Where(clause = "removed_at is NULL")
    private Set<AssessmentProcedureStep> assessmentProcedureSteps;

    @Column(name = "is_pilot_assessment", columnDefinition = "boolean default false")
    @Generated(GenerationTime.ALWAYS)
    private Boolean isPilotAssessment;

    @Column(columnDefinition = "text")
    private String pilotDescription;

    @Column(name = "questions_allowed", columnDefinition = "boolean default false")
    @Generated(GenerationTime.ALWAYS)
    private Boolean questionsAllowed;
}
