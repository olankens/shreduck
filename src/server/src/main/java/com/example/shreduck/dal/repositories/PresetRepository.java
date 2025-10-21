package com.example.shreduck.dal.repositories;

import com.example.shreduck.dl.entities.Member;
import com.example.shreduck.dl.entities.Preset;
import com.example.shreduck.dl.enums.PresetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PresetRepository extends JpaRepository<Preset, Long> {

    List<Preset> findAllByMember(Member member);

    List<Preset> findByPresetTypeIn(Collection<PresetType> presetTypes);

}
