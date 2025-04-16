package com.example.shreduck.api.models.preset.forms;

import com.example.shreduck.dl.entities.Preset;
import com.example.shreduck.dl.enums.PresetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PresetForm(
        @NotBlank String name,
        List<PresetExerciseForm> presetExercises,
        @NotNull PresetType presetType
) {

    public Preset toPreset() {
        return Preset
                .builder()
                .name(name)
                .presetType(presetType)
                .build();
    }

}
