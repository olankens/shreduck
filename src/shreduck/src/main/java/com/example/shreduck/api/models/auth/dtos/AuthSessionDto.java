package com.example.shreduck.api.models.auth.dtos;

import com.example.shreduck.dl.entities.Member;
import com.example.shreduck.dl.enums.MemberRole;
import lombok.Builder;

@Builder
public record AuthSessionDto(
        Long id,
        MemberRole memberRole,
        String pseudo
) {

    public static AuthSessionDto fromMember(Member member) {
        return AuthSessionDto
                .builder()
                .id(member.getId())
                .memberRole(member.getMemberRole())
                .pseudo(member.getPseudo())
                .build();
    }

}
