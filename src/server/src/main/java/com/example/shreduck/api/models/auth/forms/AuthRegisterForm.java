package com.example.shreduck.api.models.auth.forms;

import com.example.shreduck.dl.entities.Member;
import jakarta.validation.constraints.NotBlank;

public record AuthRegisterForm(
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String pseudo
) {

    public Member toMember() {
        return Member
                .builder()
                .email(email)
                .password(password)
                .pseudo(pseudo)
                .build();
    }

}
