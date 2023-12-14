package com.abdullah.socialapp.dto;

import com.abdullah.socialapp.model.Role;
import lombok.Builder;

import java.util.Set;

@Builder
public record CreateUserRequest(
        String fullName,
        String username,
        String email,
        String password,
        Set<Role> authorities
) {
}
