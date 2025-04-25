package com.example.shreduck.bll.services;

import com.example.shreduck.dl.entities.Member;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    Member login(String pseudo, String password);

    void register(Member member);

}
