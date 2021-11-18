package com.unicap.tcc.usability.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "scale_questions")
public class ScaleQuestion extends BaseEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(name = "scale_id", insertable = false, updatable = false)
    private Long scaleId;


    @Column(name = "key", columnDefinition = "varchar(10)", nullable = false)
    private String key;

    @Column(columnDefinition = "text")
    private String question;

    @Column(name = "lower_score_label", columnDefinition = "varchar(25)", nullable = false)
    private String lowerScoreLabel;

    @Column(name = "higher_score_label", columnDefinition = "varchar(25)", nullable = false)
    private String higherScoreLabel;
}


