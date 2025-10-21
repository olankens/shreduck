package com.example.shreduck.api.models.preset.dtos;

import com.example.shreduck.api.models.exercise.dtos.ExerciseDto;
import com.example.shreduck.dl.entities.PresetExercise;

public record PresetExerciseDto(
        ExerciseDto exercise,
        int position
) {

    public static PresetExerciseDto fromPresetExercise(PresetExercise pe) {
        return new PresetExerciseDto(
                ExerciseDto.fromExercise(pe.getExercise()),
                pe.getPosition()
        );
    }

}
