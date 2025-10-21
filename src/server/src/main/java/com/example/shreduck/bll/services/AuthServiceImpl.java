package com.example.shreduck.bll.services;

import com.example.shreduck.dal.repositories.MemberRepository;
import com.example.shreduck.dl.entities.Member;
import com.example.shreduck.dl.enums.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member login(String pseudo, String password) {
        Member member = memberRepository
                .findByPseudo(pseudo)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Member with pseudo " + pseudo + " not found")
                );
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new RuntimeException("Wrong password");
        }
        return member;
    }

    @Override
    public void register(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new RuntimeException("Member with email " + member.getEmail() + " already exists");
        }
        if (memberRepository.existsByPseudo(member.getPseudo())) {
            throw new RuntimeException("Member with pseudo " + member.getPseudo() + " already exists");
        }
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setMemberRole(MemberRole.MEMBER);
        memberRepository.save(member);
    }

    @Override
    public UserDetails loadUserByUsername(String pseudo) throws UsernameNotFoundException {
        return memberRepository.findByPseudo(pseudo)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Member with pseudo " + pseudo + " not found")
                );
    }

}
