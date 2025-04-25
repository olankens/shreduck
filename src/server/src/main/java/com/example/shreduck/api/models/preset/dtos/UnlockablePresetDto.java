package com.example.shreduck.api.models.preset.dtos;

public record UnlockablePresetDto(
        PresetDto preset,
        boolean unlocked
) {
}
