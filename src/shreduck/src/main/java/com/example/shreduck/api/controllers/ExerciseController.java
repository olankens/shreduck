package com.example.shreduck.api.controllers;

import com.example.shreduck.api.models.exercise.dtos.ExerciseDto;
import com.example.shreduck.api.models.exercise.forms.ExerciseForm;
import com.example.shreduck.bll.services.ExerciseService;
import com.example.shreduck.dl.entities.Exercise;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/exercise")
@RequiredArgsConstructor
@RestController
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ExerciseDto> create(
            @RequestPart @Valid ExerciseForm form,
            @RequestPart(value = "media", required = false) MultipartFile file
    ) {
        Exercise exercise = exerciseService.create(form.toExercise(), file);
        return ResponseEntity.ok(ExerciseDto.fromExercise(exercise));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEMBER')")
    public ResponseEntity<ExerciseDto> detail(@PathVariable Long id) {
        Exercise exercise = exerciseService.detail(id);
        return ResponseEntity.ok(ExerciseDto.fromExercise(exercise));
    }

    @GetMapping
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

    @PutMapping(value = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ExerciseDto> update(
            @PathVariable Long id,
            @RequestPart @Valid ExerciseForm form,
            @RequestPart(value = "media", required = false) MultipartFile file
    ) {
        Exercise exercise = exerciseService.update(id, form.toExercise(), file);
        return ResponseEntity.ok(ExerciseDto.fromExercise(exercise));
    }

}
