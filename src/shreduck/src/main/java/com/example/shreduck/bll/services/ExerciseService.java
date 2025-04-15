package com.example.shreduck.bll.services;

import com.example.shreduck.dl.entities.Exercise;

import java.util.List;

public interface ExerciseService {

    Exercise create(Exercise exercise);

    Exercise detail(Long id);

    List<Exercise> export();

    List<Exercise> filter(String query);

    Exercise update(Long id, Exercise exercise);

}
