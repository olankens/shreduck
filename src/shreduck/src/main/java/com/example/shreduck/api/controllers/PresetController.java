package com.example.shreduck.api.controllers;

import com.example.shreduck.api.models.preset.dtos.PresetDto;
import com.example.shreduck.api.models.preset.forms.PresetForm;
import com.example.shreduck.bll.services.PresetService;
import com.example.shreduck.dl.entities.Member;
import com.example.shreduck.dl.entities.Preset;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/preset")
@RequiredArgsConstructor
@RestController
public class PresetController {

    private final PresetService presetService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER')")
    public ResponseEntity<PresetDto> create(
            @RequestPart() PresetForm form,
            @AuthenticationPrincipal Member current
    ) {
        Preset preset = presetService.create(form.toPreset(), current);
        return ResponseEntity.ok(PresetDto.fromPreset(preset));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER')")
    public ResponseEntity<PresetDto> delete(
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

}
