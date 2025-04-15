package com.example.shreduck.dal.initializers;

import com.example.shreduck.dal.repositories.MemberRepository;
import com.example.shreduck.dl.entities.Member;
import com.example.shreduck.dl.enums.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
        Member admin = Member.builder()
                .email("admin@example.com")
                .password(passwordEncoder.encode("admin"))
                .pseudo("admin")
                .memberRole(MemberRole.ADMIN)
                .build();
        Member member = Member.builder()
                .email("member@example.com")
                .password(passwordEncoder.encode("member"))
                .pseudo("member")
                .memberRole(MemberRole.MEMBER)
                .build();
        memberRepository.saveAndFlush(admin);
        memberRepository.saveAndFlush(member);
    }

}
