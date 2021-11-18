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
@Table(name = "assessment_threat")
@TypeDefs({
        @TypeDef(
                name = "list-array",
                typeClass = ListArrayType.class
        ),
})
public class AssessmentThreat extends BaseEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(name = "assessment_tools_id", insertable = false, updatable = false)
    private Long assessmentToolsId;

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
            name = "threats",
            columnDefinition = "text[]"
    )
    private List<String> threats;

    @Column(name = "control_measure", columnDefinition = "text")
    private String controlMeasure;

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
            name = "limitations",
            columnDefinition = "text[]"
    )
    private List<String> limitations;

    @Column(name = "ethical_aspects_defined", columnDefinition = "boolean default false")
    @Generated(GenerationTime.ALWAYS)
    private Boolean ethicalAspectsDefined;

    @Column(name = "ethical_aspects_description", columnDefinition = "text")
    private String ethicalAspectsDescription;

    @Column(name = "bias_description", columnDefinition = "text")
    private String biasDescription;
}
