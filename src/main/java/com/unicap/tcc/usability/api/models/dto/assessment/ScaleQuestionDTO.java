package com.unicap.tcc.usability.api.models.dto.assessment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unicap.tcc.usability.api.models.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "scale_questions")
public class ScaleQuestionDTO extends BaseEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(name = "scale_id", insertable = false, updatable = false)
    private Long scaleId;


    @Column(name = "key", columnDefinition = "varchar(10)", nullable = false)
    private String key;

    @Column
    private String question;

    @Column(name = "lower_score_label", columnDefinition = "varchar(25)", nullable = false)
    private String lowerScoreLabel;

    @Column(name = "higher_score_label", columnDefinition = "varchar(25)", nullable = false)
    private String higherScoreLabel;
}


