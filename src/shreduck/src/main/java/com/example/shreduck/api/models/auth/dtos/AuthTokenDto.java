package com.example.shreduck.api.models.auth.dtos;

public record AuthTokenDto(
        AuthSessionDto member,
        String token
) {
}
