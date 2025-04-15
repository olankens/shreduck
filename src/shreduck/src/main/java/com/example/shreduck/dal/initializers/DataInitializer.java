package com.example.shreduck.dal.initializers;

import com.example.shreduck.dal.repositories.ExerciseRepository;
import com.example.shreduck.dal.repositories.MemberRepository;
import com.example.shreduck.dl.entities.Exercise;
import com.example.shreduck.dl.entities.Member;
import com.example.shreduck.dl.enums.ExerciseTarget;
import com.example.shreduck.dl.enums.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class DataInitializer implements ApplicationRunner {

    private final ExerciseRepository exerciseRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        createDefaultExercises();
        createDefaultMembers();
    }

    private void createDefaultExercises() {
        if (memberRepository.count() != 0) return;
        exerciseRepository.saveAllAndFlush(List.of(
                Exercise
                        .builder()
                        .description("Pellentesque ultricies ex eu dui ornare maximus.")
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
                        .media(null)
                        .name("Grasshopper Push-Ups")
                        .build(),
                Exercise
                        .builder()
                        .description("Pellentesque ultricies ex eu dui ornare maximus.")
                        .exerciseTargets(List.of(
                                ExerciseTarget.ABS,
                                ExerciseTarget.CHEST,
                                ExerciseTarget.FOREARMS,
                                ExerciseTarget.LATS,
                                ExerciseTarget.SHOULDERS,
                                ExerciseTarget.TRICEPS
                        ))
                        .media(null)
                        .name("Hindu Push-Ups")
                        .build()
        ));
    }

    private void createDefaultMembers() {
        if (memberRepository.count() != 0) return;
        memberRepository.saveAllAndFlush(List.of(
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

}
