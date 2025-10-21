package com.example.shreduck.dl.entities;

import com.example.shreduck.dl.enums.MemberRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@Setter
@ToString
@Entity
public class Member implements UserDetails {

    @Column(nullable = false, unique = true)
    private String email;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Preset> presets = new ArrayList<>();

    @Column(nullable = false, unique = true)
    private String pseudo;

    @JoinTable(
            name = "unlocked_preset",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "preset_id")
    )
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Preset> unlockedPresets = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.memberRole.toString()));
    }

    @Override
    public String getUsername() {
        return this.pseudo;
    }

}
