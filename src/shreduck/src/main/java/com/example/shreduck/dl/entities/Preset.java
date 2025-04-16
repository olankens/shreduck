package com.example.shreduck.dl.entities;

import com.example.shreduck.dl.enums.PresetType;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@Setter
@Entity
public class Preset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PresetType presetType;

}
