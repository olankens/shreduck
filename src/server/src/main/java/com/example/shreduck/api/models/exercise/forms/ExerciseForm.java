package com.example.shreduck.api.models.exercise.forms;

import com.example.shreduck.dl.entities.Exercise;
import com.example.shreduck.dl.enums.ExerciseTarget;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ExerciseForm(
        @NotBlank String description,
        List<ExerciseTarget> exerciseTargets,
        @NotBlank String name
) {

    public Exercise toExercise() {
        return Exercise
                .builder()
                .description(description)
                .exerciseTargets(exerciseTargets)
                .media(null)
                .name(name)
                .build();
    }

}
