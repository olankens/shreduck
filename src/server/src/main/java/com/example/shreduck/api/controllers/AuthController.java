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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping(value = "login", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<AuthTokenDto> login(@RequestPart @Valid AuthLoginForm form) {
        Member member = authService.login(form.pseudo(), form.password());
        String token = jwtUtil.generateToken(member);
        AuthSessionDto authSessionDto = AuthSessionDto.fromMember(member);
        AuthTokenDto authTokenDto = new AuthTokenDto(authSessionDto, token);
        return ResponseEntity.ok(authTokenDto);
    }

    @PostMapping(value = "register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<Void> register(@RequestPart @Valid AuthRegisterForm form) {
        authService.register(form.toMember());
        return ResponseEntity.noContent().build();
    }

}
