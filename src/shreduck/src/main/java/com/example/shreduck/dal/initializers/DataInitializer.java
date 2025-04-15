package com.example.shreduck.dal.initializers;

import com.example.shreduck.dal.repositories.MemberRepository;
import com.example.shreduck.dl.entities.Member;
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

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        createDefaultMembers();
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
