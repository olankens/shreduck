package com.example.shreduck.api.models.exercise.forms;

import com.example.shreduck.dl.entities.Exercise;
import com.example.shreduck.dl.enums.ExerciseTarget;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ExerciseCreateForm(
        @NotBlank String name,
        @NotBlank String description,
        List<ExerciseTarget> exerciseTargets,
        @NotBlank String media
) {

    public Exercise toExercise() {
        return Exercise
                .builder()
                .name(name)
                .description(description)
                .exerciseTargets(exerciseTargets)
                .media(media)
                .build();
    }

}
