package com.example.shreduck.api.models.preset.dtos;

import com.example.shreduck.dl.entities.Preset;
import com.example.shreduck.dl.enums.PresetType;

public record PresetDto(
        Long id,
        Long memberId,
        String name,
        PresetType presetType
) {
    public static PresetDto fromPreset(Preset preset) {
        return new PresetDto(
                preset.getId(),
                preset.getMember().getId(),
                preset.getName(),
                preset.getPresetType()
        );
    }
}
