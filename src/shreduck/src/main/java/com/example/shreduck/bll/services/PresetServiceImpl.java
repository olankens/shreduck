package com.example.shreduck.bll.services;

import com.example.shreduck.dal.repositories.PresetRepository;
import com.example.shreduck.dl.entities.Member;
import com.example.shreduck.dl.entities.Preset;
import com.example.shreduck.dl.enums.MemberRole;
import com.example.shreduck.dl.enums.PresetType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PresetServiceImpl implements PresetService {

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
    public List<Preset> export(Member current) {
        return presetRepository.findAllByMember(current);
    }

}
