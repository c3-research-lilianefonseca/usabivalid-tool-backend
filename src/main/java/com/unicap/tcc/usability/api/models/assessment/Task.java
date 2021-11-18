package com.unicap.tcc.usability.api.models.assessment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unicap.tcc.usability.api.models.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Objects;

@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "assessment_tasks")
public @Data
class Task extends BaseEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assessment_tools_id", insertable = false, updatable = false)
    private Long assessmentToolsId;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String taskExecutionTime;

    @Column(columnDefinition = "text")
    private String acceptanceCriteria;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return description.equals(task.description) &&
                taskExecutionTime.equals(task.taskExecutionTime) &&
                acceptanceCriteria.equals(task.acceptanceCriteria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, taskExecutionTime, acceptanceCriteria);
    }
}
