package com.unicap.tcc.usability.api.models.assessment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unicap.tcc.usability.api.models.BaseEntity;
import com.unicap.tcc.usability.api.models.enums.UsabilityAttribute;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@Getter
@Setter
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attribute")
public @Data
class Attribute extends BaseEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(name = "assessment_id", insertable = false, updatable = false)
    private Long assessmentId;

    @Column(name = "usability_attribute", columnDefinition = "varchar(20)", nullable = false)
    @Enumerated(EnumType.STRING)
    private UsabilityAttribute usabilityAttribute;

    @Column(name = "variable_list", columnDefinition = "text")
    private String variables;

    @Column(columnDefinition = "text")
    private String obtainedBy;
}
