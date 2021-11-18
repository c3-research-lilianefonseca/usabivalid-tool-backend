package com.unicap.tcc.usability.api.models.assessment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unicap.tcc.usability.api.models.enums.UsabilityAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;

@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usabiliy_goals")
public @Data
class UsabilityGoal {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="assessment_id", insertable = false, updatable = false)
    @JsonIgnore
    private Long assessmentId;
    @Column(name = "attribute", nullable = false)
    @Enumerated(EnumType.STRING)
    private UsabilityAttribute attribute;
    @Column(columnDefinition = "text")
    private String goal;
    @Column(name = "done", columnDefinition = "boolean default false")
    @Generated(GenerationTime.ALWAYS)
    private Boolean done;
}
