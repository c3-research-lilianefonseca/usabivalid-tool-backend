package com.unicap.tcc.usability.api.models.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unicap.tcc.usability.api.models.BaseEntity;
import com.unicap.tcc.usability.api.models.enums.EScore;
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
@Table(name = "review_item")
public class ReviewItem extends BaseEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state", columnDefinition = "varchar(15)", nullable = false)
    @Enumerated(EnumType.STRING)
    private EScore score;

    @OneToOne
    private Review review;

    @Column(name = "comment", columnDefinition = "text")
    private String comment;
}


