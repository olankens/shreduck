package com.example.shreduck.bll.services;

import com.example.shreduck.dal.repositories.ExerciseRepository;
import com.example.shreduck.dl.entities.Exercise;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Override
    public Exercise create(Exercise exercise, MultipartFile media) {
        if (media != null && !media.isEmpty()) {
            String mediaPath = handleMedia(media);
            exercise.setMedia(mediaPath);
        }
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
    public Exercise update(Long id, Exercise exercise, MultipartFile media) {
        Exercise updated = exerciseRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Exercise with id " + id + " not found")
        );
        updated.setName(exercise.getName());
        updated.setDescription(exercise.getDescription());
        updated.setExerciseTargets(exercise.getExerciseTargets());
        if (media != null && !media.isEmpty()) {
            if (updated.getMedia() != null) {
                File oldFile = new File(updated.getMedia());
                if (oldFile.exists() && oldFile.isFile()) {
                    boolean deleted = oldFile.delete();
                    if (!deleted) {
                        throw new RuntimeException(
                                "Deleting media with path " + oldFile.getAbsolutePath() + " has failed"
                        );
                    }
                }
            }
            String mediaPath = handleMedia(media);
            updated.setMedia(mediaPath);
        }
        return exerciseRepository.save(updated);
    }

    private String handleMedia(MultipartFile media) throws SecurityException {
        try {
            String depositPath = "uploads/";
            File depositFolder = new File(depositPath);
            if (!depositFolder.exists() && !depositFolder.mkdirs()) {
                throw new RuntimeException("Creating upload folder has failed");
            }
            Objects.requireNonNull(media.getOriginalFilename());
            String extension = media.getOriginalFilename().substring(media.getOriginalFilename().lastIndexOf("."));
            String mediaName = UUID.randomUUID().toString().replace("-", "");
            String mediaPath = depositPath + mediaName + extension;
            Files.write(Paths.get(mediaPath), media.getBytes());
            return mediaPath;
        } catch (IOException e) {
            throw new RuntimeException("Handling media file has failed", e);
        }
    }

}
