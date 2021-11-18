package com.unicap.tcc.usability.api.models.dto.assessment;

import com.unicap.tcc.usability.api.models.assessment.AssessmentTools;
import com.unicap.tcc.usability.api.models.assessment.Task;
import com.unicap.tcc.usability.api.models.assessment.answer.PlanTasksAnswers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentToolsDTO {

    private UUID assessmentUid;
    private List<String> tools;
    private String toolsUsageDescription;
    private List<TaskDTO> taskDTOS;
    private PlanTasksAnswers planTasksAnswers;

    public AssessmentTools updateAssessmentTools(AssessmentTools assessmentTools) {
        if (Objects.isNull(assessmentTools)) {
            return AssessmentTools.builder()
                    .tasks(this.taskDTOListToTaskList())
                    .tools(this.tools)
                    .toolsUsageDescription(this.toolsUsageDescription)
                    .build();
        }
        this.updateTasks(assessmentTools.getTasks());
        assessmentTools.setToolsUsageDescription(this.toolsUsageDescription);
        assessmentTools.setTools(this.tools);
        return assessmentTools;
    }

    private Set<Task> taskDTOListToTaskList() {
        return this.taskDTOS.stream().map(taskDTO ->
                Task.builder()
                        .acceptanceCriteria(taskDTO.getAcceptanceCriteria())
                        .description(taskDTO.getDescription())
                        .taskExecutionTime(taskDTO.getTaskExecutionTime())
                        .build())
                .collect(Collectors.toSet());
    }

    private void updateTasks(Set<Task> tasks) {
        tasks.forEach(task -> {
            if (!this.taskDTOListToTaskList().contains(task)) {
                task.setRemovedDate(LocalDateTime.now());
            }
        });
        Set<Task> newTasks = new HashSet<>();
        this.taskDTOListToTaskList().forEach(task -> {
            if (!tasks.contains(task))
                newTasks.add(task);
        });
        tasks.addAll(newTasks);
    }
}
