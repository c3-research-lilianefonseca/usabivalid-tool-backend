package com.unicap.tcc.usability.api.models.assessment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.unicap.tcc.usability.api.models.BaseEntity;
import com.unicap.tcc.usability.api.models.Scale;
import com.unicap.tcc.usability.api.models.SmartCityQuestionnaire;
import com.unicap.tcc.usability.api.models.User;
import com.unicap.tcc.usability.api.models.assessment.answer.PlanAnswers;
import com.unicap.tcc.usability.api.models.enums.AssessmentState;
import com.unicap.tcc.usability.api.models.enums.UserProfileEnum;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "assessment")
@TypeDefs({
        @TypeDef(
                name = "jsonb",
                typeClass = JsonBinaryType.class
        )
})
public class Assessment extends BaseEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid")
    @org.hibernate.annotations.Type(type="pg-uuid")
    private UUID uid;

    @PrePersist
    protected void onCreate() {
        this.uid = java.util.UUID.randomUUID();
    }

    @OneToOne(fetch=FetchType.EAGER)
    private User systemUser;

    @Column(name = "state", columnDefinition = "varchar(30)")
    @Enumerated(EnumType.STRING)
    private AssessmentState state;
    ////////// GOALS
    @Column
    private String projectName;
    @Column(columnDefinition = "text")
    private String projectDescription;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "assessment_id", referencedColumnName = "id", nullable = false)
    private List<UsabilityGoal> usabilityGoals;

    @Column
    private Double smartCityPercentage;

    @JsonManagedReference
    @OneToOne(mappedBy = "assessment", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private SmartCityQuestionnaire smartCityQuestionnaire;

    @OneToOne(cascade = CascadeType.ALL)
    private Participant participant;

    @OneToOne(cascade = CascadeType.ALL)
    private AssessmentTools assessmentTools;

    @OneToOne(cascade = CascadeType.ALL)
    private AssessmentProcedure assessmentProcedure;

    @OneToOne(cascade = CascadeType.ALL)
    private AssessmentData assessmentData;

    @OneToOne(cascade = CascadeType.ALL)
    private AssessmentThreat assessmentThreat;

    @Type(type = "jsonb")
    @Column(
            name = "answers",
            columnDefinition = "jsonb"
    )
    private PlanAnswers answers;

    @Type(type = "jsonb")
    @Column(
            name = "sections_control",
            columnDefinition = "jsonb"
    )
    private SectionsControlGroup sectionsControlGroup;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "assessment_id", referencedColumnName = "id", nullable = false)
    @Where(clause = "removed_at is NULL")
    private Set<Attribute> attributes;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "av_scale",
            joinColumns = @JoinColumn(name = "assessment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "scale_id", referencedColumnName = "id"))
    private Set<Scale> scale;

    @Transient
    private UserProfileEnum UserProfile;

}


