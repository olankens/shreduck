package com.example.shreduck.dal.repositories;

import com.example.shreduck.dl.entities.Preset;
import com.example.shreduck.dl.entities.PresetExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresetExerciseRepository extends JpaRepository<PresetExercise, Long> {

    void deleteAllByPreset(Preset preset);

}