package com.example.shreduck.bll.services;

import com.example.shreduck.api.models.preset.forms.PresetForm;
import com.example.shreduck.dal.repositories.MemberRepository;
import com.example.shreduck.dal.repositories.PresetExerciseRepository;
import com.example.shreduck.dal.repositories.PresetRepository;
import com.example.shreduck.dl.entities.Member;
import com.example.shreduck.dl.entities.Preset;
import com.example.shreduck.dl.enums.MemberRole;
import com.example.shreduck.dl.enums.PresetType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PresetServiceImpl implements PresetService {

    private final MemberRepository memberRepository;
    private final PresetExerciseRepository presetExerciseRepository;
    private final PresetRepository presetRepository;

    @Override
    public Preset create(Preset preset, Member current) {
        preset.setMember(current);
        if (current.getMemberRole().equals(MemberRole.MEMBER)) preset.setPresetType(PresetType.FREE);
        return presetRepository.save(preset);
    }

    @Override
    public void delete(Long id, Member current) {
        Preset preset = presetRepository.findById(id).orElseThrow();
        if (!preset.getMember().equals(current)) {
            throw new SecurityException("You do not have permission to delete this preset");
        }
        presetRepository.delete(preset);
    }

    @Override
    public Preset detail(Long id, Member current) {
        Preset preset = presetRepository.findById(id).orElseThrow();
        if (!preset.getMember().equals(current)) {
            throw new SecurityException("You do not have permission to delete this preset");
        }
        return preset;
    }

    @Override
    public Preset detailUnlockable(Long id) {
        Preset preset = presetRepository.findById(id).orElseThrow();
        if (preset.getPresetType() == PresetType.FREE) {
            throw new RuntimeException("Preset is free and doesn't require unlocking");
        }
        return preset;
    }

    @Override
    public List<Preset> export(Member current) {
        return presetRepository.findAllByMember(current);
    }

    @Override
    public List<Preset> exportUnlockable() {
        return presetRepository.findByPresetTypeIn(List.of(PresetType.AD, PresetType.PAID));
    }

    @Override
    public void unlock(Long id, Member current) {
        Preset preset = presetRepository.findById(id).orElseThrow();
        if (preset.getPresetType() == PresetType.FREE) {
            throw new RuntimeException("Free presets do not need to be unlocked");
        }
        if (!current.getUnlockedPresets().contains(preset)) {
            current.getUnlockedPresets().add(preset);
            memberRepository.save(current);
        }
    }

    @Override
    @Transactional
    public Preset update(Long id, PresetForm form, Member current) {
        Preset preset = presetRepository.findById(id).orElseThrow();
        if (!preset.getMember().getId().equals(current.getId())) {
            throw new SecurityException("You do not have permission to update this preset");
        }
        preset.setName(form.name());
        preset.setPresetType(form.presetType());
        presetExerciseRepository.deleteAllByPreset(preset);
//        List<PresetExercise> newExercises = form.presetExercises().stream()
//                .map(pef -> PresetExercise.builder()
//                        .exercise(exerciseRepository.findById(pef.exerciseId()).orElseThrow())
//                        .position(pef.position())
//                        .preset(preset)
//                        .build())
//                .toList();
//        presetExerciseRepository.saveAll(newExercises);
        return presetRepository.save(preset);
    }

}
