package com.example.shreduck.dal.repositories;

import com.example.shreduck.dl.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findAllByNameContainingIgnoreCase(String query);

}
