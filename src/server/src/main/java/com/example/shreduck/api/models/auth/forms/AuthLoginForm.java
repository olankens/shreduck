package com.example.shreduck.api.models.auth.forms;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginForm(
        @NotBlank String password,
        @NotBlank String pseudo
) {
}
