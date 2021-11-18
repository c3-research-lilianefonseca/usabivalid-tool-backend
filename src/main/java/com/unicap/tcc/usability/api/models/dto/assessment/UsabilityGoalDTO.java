package com.unicap.tcc.usability.api.models.dto.assessment;

import com.amazonaws.util.StringUtils;
import com.unicap.tcc.usability.api.models.assessment.UsabilityGoal;
import com.unicap.tcc.usability.api.models.assessment.answer.PlanGoalsAnswers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsabilityGoalDTO {

    private UUID assessmentUid;
    private List<GoalDTO> goals;
    private PlanGoalsAnswers planGoalsAnswers;

    public List<UsabilityGoal> toUsabilityGoals(List<UsabilityGoal> usabilityGoals) {
        if (CollectionUtils.isEmpty(usabilityGoals)) {
            return goals.stream()
                    .filter(goalDTO -> !StringUtils.isNullOrEmpty(goalDTO.getGoal()))
                    .map(goalDTO -> {
                var usabilityGoal = UsabilityGoal.builder()
                        .attribute(goalDTO.getAttribute())
                        .goal(goalDTO.getGoal())
                        .build();
                if (!StringUtils.isNullOrEmpty(goalDTO.getGoal())) {
                    usabilityGoal.setDone(true);
                }
                return usabilityGoal;
            }).collect(Collectors.toList());
        }

        var receivedGoals = goals
                .stream()
                .filter(goalDTO -> Objects.nonNull(goalDTO.getGoal()))
                .map(goalDTO -> UsabilityGoal.builder()
                        .attribute(goalDTO.getAttribute())
                        .goal(goalDTO.getGoal())
                        .done(true)
                        .build()).collect(Collectors.toList());

        var newGoals = new ArrayList<UsabilityGoal>();

        receivedGoals.forEach(usabilityGoal -> {
            AtomicBoolean exist = new AtomicBoolean(false);
            usabilityGoals.forEach(usabilityGoal1 -> {
                if (usabilityGoal.getAttribute() == usabilityGoal1.getAttribute())
                    usabilityGoal1.setGoal(usabilityGoal.getGoal());
                    exist.set(true);
            });
            if (!exist.get()) {
                newGoals.add(usabilityGoal);
            }
            exist.set(false);
        });
        if (CollectionUtils.isNotEmpty(newGoals))
            usabilityGoals.addAll(newGoals);
        return usabilityGoals;
    }
}
