package com.example.shreduck.dal.initializers;

import com.example.shreduck.dal.repositories.ExerciseRepository;
import com.example.shreduck.dal.repositories.MemberRepository;
import com.example.shreduck.dal.repositories.PresetExerciseRepository;
import com.example.shreduck.dal.repositories.PresetRepository;
import com.example.shreduck.dl.entities.Exercise;
import com.example.shreduck.dl.entities.Member;
import com.example.shreduck.dl.entities.Preset;
import com.example.shreduck.dl.entities.PresetExercise;
import com.example.shreduck.dl.enums.ExerciseTarget;
import com.example.shreduck.dl.enums.MemberRole;
import com.example.shreduck.dl.enums.PresetType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Component
public class DataInitializer implements ApplicationRunner {

    private final ExerciseRepository exerciseRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final PresetRepository presetRepository;
    private final PresetExerciseRepository presetExerciseRepository;

    @Override
    public void run(ApplicationArguments args) {
        createDefaultExercises();
        createDefaultMembers();
        createDefaultPresets();
    }

    private void createDefaultExercises() {
        if (memberRepository.count() != 0) return;
        exerciseRepository.saveAll(List.of(
                Exercise
                        .builder()
                        .description("...")
                        .exerciseTargets(List.of(
                                ExerciseTarget.ABS,
                                ExerciseTarget.CHEST,
                                ExerciseTarget.GLUTES,
                                ExerciseTarget.LATS,
                                ExerciseTarget.SHOULDERS
                        ))
                        .media("uploads/arm_raise_plank.mp4")
                        .name("Arm Raise Plank")
                        .build(),
                Exercise
                        .builder()
                        .description("...")
                        .exerciseTargets(List.of(
                                ExerciseTarget.ABS,
                                ExerciseTarget.CHEST,
                                ExerciseTarget.FOREARMS,
                                ExerciseTarget.GLUTES,
                                ExerciseTarget.OBLIQUES,
                                ExerciseTarget.SHOULDERS
                        ))
                        .media("uploads/shoulder_tap_plank.mp4")
                        .name("Shoulder Tap Plank")
                        .build(),
                Exercise
                        .builder()
                        .description("...")
                        .exerciseTargets(List.of(
                                ExerciseTarget.ABS,
                                ExerciseTarget.CHEST,
                                ExerciseTarget.FOREARMS,
                                ExerciseTarget.GLUTES,
                                ExerciseTarget.HAMSTRINGS,
                                ExerciseTarget.LATS,
                                ExerciseTarget.SHOULDERS,
                                ExerciseTarget.TRICEPS
                        ))
                        .media("uploads/grasshopper_push_ups.mp4")
                        .name("Grasshopper Push-Ups")
                        .build(),
                Exercise
                        .builder()
                        .description("...")
                        .exerciseTargets(List.of(
                                ExerciseTarget.ABS,
                                ExerciseTarget.CHEST,
                                ExerciseTarget.FOREARMS,
                                ExerciseTarget.LATS,
                                ExerciseTarget.SHOULDERS,
                                ExerciseTarget.TRICEPS
                        ))
                        .media("uploads/hindu_push_ups.mp4")
                        .name("Hindu Push-Ups")
                        .build(),
                Exercise
                        .builder()
                        .description("...")
                        .media("uploads/rest.mp4")
                        .name("Rest")
                        .build()
        ));
    }

    private void createDefaultMembers() {
        if (memberRepository.count() != 0) return;
        memberRepository.saveAll(List.of(
                Member
                        .builder()
                        .email("admin@example.com")
                        .memberRole(MemberRole.ADMIN)
                        .password(passwordEncoder.encode("admin"))
                        .pseudo("admin")
                        .build(),
                Member
                        .builder()
                        .email("member@example.com")
                        .memberRole(MemberRole.MEMBER)
                        .password(passwordEncoder.encode("member"))
                        .pseudo("member")
                        .build()
        ));
    }

    private void createDefaultPresets() {
        if (presetRepository.count() != 0) return;
        createPreset("admin", "push-up", "Premium Push-Ups", PresetType.AD);
        createPreset("admin", "plank", "Premium Plank to Hell", PresetType.AD);
        createPreset("member", "push-up", "Basic Push-Ups", PresetType.FREE);
        createPreset("member", "plank", "Basic Plank to Hell", PresetType.FREE);
    }

    private void createPreset(String pseudo, String query, String name, PresetType type) {
        Member member = memberRepository.findByPseudo(pseudo).orElseThrow();
        List<Exercise> exerciseList = exerciseRepository.findAllByNameContainingIgnoreCase(query);
        if (!exerciseList.isEmpty()) {
            Preset preset = Preset.builder()
                    .name(name)
                    .member(member)
                    .presetType(type)
                    .build();
            presetRepository.save(preset);
            List<PresetExercise> presetExerciseList = new ArrayList<>();
            IntStream.range(0, exerciseList.size()).forEach(i -> {
                PresetExercise pe = new PresetExercise();
                pe.setPreset(preset);
                pe.setExercise(exerciseList.get(i));
                pe.setPosition(i + 1);
                presetExerciseList.add(pe);
            });
            presetExerciseRepository.saveAll(presetExerciseList);
        }
    }

}
