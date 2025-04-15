package com.example.shreduck.dal.repositories;

import com.example.shreduck.dl.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    boolean existsByPseudo(String pseudo);

    Optional<Member> findByPseudo(String pseudo);

}
