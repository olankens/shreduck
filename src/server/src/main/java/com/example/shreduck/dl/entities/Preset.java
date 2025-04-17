package com.example.shreduck.dl.entities;

import com.example.shreduck.dl.enums.PresetType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@Setter
@ToString
@Entity
public class Preset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "preset", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private List<PresetExercise> presetExercises;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PresetType presetType;

    @ManyToMany(mappedBy = "unlockedPresets")
    private List<Member> unlockedMembers = new ArrayList<>();

}
