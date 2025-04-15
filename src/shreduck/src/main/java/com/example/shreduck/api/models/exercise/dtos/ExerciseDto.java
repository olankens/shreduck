package com.example.shreduck.api.models.exercise.dtos;

import com.example.shreduck.dl.entities.Exercise;
import com.example.shreduck.dl.enums.ExerciseTarget;
import lombok.Builder;

import java.util.List;

@Builder
public record ExerciseDto(
        Long id,
        String name,
        String description,
        List<ExerciseTarget> exerciseTargets,
        String media
) {

    public static ExerciseDto fromExercise(Exercise exercise) {
        return ExerciseDto
                .builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .description(exercise.getDescription())
                .exerciseTargets(exercise.getExerciseTargets())
                .media(exercise.getMedia())
                .build();
    }

}
