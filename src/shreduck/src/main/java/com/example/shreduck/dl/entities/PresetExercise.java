package com.example.shreduck.dl.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@Setter
@Entity
public class PresetExercise {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int position;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "preset_id")
    private Preset preset;

}
