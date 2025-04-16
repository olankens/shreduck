package com.example.shreduck.api.models.exercise.dtos;

import com.example.shreduck.dl.entities.Exercise;
import com.example.shreduck.dl.enums.ExerciseTarget;
import lombok.Builder;

import java.util.List;

@Builder
public record ExerciseDto(
        String description,
        List<ExerciseTarget> exerciseTargets,
        Long id,
        String media,
        String name
) {

    public static ExerciseDto fromExercise(Exercise exercise) {
        return ExerciseDto
                .builder()
                .description(exercise.getDescription())
                .exerciseTargets(exercise.getExerciseTargets())
                .id(exercise.getId())
                .media(exercise.getMedia())
                .name(exercise.getName())
                .build();
    }

}
