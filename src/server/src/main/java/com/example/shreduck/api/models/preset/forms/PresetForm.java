package com.example.shreduck.api.models.preset.forms;

import com.example.shreduck.dl.entities.Exercise;
import com.example.shreduck.dl.entities.Preset;
import com.example.shreduck.dl.entities.PresetExercise;
import com.example.shreduck.dl.enums.PresetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.function.Function;

public record PresetForm(
        @NotBlank String name,
        List<PresetExerciseForm> presetExercises,
        @NotNull PresetType presetType
) {

    public Preset toPreset(Function<Long, Exercise> exerciseFetcher) {
        Preset preset = Preset
                .builder()
                .name(name)
                .presetType(presetType)
                .build();
        if (presetExercises != null && !presetExercises.isEmpty()) {
            List<PresetExercise> exercises = presetExercises.stream()
                    .map(form -> PresetExercise.builder()
                            .exercise(exerciseFetcher.apply(form.exerciseId()))
                            .position(form.position())
                            .preset(preset)
                            .build()
                    )
                    .toList();
            preset.setPresetExercises(exercises);
        }
        return preset;
    }

}
