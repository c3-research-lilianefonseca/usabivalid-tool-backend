package com.unicap.tcc.usability.api.models.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unicap.tcc.usability.api.models.BaseEntity;
import com.unicap.tcc.usability.api.models.User;
import com.unicap.tcc.usability.api.models.assessment.Assessment;
import com.unicap.tcc.usability.api.models.assessment.SectionsControlGroup;
import com.unicap.tcc.usability.api.models.enums.EReviewState;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review")
@TypeDefs({
        @TypeDef(
                name = "jsonb",
                typeClass = JsonBinaryType.class
        )
})
public class Review extends BaseEntity {

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

    @Column(name = "state", columnDefinition = "varchar(15)", nullable = false)
    @Enumerated(EnumType.STRING)
    private EReviewState state;

    @Column
    private LocalDate limitReviewDate;

    @Column
    private LocalDate reviewedDate;

    @JsonIgnore
    @OneToOne
    private User reviewer;

    @OneToOne
    private Assessment assessment;

    @Type(type = "jsonb")
    @Column(
            name = "comments",
            columnDefinition = "jsonb"
    )
    private Set<Comment> comments;
}


