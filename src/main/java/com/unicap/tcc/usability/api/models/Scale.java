package com.unicap.tcc.usability.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unicap.tcc.usability.api.models.enums.ScalesEnum;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "scale")
@TypeDefs({
        @TypeDef(
                name = "list-array",
                typeClass = ListArrayType.class
        ),
})
public class Scale extends BaseEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid")
    @org.hibernate.annotations.Type(type="pg-uuid")
    private UUID uid;

    @Column(name = "acronym", columnDefinition = "varchar(10)", nullable = false)
    @Enumerated(EnumType.STRING)
    private ScalesEnum acronym;

    @Column(columnDefinition = "text")
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

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
            name = "measures",
            columnDefinition = "text[]"
    )
    private List<String> measures;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "scale_id", referencedColumnName = "id", nullable = false)
    private List<ScaleQuestion> scaleQuestions;

    @PrePersist
    protected void onCreate() {
        this.uid = java.util.UUID.randomUUID();
    }
}


