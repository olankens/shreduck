package com.example.shreduck.dl.entities;

import com.example.shreduck.dl.enums.ExerciseTarget;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@Setter
@ToString
@Entity
public class Exercise {

    @Column
    private String description;

    @CollectionTable(name = "exercise_target", joinColumns = @JoinColumn(name = "exercise_id"))
    @Column(name = "exercise_target", nullable = false)
    @ElementCollection(targetClass = ExerciseTarget.class)
    @Enumerated(EnumType.STRING)
    private List<ExerciseTarget> exerciseTargets;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String media;

    @Column(nullable = false, unique = true)
    private String name;

}
