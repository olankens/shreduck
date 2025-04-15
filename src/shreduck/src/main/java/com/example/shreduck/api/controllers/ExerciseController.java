package com.example.shreduck.api.controllers;

import com.example.shreduck.api.models.exercise.dtos.ExerciseDto;
import com.example.shreduck.api.models.exercise.forms.ExerciseForm;
import com.example.shreduck.bll.services.ExerciseService;
import com.example.shreduck.dl.entities.Exercise;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/exercise")
@RequiredArgsConstructor
@RestController
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ExerciseDto> create(@RequestBody @Valid ExerciseForm form) {
        Exercise exercise = exerciseService.create(form.toExercise());
        return ResponseEntity.ok(ExerciseDto.fromExercise(exercise));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER')")
    public ResponseEntity<ExerciseDto> detail(@PathVariable Long id) {
        Exercise exercise = exerciseService.detail(id);
        return ResponseEntity.ok(ExerciseDto.fromExercise(exercise));
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER')")
    public ResponseEntity<List<ExerciseDto>> export(@RequestParam(required = false) String query) {
        List<Exercise> exerciseList = (query == null || query.isBlank())
                ? exerciseService.export()
                : exerciseService.filter(query);
        List<ExerciseDto> exerciseDtoList = exerciseList.stream()
                .map(ExerciseDto::fromExercise)
                .toList();
        return ResponseEntity.ok(exerciseDtoList);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ExerciseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid ExerciseCreateForm form
    ) {
        Exercise exercise = exerciseService.update(id, form.toExercise());
        return ResponseEntity.ok(ExerciseDto.fromExercise(exercise));
    }

}
