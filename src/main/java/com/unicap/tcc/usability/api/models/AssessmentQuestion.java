package com.unicap.tcc.usability.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unicap.tcc.usability.api.models.enums.SectionEnum;
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
@Table(name = "assessment_questions")
public class AssessmentQuestion extends BaseEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category", columnDefinition = "varchar(2)", nullable = false)
    @Enumerated(EnumType.STRING)
    private SectionEnum category;


    @Column(name = "instruction", columnDefinition = "text")
    private String instruction;

    @Column
    private String question;

    @Column(name = "hint", columnDefinition = "text")
    private String hint;

}


