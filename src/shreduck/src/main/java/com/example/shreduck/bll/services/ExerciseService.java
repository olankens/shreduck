package com.example.shreduck.bll.services;

import com.example.shreduck.dl.entities.Exercise;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExerciseService {

    Exercise create(Exercise exercise, MultipartFile file);

    Exercise detail(Long id);

    List<Exercise> export();

    List<Exercise> filter(String query);

    Exercise update(Long id, Exercise exercise, MultipartFile file);

}
