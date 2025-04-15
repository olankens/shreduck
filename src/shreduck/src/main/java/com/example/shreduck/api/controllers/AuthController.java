package com.example.shreduck.api.controllers;

import com.example.shreduck.api.models.auth.dtos.AuthSessionDto;
import com.example.shreduck.api.models.auth.dtos.AuthTokenDto;
import com.example.shreduck.api.models.auth.forms.AuthLoginForm;
import com.example.shreduck.api.models.auth.forms.AuthRegisterForm;
import com.example.shreduck.bll.services.AuthService;
import com.example.shreduck.dl.entities.Member;
import com.example.shreduck.il.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PreAuthorize("isAnonymous()")
    @PostMapping("/login")
    public ResponseEntity<AuthTokenDto> login(@RequestBody @Valid AuthLoginForm form) {
        Member member = authService.login(form.pseudo(), form.password());
        String token = jwtUtil.generateToken(member);
        AuthSessionDto authSessionDto = AuthSessionDto.fromMember(member);
        AuthTokenDto authTokenDto = new AuthTokenDto(authSessionDto, token);
        return ResponseEntity.ok(authTokenDto);
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid AuthRegisterForm form) {
        authService.register(form.toMember());
        return ResponseEntity.noContent().build();
    }

}
