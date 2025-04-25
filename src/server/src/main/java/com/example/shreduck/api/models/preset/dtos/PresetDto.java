package com.example.shreduck.api.models.preset.dtos;

import com.example.shreduck.dl.entities.Preset;
import com.example.shreduck.dl.enums.PresetType;

import java.util.List;

public record PresetDto(
        Long id,
        Long memberId,
        String name,
        List<PresetExerciseDto> presetExercises,
        PresetType presetType
) {

    public static PresetDto fromPreset(Preset preset) {
        return new PresetDto(
                preset.getId(),
                preset.getMember().getId(),
                preset.getName(),
                preset.getPresetExercises()
                        .stream()
                        .map(PresetExerciseDto::fromPresetExercise)
                        .toList(),
                preset.getPresetType()
        );
    }

}
