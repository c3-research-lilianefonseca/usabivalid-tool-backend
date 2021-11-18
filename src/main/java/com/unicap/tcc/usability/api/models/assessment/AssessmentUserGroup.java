package com.unicap.tcc.usability.api.models.assessment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unicap.tcc.usability.api.models.BaseEntity;
import com.unicap.tcc.usability.api.models.User;
import com.unicap.tcc.usability.api.models.enums.UserProfileEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "assessment_user_group")
public class AssessmentUserGroup extends BaseEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User systemUser;

    @OneToOne
    private Assessment assessment;

    @Column(name = "profile", columnDefinition = "varchar(15)", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserProfileEnum profile;
}


