package com.unicap.tcc.usability.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@MappedSuperclass
public class BaseEntity {

    protected static final long serialVersionUID = 1L;

    @Column(name = "created_at", columnDefinition = "timestamp default now()")
    @CreationTimestamp
    protected LocalDateTime creationDate;

    @JsonIgnore
    @Column(name = "last_updated_at", columnDefinition = "timestamp default now()")
    @UpdateTimestamp
    protected LocalDateTime updateDate;

    @JsonIgnore
    @Column(name = "removed_at")
    protected LocalDateTime removedDate;
}
