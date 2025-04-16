package com.example.shreduck.bll.services;

import com.example.shreduck.dal.repositories.ExerciseRepository;
import com.example.shreduck.dl.entities.Exercise;
import com.example.shreduck.dl.enums.ExerciseTarget;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;

@SpringBootTest
class ExerciseServiceImplTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    @Test
    void createExerciseWithoutTargets() {
        Exercise payload = Exercise
                .builder()
                .name("Name goes here")
                .description("Description goes here")
                .exerciseTargets(List.of())
                .media(null)
                .build();
        Exercise mocking = payload.toBuilder()
                .id(1L)
                .build();
        when(exerciseRepository.save(payload)).thenReturn(mocking);
        Exercise created = exerciseService.create(payload, null);
        assertEquals(0, created.getExerciseTargets().size());
        assertTrue(created.getId() > 0);
    }

    @Test
    void createExerciseWithTwoTargets() {
        Exercise payload = Exercise
                .builder()
                .name("Name goes here")
                .description("Description goes here")
                .exerciseTargets(List.of(ExerciseTarget.values()[0], ExerciseTarget.values()[1]))
                .media(null)
                .build();
        Exercise mocking = payload.toBuilder()
                .id(1L)
                .build();
        when(exerciseRepository.save(payload)).thenReturn(mocking);
        Exercise created = exerciseService.create(payload, null);
        assertEquals(2, created.getExerciseTargets().size());
        assertTrue(created.getId() > 0);
    }

    @Test
    void detail() {
    }

    @Test
    void export() {
    }

    @Test
    void filter() {
    }

    @Test
    void update() {
    }

}