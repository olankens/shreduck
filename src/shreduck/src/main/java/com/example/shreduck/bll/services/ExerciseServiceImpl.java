package com.example.shreduck.bll.services;

import com.example.shreduck.dal.repositories.ExerciseRepository;
import com.example.shreduck.dl.entities.Exercise;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Override
    public Exercise create(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @Override
    public Exercise detail(Long id) {
        return exerciseRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Exercise with id " + id + " not found")
        );
    }

    @Cacheable(value = "exercises")
    @Override
    public List<Exercise> export() {
        return exerciseRepository.findAll();
    }

    @Override
    public List<Exercise> filter(String query) {
        return exerciseRepository.findAllByNameContainingIgnoreCase(query);
    }

    @Override
    public Exercise update(Long id, Exercise exercise) {
        Exercise updated = exerciseRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Exercise with id " + id + " not found")
        );
        updated.setName(exercise.getName());
        updated.setDescription(exercise.getDescription());
        updated.setExerciseTargets(exercise.getExerciseTargets());
        updated.setMedia(exercise.getMedia());
        return exerciseRepository.save(updated);
    }

}
