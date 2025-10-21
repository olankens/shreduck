package com.example.shreduck.api.controllers;

import com.example.shreduck.api.models.preset.dtos.PresetDto;
import com.example.shreduck.api.models.preset.dtos.UnlockablePresetDto;
import com.example.shreduck.api.models.preset.forms.PresetForm;
import com.example.shreduck.bll.services.ExerciseService;
import com.example.shreduck.bll.services.PresetService;
import com.example.shreduck.dl.entities.Member;
import com.example.shreduck.dl.entities.Preset;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/preset")
@RequiredArgsConstructor
@RestController
public class PresetController {

    private final PresetService presetService;
    private final ExerciseService exerciseService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER')")
    public ResponseEntity<PresetDto> create(
            @RequestPart() PresetForm form,
            @AuthenticationPrincipal Member current
    ) {
        Preset preset = presetService.create(form.toPreset(exerciseService::detail), current);
        return ResponseEntity.ok(PresetDto.fromPreset(preset));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal Member current
    ) {
        presetService.delete(id, current);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER')")
    public ResponseEntity<PresetDto> detail(
            @PathVariable Long id,
            @AuthenticationPrincipal Member current
    ) {
        Preset preset = presetService.detail(id, current);
        return ResponseEntity.ok(PresetDto.fromPreset(preset));
    }

    @GetMapping("/unlockable/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER')")
    public ResponseEntity<PresetDto> detailUnlockable(@PathVariable Long id) {
        Preset preset = presetService.detailUnlockable(id);
        return ResponseEntity.ok(PresetDto.fromPreset(preset));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER')")
    public ResponseEntity<List<PresetDto>> export(@AuthenticationPrincipal Member current) {
        List<Preset> presetList = presetService.export(current);
        List<PresetDto> presetDtoList = presetList.stream()
                .map(PresetDto::fromPreset)
                .toList();
        return ResponseEntity.ok(presetDtoList);
    }

    @GetMapping("/unlockable")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER')")
    public ResponseEntity<List<UnlockablePresetDto>> exportUnlockable(
            @AuthenticationPrincipal Member current
    ) {
        List<Preset> presets = presetService.exportUnlockable();
        List<UnlockablePresetDto> dtoList = presets.stream()
                .map(p -> new UnlockablePresetDto(
                        PresetDto.fromPreset(p),
                        p.getUnlockedMembers().contains(current)
                ))
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("/{id}/unlock")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER')")
    public ResponseEntity<Void> unlock(
            @PathVariable Long id,
            @AuthenticationPrincipal Member current
    ) {
        presetService.unlock(id, current);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER')")
    public ResponseEntity<PresetDto> update(
            @PathVariable Long id,
            @RequestBody PresetForm form,
            @AuthenticationPrincipal Member current
    ) {
        Preset updated = presetService.update(id, form, current);
        return ResponseEntity.ok(PresetDto.fromPreset(updated));
    }

}
