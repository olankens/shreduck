package com.example.shreduck.dal.repositories;

import com.example.shreduck.dl.entities.Preset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresetRepository extends JpaRepository<Preset, Long> {
}
